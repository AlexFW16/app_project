package uk.bradford.app_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_cipher);


            drawerLayout = findViewById(R.id.drawer_layout);

            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    // Handle navigation view item clicks here.
                    int id = menuItem.getItemId();


                    if (id == R.id.nav_item5) {

                        Fragment aboutFragment = new AboutFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, aboutFragment).commit();
                    }

                    // Handle other navigation items ...


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

