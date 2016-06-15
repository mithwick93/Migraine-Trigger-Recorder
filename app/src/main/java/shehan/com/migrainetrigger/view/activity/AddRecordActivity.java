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
import shehan.com.migrainetrigger.utility.AppUtil;
import shehan.com.migrainetrigger.view.fragment.record.add.AddRecordBasicFragment;
import shehan.com.migrainetrigger.view.fragment.record.add.AddRecordFullFragment;
import shehan.com.migrainetrigger.view.fragment.record.add.AddRecordIntermediateFragment;

public class AddRecordActivity
        extends BaseActivity
        implements AddRecordBasicFragment.AddRecordBasicFragmentListener, AddRecordIntermediateFragment.AddRecordIntermediateFragmentListener, AddRecordFullFragment.AddRecordFullFragmentListener {

    private static final boolean DEVELOPER_MODE = false;
    private AddRecordBasicFragment mFragment;

    @Override
    public void onAddRecordBasicRequest(int request) {
        Log.d("AddRecordActivity", "onAddRecordBasicRequest request : " + request);
        if (request == 0) {
            AddRecordActivity.super.onBackPressed();
        }
    }

    @Override
    public void onAddRecordFullRequest(int request) {
        Log.d("AddRecordActivity", "onAddRecordFullRequest request : " + request);
        if (request == 0) {
            AddRecordActivity.super.onBackPressed();
        }
    }

    @Override
    public void onAddRecordIntermediateRequest(int request) {
        Log.d("AddRecordActivity", "onAddRecordIntermediateRequest request : " + request);
        if (request == 0) {
            AddRecordActivity.super.onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        new MaterialDialog.Builder(this)
                .title("Discard new record")
                .content("Do you want to discard the new Migraine record ?")
                .positiveText("Discard")
                .negativeText(R.string.cancelButtonGeneral)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        AppUtil.showToast(AddRecordActivity.this, "Record discarded");
                        AddRecordActivity.super.onBackPressed();
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
        setContentView(R.layout.activity_add_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.add_record_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("New Migraine record");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initialSetup();
    }

    /**
     * Floating action button behaviour
     */
    private void fabSetup() {

        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);

        if (fabAdd != null) {
            fabAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("AddRecordActivity", "sending accept action");
                    if (mFragment != null) {
                        mFragment.recordAcceptAction();
                    }
                }
            });
        }
    }

    /**
     * Setup record add activity
     */
    private void initialSetup() {
        int levelOfInformation = getIntent().getIntExtra("levelOfInformation", 1);
        Log.d("AddRecordAct-init", "levelOfInformation : " + levelOfInformation);

        mFragment = null;
        switch (levelOfInformation) {
            case 0:
                mFragment = new AddRecordBasicFragment();
                break;
            case 1:
                mFragment = new AddRecordIntermediateFragment();
                break;
            case 2:
                mFragment = new AddRecordFullFragment();
                break;
        }

        if (mFragment != null) {
            Log.d("AddRecordAct-init", "record fragment type selected  : " + mFragment.toString());
        } else {
            Log.e("AddRecordAct-init", "record fragment : null");
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.new_record_container, mFragment);
        fragmentTransaction.commit();
        if (mFragment != null) {
            AppUtil.showToast(AddRecordActivity.this, mFragment.toString() + " record selected");
        }

        fabSetup();
    }
}
