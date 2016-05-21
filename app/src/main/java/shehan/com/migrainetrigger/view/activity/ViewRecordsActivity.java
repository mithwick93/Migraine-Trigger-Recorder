package shehan.com.migrainetrigger.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.utility.BaseActivity;
import shehan.com.migrainetrigger.view.fragment.record.view.ViewRecordCalenderFragment;
import shehan.com.migrainetrigger.view.fragment.record.view.ViewRecordListFragment;

public class ViewRecordsActivity
        extends BaseActivity
        implements ViewRecordListFragment.RecordListListener, ViewRecordCalenderFragment.RecordCalenderListener {

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
        setContentView(R.layout.activity_view_records);
        Toolbar toolbar = (Toolbar) findViewById(R.id.view_record_toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);


        assert tabLayout != null;
        tabLayout.addTab(tabLayout.newTab().setText("List"));
        tabLayout.addTab(tabLayout.newTab().setText("Calender"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //initialize tab layout
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter;

        adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        if (viewPager != null) {
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.nav_records);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onRecordCalenderCallBack(int recordId) {
        showSingleRecordActivity(recordId);
    }

    private void showSingleRecordActivity(int recordId) {
        Intent intent = new Intent(ViewRecordsActivity.this, ViewSingleRecordActivity.class);
        intent.putExtra("recordId", recordId);
        Log.d("ViewRecordsActivity ", "Launching view single record activity");
        startActivity(intent);
    }

    @Override
    public void onRecordListCallBack(int request) {
        if (request == -1) {
            ViewRecordsActivity.super.onBackPressed();
            return;
        }
        showSingleRecordActivity(request);
    }

    @Override
    public String toString() {
        return "View records";
    }

    /**
     * private static adapter class to host tabs
     */
    public static class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;
        ViewRecordCalenderFragment viewRecordCalenderFragment;
        ViewRecordListFragment viewRecordListFragment;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
            this.viewRecordListFragment = new ViewRecordListFragment();
            this.viewRecordCalenderFragment = new ViewRecordCalenderFragment();
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return viewRecordListFragment;
                case 1:
                    return viewRecordCalenderFragment;
                default:
                    return null;
            }
        }


    }
}
