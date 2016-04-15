package shehan.com.migrainetrigger.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.view.fragment.HomeFragment;
import shehan.com.migrainetrigger.view.fragment.SeverityFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Feature not implemented", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
            fab.setVisibility(View.VISIBLE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        if (savedInstanceState == null) {

            Fragment homeFragment = new HomeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, homeFragment);
            fragmentTransaction.commit();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(R.string.nav_home);
            }

        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Fragment homeFragment = new HomeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, homeFragment);
            fragmentTransaction.commit();

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(R.string.nav_home);
            }

            if (fab != null) {
                fab.setVisibility(View.VISIBLE);
            }

            Toast toast = Toast.makeText(this, "Feature not  fully implemented", Toast.LENGTH_SHORT);
            toast.show();
        } else if (id == R.id.nav_severity) {
            Fragment severityFragment = new SeverityFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, severityFragment);
            fragmentTransaction.commit();

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(R.string.nav_severity);
            }
            if (fab != null) {
                fab.setVisibility(View.INVISIBLE);
            }

        } else if (id == R.id.nav_answers) {
            if (fab != null) {
                fab.setVisibility(View.INVISIBLE);
            }
            Toast toast = Toast.makeText(this, "Feature not implemented", Toast.LENGTH_SHORT);
            toast.show();
        } else if (id == R.id.nav_faq) {
            if (fab != null) {
                fab.setVisibility(View.INVISIBLE);
            }
            Toast toast = Toast.makeText(this, "Feature not implemented", Toast.LENGTH_SHORT);
            toast.show();
        } else if (id == R.id.nav_record) {
            if (fab != null) {
                fab.setVisibility(View.INVISIBLE);
            }
            Toast toast = Toast.makeText(this, "Feature not implemented", Toast.LENGTH_SHORT);
            toast.show();
        } else if (id == R.id.nav_report) {
            if (fab != null) {
                fab.setVisibility(View.INVISIBLE);
            }
            Toast toast = Toast.makeText(this, "Feature not implemented", Toast.LENGTH_SHORT);
            toast.show();
        } else if (id == R.id.nav_settings) {
            if (fab != null) {
                fab.setVisibility(View.INVISIBLE);
            }
            Toast toast = Toast.makeText(this, "Feature not implemented", Toast.LENGTH_SHORT);
            toast.show();
        } else if (id == R.id.nav_about) {
            if (fab != null) {
                fab.setVisibility(View.INVISIBLE);
            }
            Toast toast = Toast.makeText(this, "Feature not implemented", Toast.LENGTH_SHORT);
            toast.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }
}
