package shehan.com.migrainetrigger.view.activity;

import android.content.Intent;
import android.os.Bundle;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.view.fragment.dummy.AboutFragment;
import shehan.com.migrainetrigger.view.fragment.dummy.AlternativeFragment;
import shehan.com.migrainetrigger.view.fragment.dummy.CausesFragment;
import shehan.com.migrainetrigger.view.fragment.dummy.ComplicationsFragment;
import shehan.com.migrainetrigger.view.fragment.dummy.DefinitionFragment;
import shehan.com.migrainetrigger.view.fragment.dummy.DiagnosisFragment;
import shehan.com.migrainetrigger.view.fragment.dummy.PreventionFragment;
import shehan.com.migrainetrigger.view.fragment.dummy.RemediesFragment;
import shehan.com.migrainetrigger.view.fragment.dummy.RiskFragment;
import shehan.com.migrainetrigger.view.fragment.dummy.SymptomsFragment;
import shehan.com.migrainetrigger.view.fragment.dummy.TreatmentsFragment;
import shehan.com.migrainetrigger.view.fragment.main.FAQTopicsFragment;
import shehan.com.migrainetrigger.view.fragment.main.HomeFragment;
import shehan.com.migrainetrigger.view.fragment.main.SeverityFragment;

public class MainActivity
        extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FAQTopicsFragment.OnTopicSelectedListener {

    private FloatingActionButton fab;
    private int lastFragment;

    //region activity default

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
//        int count = getFragmentManager().getBackStackEntryCount();
//
//        if (drawer != null) {
//            if (drawer.isDrawerOpen(GravityCompat.START)) {
//                drawer.closeDrawer(GravityCompat.START);
//            } else if (count > 0) {
//                    getFragmentManager().popBackStack();
//            } else {
//                super.onBackPressed();
//            }
//        }

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
        //Settings menu
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //endregion


    //region interface implementations

    @SuppressWarnings("StatementWithEmptyBody")
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
            setFragment(new SeverityFragment(), R.string.nav_severity, View.INVISIBLE, false);

        } else if (id == R.id.nav_answers) {
            Log.d("Main-navigation", "Answers selected");
            showNotImplemented();

        } else if (id == R.id.nav_faq) {
            Log.d("Main-navigation", "F.A.Q selected");
            setFragment(new FAQTopicsFragment(), R.string.nav_f_a_q, View.INVISIBLE, false);

        } else if (id == R.id.nav_record) {
            Log.d("Main-navigation", "Record selected");
            showNotImplemented();

        } else if (id == R.id.nav_report) {
            Log.d("Main-navigation", "Report selected");
            showNotImplemented();

        } else if (id == R.id.nav_settings) {
            Log.d("Main-navigation", "Settings selected");
            showNotImplemented();

        } else if (id == R.id.nav_about) {
            Log.d("Main-navigation", "About selected");
            setFragment(new AboutFragment(), R.string.nav_about, View.INVISIBLE, false);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    /**
     * @param clickPosition clicked position of f.a.q topics
     */
    @Override
    public void onFragmentInteraction(int clickPosition) {
        Fragment sectionFragment = null;
        switch (clickPosition) {

            case 0:
                sectionFragment = new DefinitionFragment();
                Log.d("Main-FAQSelect", "DefinitionFragment");
                break;
            case 1:
                sectionFragment = new SymptomsFragment();
                Log.d("Main-FAQSelect", "SymptomsFragment");
                break;
            case 2:
                sectionFragment = new CausesFragment();
                Log.d("Main-FAQSelect", "CausesFragment");
                break;
            case 3:
                sectionFragment = new RiskFragment();
                Log.d("Main-FAQSelect", "RiskFragment");
                break;
            case 4:
                sectionFragment = new ComplicationsFragment();
                Log.d("Main-FAQSelect", "ComplicationsFragment");
                break;
            case 5:
                sectionFragment = new DiagnosisFragment();
                Log.d("Main-FAQSelect", "DiagnosisFragment");
                break;
            case 6:
                sectionFragment = new TreatmentsFragment();
                Log.d("Main-FAQSelect", "TreatmentsFragment");
                break;
            case 7:
                sectionFragment = new RemediesFragment();
                Log.d("Main-FAQSelect", "RemediesFragment");
                break;
            case 8:
                sectionFragment = new AlternativeFragment();
                Log.d("Main-FAQSelect", "AlternativeFragment");
                break;
            case 9:
                sectionFragment = new PreventionFragment();
                Log.d("Main-FAQSelect", "PreventionFragment");
                break;
        }

        if (sectionFragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            fragmentTransaction.replace(R.id.container_body, sectionFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            Log.d("Main-FAQSelect", "Section shown :" + fragmentManager.toString());
        }
    }

    //endregion

    //region methods

    /**
     * Floating action button behaviour
     */
    private void fabSetup() {

        fab = (FloatingActionButton) findViewById(R.id.fab);

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

        //TODO : fix fragment overlapping bug -> suggested fix >> move F.A.Q fragment to new activity


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
        Toast toast = Toast.makeText(this, "Feature not implemented", Toast.LENGTH_SHORT);
        toast.show();
    }

    //endregion
}
