package uk.bradford.app_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_cipher);

        Fragment aboutFragment = new VigenereFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, aboutFragment).commit();

        //DEBUG
        Toast.makeText(getApplicationContext(), "THIS IS A STARTING TOAST ", Toast.LENGTH_LONG);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);


        // Can be removed or changed, just for testing -> Works
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                Toast.makeText(getApplicationContext(), "MESSAGELA:DLFKJ:SLDKFJ:SLDKFJ:", Toast.LENGTH_LONG).show();
                // still not working

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        /* Added:
        android:enableOnBackInvokedCallback="true" in manifest bc of warning
        Is apparently an opt-in for hand gestures
         */
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // Handle navigation view item clicks here.
                int id = menuItem.getItemId();

                //DEBUg
                Log.e("onNavigationItemSelected", "onNavigationItemSelectedDDDDDDDDDDDDD");

                Fragment fragment = null;

                if (id == R.id.nav_item1) {
                    fragment = new VigenereFragment();
                } else if (id == R.id.nav_item2) {
                    fragment = new XORFragment();

                } else if (id == R.id.nav_item3) {

                    fragment = new SubstitutionFragment();
                } else if (id == R.id.nav_item4) {

                    fragment = new TranspositionFragment();
                } else if (id == R.id.nav_item5) {

                    fragment = new SettingsFragment();
                } else if (id == R.id.nav_item6) {
                    fragment = new AboutFragment();

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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

