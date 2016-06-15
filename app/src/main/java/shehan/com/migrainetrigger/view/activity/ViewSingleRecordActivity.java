package shehan.com.migrainetrigger.view.activity;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.view.fragment.record.view.ViewRecordSingleFragment;

public class ViewSingleRecordActivity
        extends BaseActivity
        implements ViewRecordSingleFragment.SingleRecordViewFragmentListener {

    private static final boolean DEVELOPER_MODE = false;
    private ViewRecordSingleFragment mViewRecordSingleFragment;

    @Override
    public void onBackPressed() {
        new MaterialDialog.Builder(this)
                .title("Discard record changes")
                .content("Do you want to discard the changes made to the record ?")
                .positiveText("Discard")
                .negativeText(R.string.cancelButtonGeneral)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //AppUtil.showToast(ViewSingleRecordActivity.this, "Record discarded");
                        ViewSingleRecordActivity.super.onBackPressed();
                    }
                })
                .show();

    }

    @Override
    public void onResume() {
        setCustomTheme();
        super.onResume();
    }

    @Override
    public void onSingleRecordViewRequest(int request) {
        Log.d("ViewSingleRecord", "onTopicRawClicked request : " + request);
        if (request == 0) {
            ViewSingleRecordActivity.super.onBackPressed();
            //notify view records activity that delete happened , so update view
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
        setContentView(R.layout.activity_view_single_record);

        Toolbar toolbar = (Toolbar) findViewById(R.id.view_single_record_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("View record");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initialSetup();
    }

    /**
     * Floating action button behaviour
     */
    private void fabSetup() {

        FloatingActionButton fabUpdate = (FloatingActionButton) findViewById(R.id.fab_update);

        if (fabUpdate != null) {
            fabUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("ViewSingleRecord", "sending Update action");
                    if (mViewRecordSingleFragment != null) {
                        mViewRecordSingleFragment.updateRecord();
                    }
                }
            });
        }
    }

    /**
     * Setup record view UI
     */
    private void initialSetup() {
        int recordId = getIntent().getIntExtra("recordId", -1);//get passed record ID
        Log.d("ViewSingleRecord", "recordId : " + recordId);

        mViewRecordSingleFragment = ViewRecordSingleFragment.newInstance(recordId);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.single_record_container, mViewRecordSingleFragment);
        fragmentTransaction.commit();
        fabSetup();

    }

}
