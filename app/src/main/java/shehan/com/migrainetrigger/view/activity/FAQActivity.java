package shehan.com.migrainetrigger.view.activity;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.utility.AppUtil;
import shehan.com.migrainetrigger.view.fragment.faq.FAQTopicsFragment;
import shehan.com.migrainetrigger.view.fragment.faq.WebViewFragment;

public class FAQActivity
        extends BaseActivity
        implements FAQTopicsFragment.TopicSelectedListener {

    private static final boolean DEVELOPER_MODE = false;

    @Override
    public void onResume() {
        setCustomTheme();
        super.onResume();
    }

    /**
     * Change content of faq according to item selected
     *
     * @param clickPosition clicked position of f.a.q topics
     */
    @Override
    public void onTopicRawClicked(int clickPosition) {
        WebViewFragment webViewFragment = null;
        switch (clickPosition) {

            case 0:
                webViewFragment = WebViewFragment.newInstance("Definition");
                Log.d("FAQ-FAQSelect", "Definition");
                break;
            case 1:
                webViewFragment = WebViewFragment.newInstance("Symptoms");
                Log.d("FAQ-FAQSelect", "Symptoms");
                break;
            case 2:
                webViewFragment = WebViewFragment.newInstance("Causes");
                Log.d("FAQ-FAQSelect", "Causes");
                break;
            case 3:
                webViewFragment = WebViewFragment.newInstance("Risk");
                Log.d("FAQ-FAQSelect", "Risk");
                break;
            case 4:
                webViewFragment = WebViewFragment.newInstance("Complications");
                Log.d("FAQ-FAQSelect", "Complications");
                break;
            case 5:
                webViewFragment = WebViewFragment.newInstance("Diagnosis");
                Log.d("FAQ-FAQSelect", "Diagnosis");
                break;
            case 6:
                webViewFragment = WebViewFragment.newInstance("Treatments");
                Log.d("FAQ-FAQSelect", "Treatments");
                break;
            case 7:
                webViewFragment = WebViewFragment.newInstance("Remedies");
                Log.d("FAQ-FAQSelect", "Remedies");
                break;
            case 8:
                webViewFragment = WebViewFragment.newInstance("Alternative");
                Log.d("FAQ-FAQSelect", "Alternative");
                break;
            case 9:
                webViewFragment = WebViewFragment.newInstance("Prevention");
                Log.d("FAQ-FAQSelect", "Prevention");
                break;
        }

        if (webViewFragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            fragmentTransaction.replace(R.id.faq_container, webViewFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            Log.d("FAQ-FAQSelect", "Section shown : " + fragmentManager.toString());
        }
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
        setContentView(R.layout.activity_faq);

        Toolbar toolbar = (Toolbar) findViewById(R.id.faq_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.nav_f_a_q);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        showFAQFragment();
    }

    private void showFAQFragment() {
        Fragment fragment = new FAQTopicsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.faq_container, fragment);
        fragmentTransaction.commit();
        AppUtil.showToast(this, "Content provided by mayoclinic.org");
    }
}
