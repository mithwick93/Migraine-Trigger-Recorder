package shehan.com.migrainetrigger.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.afollestad.materialdialogs.MaterialDialog;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.view.fragment.main.AboutFragment;
import shehan.com.migrainetrigger.view.fragment.main.HomeFragment;
import shehan.com.migrainetrigger.view.fragment.main.SeverityFragment;

public class MainActivity
        extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final boolean DEVELOPER_MODE = true;

    private FloatingActionButton fab;
    private int lastFragment;
    private Boolean isFabOpen = false;
    private Animation rotateForward, rotateBackward;

    //region activity default

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (DEVELOPER_MODE) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        fabSetup();

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
            navigationView.getMenu().getItem(0).setChecked(true);
        }

        if (savedInstanceState == null) {
            setFragment(new HomeFragment(), R.string.nav_home, View.VISIBLE, false);
        }

        this.lastFragment = -1;
        Log.d("Main-onCreate", "onCreate success");
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


    //endregion


    //region interface implementations

    /**
     * Action on navigation item click
     *
     * @param item clicked item
     * @return clicked value
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //Navigation drawer logic
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Log.d("Main-navigation", "Home selected");
            setFragment(new HomeFragment(), R.string.nav_home, View.VISIBLE, false);

        } else if (id == R.id.nav_severity) {
            Log.d("Main-navigation", "Severity selected");
            setFragment(new SeverityFragment(), R.string.nav_severity, View.INVISIBLE, true);

        } else if (id == R.id.nav_answers) {
            Log.d("Main-navigation", "Answers selected");
            showNotImplemented();

        } else if (id == R.id.nav_faq) {
            Log.d("Main-navigation", "F.A.Q selected");
            Intent intent = new Intent(MainActivity.this, FAQActivity.class);
            Log.d("Main-navigation", "Launching F.A.Q activity");
            startActivity(intent);

        } else if (id == R.id.nav_record) {
            Log.d("Main-navigation", "Record selected");
            Intent intent = new Intent(MainActivity.this, ViewRecordsActivity.class);
            Log.d("Main-navigation", "Launching view record activity");
            startActivity(intent);

        } else if (id == R.id.nav_report) {
            Log.d("Main-navigation", "Report selected");
            showNotImplemented();

        } else if (id == R.id.nav_settings) {
            Log.d("Main-navigation", "Settings selected");
            showNotImplemented();

        } else if (id == R.id.nav_about) {
            Log.d("Main-navigation", "About selected");
            setFragment(new AboutFragment(), R.string.nav_about, View.INVISIBLE, true);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }


    //endregion

    //region methods

    /**
     * Floating action button behaviour
     */
    private void fabSetup() {

        fab = (FloatingActionButton) findViewById(R.id.fab);
        rotateForward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);

        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("MainActivity-fab", "Launching information level dialog");

                    new MaterialDialog.Builder(MainActivity.this)
                            .title(R.string.levelOfInformationDialog)
                            .items(R.array.levelOfInformationOptions)
                            .negativeText(R.string.cancelButtonDialog)
                            .itemsCallback(new MaterialDialog.ListCallback() {
                                @Override
                                public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                                    Intent intent = new Intent(MainActivity.this, AddRecordActivity.class);
                                    intent.putExtra("levelOfInformation", which);
                                    Log.d("MainActivity-fab-dialog", "Launching new record activity");
                                    startActivity(intent);
                                }
                            })
                            .show();

                }
            });
        }
    }

    /**
     * Change fragment in main activity
     *
     * @param fragment      fragment to show
     * @param toolBarTitle  tool bar title
     * @param fabVisibility fab visibility
     */
    private void setFragment(Fragment fragment,
                             int toolBarTitle,
                             int fabVisibility,
                             boolean addToBackStack) {


        Log.d("Main-setFragment", fragment.toString() + " , " + Integer.toString(toolBarTitle) + " , " + Integer.toString(fabVisibility));
        String tag = getString(toolBarTitle);

        if (lastFragment == toolBarTitle) {
            Log.d("Main-setFragment", "Fragment already in view");
//            return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.container_body, fragment, tag);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(toolBarTitle);
        }

        if (fab != null) {
            fab.setVisibility(fabVisibility);
        }

        //Track last fragment
        lastFragment = toolBarTitle;
    }

    private void showNotImplemented() {
        if (fab != null) {
            fab.setVisibility(View.INVISIBLE);
        }
    }


    //endregion
}
