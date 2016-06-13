package shehan.com.migrainetrigger.view.activity;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.view.fragment.main.ReportFragment;

public class ReportActivity extends BaseActivity
        implements ReportFragment.ReportFragmentListener {

    private static final boolean DEVELOPER_MODE = false;

    @Override
    public void onReportFragmentRequest(int request) {
        Log.d("ReportActivity", "onReportFragmentRequest");
        if (request == 0) {
            ReportActivity.super.onBackPressed();
        }
    }

    @Override
    public void onResume() {
        setCustomTheme();
        super.onResume();
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
        setContentView(R.layout.activity_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.report_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.nav_report);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        showFAQFragment();
    }

    private void showFAQFragment() {
        Fragment fragment = new ReportFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.report_container, fragment);
        fragmentTransaction.commit();
    }
}
