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
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

import uk.bradford.app_project.fragments.AboutFragment;
import uk.bradford.app_project.fragments.CipherFragment;
import uk.bradford.app_project.fragments.DescriptionFragment;
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
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_1, aboutFragment).commit();


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

        // Restore user data
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
        Fragment descriptionFragment = new DescriptionFragment();

        if (id == R.id.nav_item1) {
            fragment = new VigenereFragment();
            Cipher cipher = new Cipher(R.layout.vigenere, R.string.input_error_vigenere, R.string.usage_vigenere, R.string.description_vigenere, Cipher.Type.VIGENERE);
            descriptionFragment = new DescriptionFragment(cipher);

        } else if (id == R.id.nav_item2) {
            fragment = new XORFragment();
            Cipher cipher = new Cipher(R.layout.xor, R.string.input_error_xor, R.string.usage_xor, R.string.description_xor, Cipher.Type.XOR);
            descriptionFragment = new DescriptionFragment(cipher);

        } else if (id == R.id.nav_item3) {

            fragment = new SubstitutionFragment();
            Cipher cipher = new Cipher(R.layout.substitution, R.string.input_error_substitution, R.string.usage_substitution, R.string.description_substitution, Cipher.Type.SUBSTITUTION);
            descriptionFragment = new DescriptionFragment(cipher);

        } else if (id == R.id.nav_item4) {

            fragment = new TranspositionFragment();
            Cipher cipher = new Cipher(R.layout.transposition, R.string.input_error_transposition, R.string.usage_transposition, R.string.description_transposition, Cipher.Type.TRANSPOSITION);
            descriptionFragment = new DescriptionFragment(cipher);

        } else if (id == R.id.nav_item5) {
            fragment = new SettingsFragment();

        } else if (id == R.id.nav_item6) {
            fragment = new AboutFragment();
        } else if (id == R.id.nav_item7) {
            return logout(user);

        } else {
            Log.e("NavigationView", "Invalid menu item id");
            fragment = null;
        }

        // Sets the fragments into the containers
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_1, fragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_2, descriptionFragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    // Firebase
    // ----------------------------
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

        // Save the last open cipher to prefs, as this might not have been done
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container_1);

        if (currentFragment instanceof CipherFragment){
            CipherFragment fragment = (CipherFragment) currentFragment;
            fragment.saveToPrefs();
        }



        SharedPreferences prefs = this.getPreferences(Context.MODE_PRIVATE);
        ArrayList<ArrayList<String>> ciphers = new ArrayList<>();

        int i = 0;
        for (Cipher.Type cipher : Cipher.Type.values()) {
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

        for (Cipher.Type cipher : Cipher.Type.values()) {
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
                    // Retrieval was successful
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    User userDataObject = task.getResult().getValue(User.class);

                    // Restoring data from the user object
                    SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();

                    // user has no history yet
                    if (userDataObject == null || userDataObject.getLastSnapshot() == null || userDataObject.getLastSnapshot().getCiphersPairs() == null)
                        return;

                    int i = 0;
                    for (Cipher.Type cipher : Cipher.Type.values()) {
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

