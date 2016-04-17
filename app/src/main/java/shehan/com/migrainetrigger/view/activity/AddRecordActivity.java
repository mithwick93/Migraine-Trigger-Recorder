package shehan.com.migrainetrigger.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.view.fragment.record.AddRecordBasicFragment;
import shehan.com.migrainetrigger.view.fragment.record.AddRecordFullFragment;
import shehan.com.migrainetrigger.view.fragment.record.AddRecordIntermediateFragment;

public class AddRecordActivity
        extends AppCompatActivity
        implements AddRecordBasicFragment.AddRecordBasicListener,AddRecordIntermediateFragment.AddRecordIntermediateListener,AddRecordFullFragment.AddRecordFullListener{

    private int levelOfInformation;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.add_record_toolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("New Migraine record");
        }

        initialSetup();
        loadRecordContent();
    }

    @Override
    public void onBackPressed() {
        Toast toast = Toast.makeText(this, "Record discarded", Toast.LENGTH_LONG);
        toast.show();
        super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_record_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Settings menu
        int id = item.getItemId();

        if (id == R.id.action_finish) {
            showToast("Feature not implemented");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void initialSetup() {
        levelOfInformation = getIntent().getIntExtra("levelOfInformation", 1);

        Log.d("AddRecordAct-init","levelOfInformation : "+levelOfInformation);
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
            Log.d("AddRecordAct-init","record fragment type selected  : "+fragment.toString());
        }else{
            Log.e("AddRecordAct-init","record fragment : null");
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.new_record_container, fragment);
        fragmentTransaction.commit();
        if (fragment != null) {
            showToast(fragment.toString()+ " record selected");
        }

    }

    private void loadRecordContent() {
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
    public void onBasicRecordInteraction() {

    }

    @Override
    public void onFullRecordInteraction() {

    }

    @Override
    public void onIntermediateRecordInteraction() {

    }
}
