package shehan.com.migrainetrigger.view.activity;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.utility.AppUtil;
import shehan.com.migrainetrigger.utility.BaseActivity;
import shehan.com.migrainetrigger.view.fragment.answer.AnswerSectionFragment;

public class ManageAnswersActivity extends BaseActivity {

    private static final boolean DEVELOPER_MODE = true;

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
        setContentView(R.layout.activity_manage_answers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.manage_answers_toolbar);
        setSupportActionBar(toolbar);

        initialSetup();
    }

    /**
     * Setup answer manage UI
     */
    private void initialSetup() {
        String answerSection = getIntent().getStringExtra("answerSection");//get answer section
        Log.d("ManageAnswersActivity", "answerSection : " + answerSection);

        AppUtil.showToast(this, answerSection);

        AnswerSectionFragment answerSectionFragment = AnswerSectionFragment.newInstance(answerSection);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.manage_answers_container, answerSectionFragment);
        fragmentTransaction.commit();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(answerSection);
        }

    }
}
