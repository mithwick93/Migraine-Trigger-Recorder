package shehan.com.migrainetrigger.view.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gordonwong.materialsheetfab.MaterialSheetFab;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.controller.RecordController;
import shehan.com.migrainetrigger.utility.AppUtil;
import shehan.com.migrainetrigger.utility.customview.SheetFab;
import shehan.com.migrainetrigger.view.fragment.main.AboutFragment;
import shehan.com.migrainetrigger.view.fragment.main.HomeFragment;
import shehan.com.migrainetrigger.view.fragment.main.ManageAnswersFragment;
import shehan.com.migrainetrigger.view.fragment.main.SeverityFragment;

public class MainActivity
        extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, ManageAnswersFragment.ManageAnswersFragmentListener, View.OnClickListener {

    private static final boolean DEVELOPER_MODE = false;

    private SheetFab fab;
    private boolean isRecordsAvailable;
    private MaterialSheetFab materialSheetFab;

    @Override
    public void OnAnswerRawClick(final String answer) {

        new AsyncTask<String, Void, Boolean>() {
            Intent intent;

            @Override
            protected Boolean doInBackground(String... params) {
                intent = new Intent(MainActivity.this, ManageAnswersActivity.class);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {
                    intent.putExtra("answerSection", answer);
                    Log.d("Main-navigation", "Launching manage answers activity");
                    startActivity(intent);
                }
            }
        }.execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (materialSheetFab != null && materialSheetFab.isSheetVisible()) {
            materialSheetFab.hideSheet();
        } else if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public void onResume() {
        setCustomTheme();
        new CheckRecordsTask().execute();
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        //Toast.makeText(this, R.string.sheet_item_pressed, Toast.LENGTH_SHORT).show();
        materialSheetFab.hideSheet();

        TextView textView = (TextView) v;
        int which = 0;
        if (textView.getText().equals("Basic")) {
            which = 0;
        } else if (textView.getText().equals("Intermediate")) {
            which = 1;
        } else if (textView.getText().equals("Full")) {
            which = 2;
        }

        Intent intent = new Intent(MainActivity.this, AddRecordActivity.class);
        intent.putExtra("levelOfInformation", which);
        Log.d("MainActivity-fab-dialog", "Launching new record activity");
        startActivity(intent);
    }

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
            HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.nav_home));
            if (homeFragment != null && homeFragment.isVisible()) {
                AppUtil.showToast(MainActivity.this, "Home already selected");
            } else {
                new AsyncTask<String, Void, Boolean>() {
                    HomeFragment homeFragment;

                    @Override
                    protected Boolean doInBackground(String... params) {
                        Log.d("Main activity", "Loading home fragment");
                        homeFragment = new HomeFragment();
                        return true;
                    }

                    @Override
                    protected void onPostExecute(Boolean result) {
                        if (result) {
                            setFragment(homeFragment, R.string.nav_home, false);
                        }
                    }
                }.execute();
            }

        } else if (id == R.id.nav_severity) {
            Log.d("Main-navigation", "Severity selected");

            SeverityFragment severityFragment = (SeverityFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.nav_severity));
            if (severityFragment != null && severityFragment.isVisible()) {
                AppUtil.showToast(MainActivity.this, "Severity already selected");
            } else {
                new AsyncTask<String, Void, Boolean>() {
                    SeverityFragment severityFragment;

                    @Override
                    protected Boolean doInBackground(String... params) {
                        Log.d("Main activity", "Loading severity fragment");
                        severityFragment = new SeverityFragment();
                        return true;
                    }

                    @Override
                    protected void onPostExecute(Boolean result) {
                        if (result) {
                            setFragment(severityFragment, R.string.nav_severity, true);
                        }
                    }
                }.execute();

            }
        } else if (id == R.id.nav_answers) {
            Log.d("Main-navigation", "Answers selected");
            ManageAnswersFragment answersFragment = (ManageAnswersFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.nav_answers));
            if (answersFragment != null && answersFragment.isVisible()) {
                AppUtil.showToast(MainActivity.this, "Answers already selected");
            } else {
                new AsyncTask<String, Void, Boolean>() {
                    ManageAnswersFragment manageAnswersFragment;

                    @Override
                    protected Boolean doInBackground(String... params) {
                        Log.d("Main activity", "Loading answers fragment");
                        manageAnswersFragment = new ManageAnswersFragment();
                        return true;
                    }

                    @Override
                    protected void onPostExecute(Boolean result) {
                        if (result) {
                            setFragment(manageAnswersFragment, R.string.nav_answers, true);
                        }
                    }
                }.execute();
            }

        } else if (id == R.id.nav_faq) {
            Log.d("Main-navigation", "F.A.Q selected");
            new AsyncTask<String, Void, Boolean>() {
                Intent intent;

                @Override
                protected Boolean doInBackground(String... params) {
                    Log.d("Main activity", "Loading FAQ activity");
                    intent = new Intent(MainActivity.this, FAQActivity.class);
                    return true;
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    if (result) {
                        Log.d("Main-navigation", "Launching F.A.Q activity");
                        startActivity(intent);
                    }
                }
            }.execute();

        } else if (id == R.id.nav_record) {
            Log.d("Main-navigation", "Record selected");
            if (isRecordsAvailable) {
                new AsyncTask<String, Void, Boolean>() {
                    Intent intent;

                    @Override
                    protected Boolean doInBackground(String... params) {
                        Log.d("Main activity", "Loading view records activity");
                        intent = new Intent(MainActivity.this, ViewRecordsActivity.class);
                        return true;
                    }

                    @Override
                    protected void onPostExecute(Boolean result) {
                        if (result) {
                            Log.d("Main-navigation", "Launching view record activity");
                            startActivity(intent);
                        }
                    }
                }.execute();
            } else {
                AppUtil.showToast(this, "No records found");
            }

        } else if (id == R.id.nav_report) {
            Log.d("Main-navigation", "Report selected");
            if (isRecordsAvailable) {
                new AsyncTask<String, Void, Boolean>() {
                    Intent intent;

                    @Override
                    protected Boolean doInBackground(String... params) {
                        Log.d("Main activity", "Loading reports activity");
                        intent = new Intent(MainActivity.this, ReportActivity.class);
                        return true;
                    }

                    @Override
                    protected void onPostExecute(Boolean result) {
                        if (result) {
                            Log.d("Main-navigation", "Launching report activity");
                            startActivity(intent);
                        }
                    }
                }.execute();
            } else {
                AppUtil.showToast(this, "Not enough records found");
            }

        } else if (id == R.id.nav_settings) {
            Log.d("Main-navigation", "Settings selected");
            new AsyncTask<String, Void, Boolean>() {
                Intent intent;

                @Override
                protected Boolean doInBackground(String... params) {
                    Log.d("Main activity", "Loading settings activity");
                    intent = new Intent(MainActivity.this, SettingsActivity.class);
                    return true;
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    if (result) {
                        Log.d("Main-navigation", "Launching settings activity");
                        startActivity(intent);
                    }
                }
            }.execute();

        } else if (id == R.id.nav_about) {
            Log.d("Main-navigation", "About selected");

            AboutFragment aboutFragment = (AboutFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.nav_about));
            if (aboutFragment != null && aboutFragment.isVisible()) {
                AppUtil.showToast(MainActivity.this, "About already selected");
            } else {
                new AsyncTask<String, Void, Boolean>() {
                    AboutFragment aboutFragment;

                    @Override
                    protected Boolean doInBackground(String... params) {
                        Log.d("Main activity", "Loading about fragment");
                        aboutFragment = new AboutFragment();
                        return true;
                    }

                    @Override
                    protected void onPostExecute(Boolean result) {
                        if (result) {
                            setFragment(aboutFragment, R.string.nav_about, true);
                        }
                    }
                }.execute();
            }
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

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
            new AsyncTask<String, Void, Boolean>() {
                HomeFragment homeFragment;

                @Override
                protected Boolean doInBackground(String... params) {
                    Log.d("Main activity", "Loading home fragment");
                    homeFragment = new HomeFragment();
                    return true;
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    if (result) {
                        setFragment(homeFragment, R.string.nav_home, false);
                    }
                }
            }.execute();

        }

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        Log.d("Main-onCreate", "onCreate success");
    }

    /**
     * Floating action button behaviour
     */
    private void fabSetup() {

        SheetFab fab = (SheetFab) findViewById(R.id.fab);
        View sheetView = findViewById(R.id.fab_sheet);
        View overlay = findViewById(R.id.overlay);

        int sheetColor = fetchResourceColor(1);
        int fabColor = fetchResourceColor(0);

        // Create material sheet FAB
        assert fab != null;
        assert sheetView != null;
        assert overlay != null;
        materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay, sheetColor, fabColor);

        // Set material sheet item click listeners
        View txtBasic = findViewById(R.id.fab_sheet_item_basic);
        assert txtBasic != null;
        txtBasic.setOnClickListener(this);

        View txtInter = findViewById(R.id.fab_sheet_item_intermediate);
        assert txtInter != null;
        txtInter.setOnClickListener(this);

        View txtFull = findViewById(R.id.fab_sheet_item_full);
        assert txtFull != null;
        txtFull.setOnClickListener(this);


    }

    private int fetchResourceColor(int index) {
        TypedValue typedValue = new TypedValue();

        TypedArray a = this.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorAccent, android.R.attr.colorBackground});
        int color = a.getColor(index, 0);

        a.recycle();

        return color;
    }

    /**
     * Change fragment in main activity
     *  @param fragment      fragment to show
     * @param toolBarTitle  tool bar title
     */
    private void setFragment(Fragment fragment,
                             int toolBarTitle,
                             boolean addToBackStack) {

        String tag = getString(toolBarTitle);


        final FragmentManager fragmentManager = getSupportFragmentManager();

        final int newBackStackLength = fragmentManager.getBackStackEntryCount() + 1;

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.container_body, fragment, tag);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(tag);
        }
        fragmentTransaction.commit();

        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                int nowCount = fragmentManager.getBackStackEntryCount();
                if (newBackStackLength != nowCount) {
                    // we don't really care if going back or forward. we already performed the logic here.
                    fragmentManager.removeOnBackStackChangedListener(this);

                    if (newBackStackLength > nowCount) { // user pressed back
                        fragmentManager.popBackStackImmediate();
                    }
                }
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(toolBarTitle);
        }

//        if (fab != null) {
//            fab.setVisibility(fabVisibility);
//        }

    }

    /**
     * Async task to check if reports can be generated
     */
    private class CheckRecordsTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            Log.d("CheckRecordsTask", "doInBackground ");
            return RecordController.getLastId();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            Log.d("CheckRecordsTask", "onPostExecute ");

            isRecordsAvailable = integer != -1;

        }
    }
}
