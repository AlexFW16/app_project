package uk.bradford.app_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;

import uk.bradford.app_project.fragments.AboutFragment;
import uk.bradford.app_project.fragments.SettingsFragment;
import uk.bradford.app_project.fragments.SubstitutionFragment;
import uk.bradford.app_project.fragments.TranspositionFragment;
import uk.bradford.app_project.fragments.VigenereFragment;
import uk.bradford.app_project.fragments.XORFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_cipher);

        // Starting screen fragment
        Fragment aboutFragment = new AboutFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, aboutFragment).commit();

        //DEBUG
        Toast.makeText(getApplicationContext(), "THIS IS A STARTING TOAST ", Toast.LENGTH_LONG);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // Handle navigation view item clicks here.
                int id = menuItem.getItemId();

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
        });

        Toolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menuicon);
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

    private boolean logout(FirebaseUser user) {
        if (user == null) {
            Log.e("Logout", "User is null");
            return false;
        } else {
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
}

