package uk.bradford.app_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import uk.bradford.app_project.fragments.AboutFragment;
import uk.bradford.app_project.fragments.SettingsFragment;
import uk.bradford.app_project.fragments.SubstitutionFragment;
import uk.bradford.app_project.fragments.TranspositionFragment;
import uk.bradford.app_project.fragments.VigenereFragment;
import uk.bradford.app_project.fragments.XORFragment;
import uk.bradford.app_project.model.CipherSnapshot;
import uk.bradford.app_project.model.User;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Starting screen fragment
        Fragment aboutFragment = new AboutFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, aboutFragment).commit();


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();


        navigationView.setNavigationItemSelectedListener(this::onNavigationViewItemSelected);

        Toolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menuicon);

        // Restore user data??
        restoreUserDataToPrefs(mAuth.getCurrentUser());
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (user == null)  // goes back to the login activity if user is not logged in
            returnToLogin();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean onNavigationViewItemSelected(MenuItem item) {

        int id = item.getItemId();
        Fragment fragment;

        if (id == R.id.nav_item1) {
            fragment = new VigenereFragment();

        } else if (id == R.id.nav_item2) {
            fragment = new XORFragment();

        } else if (id == R.id.nav_item3) {

            fragment = new SubstitutionFragment();
        } else if (id == R.id.nav_item4) {

            fragment = new TranspositionFragment();
        } else if (id == R.id.nav_item5) {
            fragment = new SettingsFragment(user);

        } else if (id == R.id.nav_item6) {
            fragment = new AboutFragment();
        } else if (id == R.id.nav_item7) {
            return logout(user);

        } else {
            Log.e("NavigationView", "Invalid menu item id");
            fragment = null;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    // Firebase
    public boolean logout(FirebaseUser user) {
        if (user == null) {
            Log.e("Logout", "User is null");
            return false;
        } else {
            // Creates a User object that is stored on the database
            // containing the recent snapshot of all ciphers
            User userDataObject = new User(mAuth.getCurrentUser());
            userDataObject.setLastSnapshot(createCipherSnapshot());


            Task task = database.getReference().child("users").child(userDataObject.getId()).setValue(userDataObject);
            // Makes sure that only if the task is completed and successful, the prefs file gets emptied
            task.addOnCompleteListener(this::onSavingUserDataCompleted);


            mAuth.signOut();
            returnToLogin();
            return true;
        }
    }

    private void returnToLogin() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void onSavingUserDataCompleted(Task task) {
        if (task.isSuccessful()) {
            Log.d("Firebase", "Saving cipher snapshot for user:success");

            removeSnapshotsFromPrefs();
        } else {
            // If sign in fails, display a message to the user.
            Log.w("Firebase", "Saving cipher snapshot failed, not deleting prefs", task.getException());
            Toast.makeText(getApplicationContext(), "An Error occurred while trying to store data, please login again to save your data.", Toast.LENGTH_SHORT).show();
        }

    }

    // Creates a snapshot of all the current ciphers
    private CipherSnapshot createCipherSnapshot() {
        SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);
        ArrayList<ArrayList<String>> ciphers = new ArrayList<>();

        int i = 0;
        for (Cipher cipher : Cipher.values()) {
            ArrayList<String> snapshot = new ArrayList<>();
            snapshot.add(0, prefs.getString(cipher + "key", ""));
            snapshot.add(1, prefs.getString(cipher + "msg", ""));
            snapshot.add(2, prefs.getString(cipher + "out", ""));
            ciphers.add(i, snapshot);
            i++;
        }

        return new CipherSnapshot(ciphers);
    }

    private void removeSnapshotsFromPrefs() {
        SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        for (Cipher cipher : Cipher.values()) {
            editor.putString(cipher + "key", "");
            editor.putString(cipher + "msg", "");
            editor.putString(cipher + "out", "");
            editor.apply();
        }
    }


    private void restoreUserDataToPrefs(FirebaseUser user) {

        DatabaseReference mDatabase = database.getReference();
        mDatabase.child("users").child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    // Retrieval was succuessful
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    User userDataObject = task.getResult().getValue(User.class);

                    // Restoring data from the user object
                    SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();

                    // user has no history yet
                    if (userDataObject == null || userDataObject.getLastSnapshot() == null || userDataObject.getLastSnapshot().getCiphersPairs() == null)
                        return;

                    int i = 0;
                    for (Cipher cipher : Cipher.values()) {
                        ArrayList<String> cipherValues = userDataObject.getLastSnapshot().getCiphersPairs().get(i);
                        editor.putString(cipher + "key", cipherValues.get(0));
                        editor.putString(cipher + "msg", cipherValues.get(1));
                        editor.putString(cipher + "out", cipherValues.get(2));
                        editor.apply();
                        i++;
                    }



                }
            }
        });


    }

}

