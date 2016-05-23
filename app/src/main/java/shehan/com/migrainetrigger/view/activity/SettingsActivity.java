package shehan.com.migrainetrigger.view.activity;

import android.os.Bundle;
import android.os.StrictMode;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.utility.AppUtil;
import shehan.com.migrainetrigger.utility.BaseActivity;
import shehan.com.migrainetrigger.view.fragment.main.SettingsFragment;

/**
 * Created by Shehan on 12/05/2016.
 */
public class SettingsActivity extends BaseActivity implements SettingsFragment.ThemeChangeListener {
    private static final boolean DEVELOPER_MODE = true;

    @Override
    public void onBackPressed() {
        AppUtil.showToast(this, "Please click action bar back button");
    }

    @Override
    public void onResume() {
        setCustomTheme();
        super.onResume();
    }

    @Override
    public void onThemeChanged() {
        setCustomTheme();
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
        setContentView(R.layout.activity_settings);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Settings");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        SettingsFragment settingsFragment = new SettingsFragment();
        settingsFragment.setThemeChangeListener(this);
        getFragmentManager().beginTransaction()
                .replace(R.id.settings_container, settingsFragment)
                .commit();
    }
}
