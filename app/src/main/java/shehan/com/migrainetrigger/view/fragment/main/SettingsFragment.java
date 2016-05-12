package shehan.com.migrainetrigger.view.fragment.main;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import shehan.com.migrainetrigger.R;

/**
 * Created by Shehan on 12/05/2016.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        ListPreference listPreference = (ListPreference) findPreference("pref_appTheme");
        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Toast.makeText(getActivity(), "Relaunch application for changes to take effect ", Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }

}