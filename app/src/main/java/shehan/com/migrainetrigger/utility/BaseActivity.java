package shehan.com.migrainetrigger.utility;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import shehan.com.migrainetrigger.R;

/**
 * Created by Shehan on 12/05/2016.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (PreferenceManager.getDefaultSharedPreferences(this).getString("pref_appTheme", "light_theme").equals("dark_theme")) {
            setTheme(R.style.AppTheme_Dark);
        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
    }
}