package shehan.com.migrainetrigger.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.view.fragment.record.view.ViewRecordCalenderFragment;
import shehan.com.migrainetrigger.view.fragment.record.view.ViewRecordListFragment;

public class ViewRecordsActivity
        extends AppCompatActivity
        implements ViewRecordListFragment.RecordListListener, ViewRecordCalenderFragment.RecordCalenderListener {

    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_records);
        Toolbar toolbar = (Toolbar) findViewById(R.id.view_record_toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("List"));
        tabLayout.addTab(tabLayout.newTab().setText("Calender"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

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
        }
    }


    @Override
    public String toString() {
        return "View records";
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
    public void onRecordListCallBack(int recordId) {
        showSingleRecordActivity(recordId);
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

    public static class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;
        ViewRecordListFragment viewRecordListFragment;
        ViewRecordCalenderFragment viewRecordCalenderFragment;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
            this.viewRecordListFragment = new ViewRecordListFragment();
            this.viewRecordCalenderFragment = new ViewRecordCalenderFragment();
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

        @Override
        public int getCount() {
            return mNumOfTabs;
        }


    }
}
