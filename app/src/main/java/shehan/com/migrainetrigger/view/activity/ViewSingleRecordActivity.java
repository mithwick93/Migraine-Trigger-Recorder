package shehan.com.migrainetrigger.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.view.fragment.record.view.ViewRecordSingleFragment;

public class ViewSingleRecordActivity
        extends AppCompatActivity
        implements ViewRecordSingleFragment.OnFragmentInteractionListener {

    private Toast mToast;
    private int recordId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_record);

        Toolbar toolbar = (Toolbar) findViewById(R.id.view_single_record_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("View record");
        }

        initialSetup();
    }


    private void initialSetup() {
        recordId = getIntent().getIntExtra("recordId", -1);
        Log.d("ViewSingleRecord", "recordId : " + recordId);

        Fragment fragment = ViewRecordSingleFragment.newInstance(recordId);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.single_record_container, fragment);
        fragmentTransaction.commit();
        if (fragment != null) {
            showToast("Selected record Id : " + recordId);
        }

    }

    @Override
    public void onFragmentInteraction(int request) {

    }

    private void showToast(String message) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        mToast.show();
    }
}
