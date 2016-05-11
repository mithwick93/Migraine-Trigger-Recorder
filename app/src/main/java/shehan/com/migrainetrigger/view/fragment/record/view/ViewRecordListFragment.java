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
import android.widget.Toast;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.controller.RecordController;
import shehan.com.migrainetrigger.view.adapter.RecordViewAdapter;
import shehan.com.migrainetrigger.view.model.RecordViewData;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewRecordListFragment extends Fragment
        implements RecordViewAdapter.RecordListViewClickListener {

    private Toast mToast;
    private View mView;
    private RecordListListener mCallback;

    public ViewRecordListFragment() {
        // Required empty public constructor
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
    public void onAttach(Context context) {

        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (RecordListListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement RecordListListener");
        }
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

    private void showToast(String message) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        mToast.show();
    }


    @Override
    public void recordListItemClicked(int recordId) {
        mCallback.onRecordListCallBack(recordId);
    }

    //Parent activity must implement this interface to communicate
    public interface RecordListListener {
        void onRecordListCallBack(int recordId);
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

            RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.record_list_recycler_view);

            // 2. set layoutManger
            recyclerView.setLayoutManager(new LinearLayoutManager(ViewRecordListFragment.this.getActivity()));

            // 3. create an adapter
            RecordViewAdapter recordViewAdapter = new RecordViewAdapter(ViewRecordListFragment.this, recordViewData);

            // 4. set adapter
            recyclerView.setAdapter(recordViewAdapter);

            // 5. set item animator to DefaultAnimator
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            showToast("Showing newest first");

        }
    }
}
