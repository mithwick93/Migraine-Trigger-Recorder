package shehan.com.migrainetrigger.view.fragment.record.view;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.controller.RecordController;
import shehan.com.migrainetrigger.utility.AppUtil;
import shehan.com.migrainetrigger.view.adapter.RecordViewAdapter;
import shehan.com.migrainetrigger.view.model.RecordViewData;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewRecordListFragment extends Fragment
        implements RecordViewAdapter.RecordListViewRowClickListener {

    private RecordListFragmentListener mCallback;
    private volatile Menu mMenu;
    private View mView;

    public ViewRecordListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (RecordListFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement RecordListFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_view_record_list, container, false);
        setHasOptionsMenu(true);

        new GetRecordListTask(mView).execute();//Load records to list view
        return mView;
    }

    @Override
    public void onResume() {
        Log.d("ViewRecordListFragment", "onResume");
        super.onResume();
        //update
        if (mView != null) {
            new GetRecordListTask(mView).execute();//Load records to list view
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //Show add new answer on toolbar
        mMenu = menu;
        inflater.inflate(R.menu.view_record_list_menu, menu);
        if (mMenu != null) {
            mMenu.findItem(R.id.action_filter).setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_filter) {
            initiateFiltering();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initiateFiltering() {
        Log.d("ViewRecordListFragment", "initiateFiltering");
        /*
        1 Show filter dialog
        2 Section wise selectable separately
        3 Sections collapsible
        4 On ok return array list of string array list of size 7
        5 Start async task with this filter list


         */
    }

    @Override
    public void onRecordListRowClicked(int recordId) {
        mCallback.onRecordListRequest(recordId);
    }


    //Parent activity must implement this interface to communicate
    public interface RecordListFragmentListener {
        void onRecordListRequest(int request);
    }

    /**
     * Async task to filter records
     */
    private class GetRecordFilteredListTask extends AsyncTask<Void, Void, RecordViewData[]> {
        private ArrayList<ArrayList<String>> filterList;
        private ProgressDialog nDialog;

        public GetRecordFilteredListTask(@NotNull ArrayList<ArrayList<String>> filterList) {
            this.filterList = filterList;
        }

        @Override
        protected RecordViewData[] doInBackground(Void... v) {
            Log.d("GetRecordFilteredList", " doInBackground - query filters");

            return RecordController.getRecordViewDataFiltered(filterList);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(getActivity());
            nDialog.setMessage("Filtering records...");
            nDialog.setTitle("Processing");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(false);
            nDialog.show();
        }

        @Override
        protected void onPostExecute(RecordViewData recordViewData[]) {
            Log.d("GetRecordFilteredList", " onPostExecute - update ui");

            if (nDialog != null) {
                nDialog.dismiss();
            }

            if (recordViewData.length == 0) {//Records in db
                AppUtil.showToast(ViewRecordListFragment.this.getContext(), "No records found for the search");
            }

            RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.record_list_recycler_view);

            recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());

            // 2. set layoutManger
            recyclerView.setLayoutManager(new LinearLayoutManager(ViewRecordListFragment.this.getActivity()));

            if (recyclerView.getAdapter() != null) {
                RecordViewAdapter tmpAdapter = (RecordViewAdapter) recyclerView.getAdapter();
                if (tmpAdapter != null) {
                    tmpAdapter.setData(recordViewData);
                }
            } else {

                // 3. create an adapter
                RecordViewAdapter recordViewAdapter = new RecordViewAdapter(ViewRecordListFragment.this, recordViewData);

                // 4. set adapter
                recyclerView.setAdapter(recordViewAdapter);

                // 5. set item animator to DefaultAnimator
                recyclerView.setItemAnimator(new DefaultItemAnimator());
            }
            //AppUtil.showToast(ViewRecordListFragment.this.getContext(), "Showing newest first");

        }
    }

    /**
     * Async task to initialize query db to get records
     */
    private class GetRecordListTask extends AsyncTask<String, Void, RecordViewData[]> {

        private View mView;

        GetRecordListTask(View mView) {
            this.mView = mView;
        }


        @Override
        protected RecordViewData[] doInBackground(String... params) {
            Log.d("GetRecordList", " doInBackground - query records");

            return RecordController.getRecordViewData();
        }

        @Override
        protected void onPostExecute(RecordViewData recordViewData[]) {
            Log.d("GetRecordList", " onPostExecute - update ui");

            if (recordViewData.length == 0) {//Records in db
                AppUtil.showToast(ViewRecordListFragment.this.getContext(), "No records found in database");
                mCallback.onRecordListRequest(-1);
                return;
            }

            RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.record_list_recycler_view);

            recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());

            // 2. set layoutManger
            recyclerView.setLayoutManager(new LinearLayoutManager(ViewRecordListFragment.this.getActivity()));

            if (recyclerView.getAdapter() != null) {
                RecordViewAdapter tmpAdapter = (RecordViewAdapter) recyclerView.getAdapter();
                if (tmpAdapter != null) {
                    tmpAdapter.setData(recordViewData);
                }
            } else {

                // 3. create an adapter
                RecordViewAdapter recordViewAdapter = new RecordViewAdapter(ViewRecordListFragment.this, recordViewData);

                // 4. set adapter
                recyclerView.setAdapter(recordViewAdapter);

                // 5. set item animator to DefaultAnimator
                recyclerView.setItemAnimator(new DefaultItemAnimator());
            }
            AppUtil.showToast(ViewRecordListFragment.this.getContext(), "Showing newest first");

        }
    }
}
