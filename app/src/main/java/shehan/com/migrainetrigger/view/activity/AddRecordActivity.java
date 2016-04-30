package shehan.com.migrainetrigger.view.activity;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.view.fragment.record.add.AddRecordBasicFragment;
import shehan.com.migrainetrigger.view.fragment.record.add.AddRecordFullFragment;
import shehan.com.migrainetrigger.view.fragment.record.add.AddRecordIntermediateFragment;

public class AddRecordActivity
        extends AppCompatActivity
        implements AddRecordBasicFragment.AddRecordBasicListener, AddRecordIntermediateFragment.AddRecordIntermediateListener, AddRecordFullFragment.AddRecordFullListener {

    private static final boolean DEVELOPER_MODE = true;

    private int levelOfInformation;
    private Toast mToast;

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
        }

        initialSetup();
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
                        showToast("Record discarded");
                        AddRecordActivity.super.onBackPressed();
                    }
                })
                .show();

    }

    private void initialSetup() {
        levelOfInformation = getIntent().getIntExtra("levelOfInformation", 1);
        Log.d("AddRecordAct-init", "levelOfInformation : " + levelOfInformation);

        Fragment fragment = null;
        switch (levelOfInformation) {
            case 0:
                fragment = new AddRecordBasicFragment();
                break;
            case 1:
                fragment = new AddRecordIntermediateFragment();
                break;
            case 2:
                fragment = new AddRecordFullFragment();
                break;
        }

        if (fragment != null) {
            Log.d("AddRecordAct-init", "record fragment type selected  : " + fragment.toString());
        } else {
            Log.e("AddRecordAct-init", "record fragment : null");
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.new_record_container, fragment);
        fragmentTransaction.commit();
        if (fragment != null) {
            showToast(fragment.toString() + " record selected");
        }

    }

    private void showToast(String message) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        mToast.show();
    }

    @Override
    public void onBasicRecordInteraction(int request) {
        Log.d("AddRecordActivity", "onBasicRecordInteraction request : " + request);
        if (request == 0) {
            AddRecordActivity.super.onBackPressed();
        }
    }

    @Override
    public void onFullRecordInteraction(int request) {
        Log.d("AddRecordActivity", "onFullRecordInteraction request : " + request);
        if (request == 0) {
            AddRecordActivity.super.onBackPressed();
        }
    }

    @Override
    public void onIntermediateRecordInteraction(int request) {
        Log.d("AddRecordActivity", "onIntermediateRecordInteraction request : " + request);
        if (request == 0) {
            AddRecordActivity.super.onBackPressed();
        }
    }
}
