package shehan.com.migrainetrigger.view.activity;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.utility.AppUtil;

/**
 * Created by Shehan on 12/05/2016.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setCustomTheme();
        super.onCreate(savedInstanceState);
    }

    /**
     * Set custom theme
     */
    protected void setCustomTheme() {
        String theme = PreferenceManager.getDefaultSharedPreferences(this).getString("pref_appTheme", "Light");
        String colorScheme = PreferenceManager.getDefaultSharedPreferences(this).getString("pref_appColor", "Red on yellow");
        switch (theme) {
            case "Light":

                switch (colorScheme) {
                    case "Red on yellow":
                        setTheme(R.style.AppThemeLight_RedOnYellow);
                        break;
                    case "Violet on pink":
                        setTheme(R.style.AppThemeLight_VioletOnPink);
                        break;
                    case "Orange on red":
                        setTheme(R.style.AppThemeLight_OrangeOnRed);
                        break;
                    case "Violet on sky blue":
                        setTheme(R.style.AppThemeLight_VioletOnSkyBlue);
                        break;
                    case "Pink on teal":
                        setTheme(R.style.AppThemeLight_PinkOnTeal);
                        break;
                    case "Pink on violet":
                        setTheme(R.style.AppThemeLight_PinkOnViolet);
                        break;
                    default:
                        AppUtil.showToast(this, "Color scheme not found, using default light scheme");
                        setTheme(R.style.AppThemeLight_RedOnYellow);
                        break;
                }
                break;

            case "Dark":
                switch (colorScheme) {
                    case "Red on yellow":
                        setTheme(R.style.AppThemeDark_RedOnYellow);
                        break;
                    case "Violet on pink":
                        setTheme(R.style.AppThemeDark_VioletOnPink);
                        break;
                    case "Orange on red":
                        setTheme(R.style.AppThemeDark_OrangeOnRed);
                        break;
                    case "Violet on sky blue":
                        setTheme(R.style.AppThemeDark_VioletOnSkyBlue);
                        break;
                    case "Pink on teal":
                        setTheme(R.style.AppThemeDark_PinkOnTeal);
                        break;
                    case "Pink on violet":
                        setTheme(R.style.AppThemeDark_PinkOnViolet);
                        break;
                    default:
                        AppUtil.showToast(this, "Color scheme not found, using default dark scheme");
                        setTheme(R.style.AppThemeDark_RedOnYellow);
                        break;
                }
                break;
            default:
                AppUtil.showToast(this, "Theme not found, using default theme");
                setTheme(R.style.AppThemeLight_RedOnYellow);
                break;

        }

    }
}