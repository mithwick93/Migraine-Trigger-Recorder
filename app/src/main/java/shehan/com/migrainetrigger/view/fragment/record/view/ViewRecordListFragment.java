package shehan.com.migrainetrigger.view.fragment.record.view;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        //        if (mView != null) {
//            new GetRecordListTask().execute();//Load records to list view
//        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
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
