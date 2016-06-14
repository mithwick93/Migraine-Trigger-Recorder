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
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.controller.BodyAreaController;
import shehan.com.migrainetrigger.controller.LifeActivityController;
import shehan.com.migrainetrigger.controller.LocationController;
import shehan.com.migrainetrigger.controller.MedicineController;
import shehan.com.migrainetrigger.controller.RecordController;
import shehan.com.migrainetrigger.controller.ReliefController;
import shehan.com.migrainetrigger.controller.SymptomController;
import shehan.com.migrainetrigger.controller.TriggerController;
import shehan.com.migrainetrigger.data.model.BodyArea;
import shehan.com.migrainetrigger.data.model.LifeActivity;
import shehan.com.migrainetrigger.data.model.Location;
import shehan.com.migrainetrigger.data.model.Medicine;
import shehan.com.migrainetrigger.data.model.Relief;
import shehan.com.migrainetrigger.data.model.Symptom;
import shehan.com.migrainetrigger.data.model.Trigger;
import shehan.com.migrainetrigger.utility.AppUtil;
import shehan.com.migrainetrigger.view.adapter.RecordViewAdapter;
import shehan.com.migrainetrigger.view.fragment.filter.FilterDialogFragment;
import shehan.com.migrainetrigger.view.model.RecordViewData;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewRecordListFragment extends Fragment
        implements RecordViewAdapter.RecordListViewRowClickListener {

    private RecordListFragmentListener mCallback;
    private View mView;
    private ArrayList<ArrayList<String>> selectedFilters;


    public ViewRecordListFragment() {
        // Required empty public constructor
    }

    /**
     * Called by hosting activity when it relieves filter list via filter dialog
     *
     * @param filterTags Filter list
     */
    public void filterRecords(@Nullable ArrayList<ArrayList<String>> filterTags) {
        Log.d("ViewRecordListFragment", "filterRecords");

        selectedFilters = filterTags;
        if (filterTags != null && filterTags.size() == 7) {
            new GetRecordFilteredListTask(filterTags).execute();
        } else if (filterTags == null) {
            Log.d("ViewRecordListFragment", "filterRecords - filterTags null or size!=7 , resetting");
            new GetRecordListTask().execute();//Load records to list view,clear filter action
        }
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
        selectedFilters = null;
        setHasOptionsMenu(true);
        new GetRecordListTask().execute();//Load records to list view
        return mView;
    }

    @Override
    public void onResume() {
        Log.d("ViewRecordListFragment", "onResume");
        super.onResume();
        //update
        refreshRecordList();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //Show add new answer on toolbar
        inflater.inflate(R.menu.view_record_list_menu, menu);
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

    @Override
    public void onRecordListRowClicked(int recordId) {
        mCallback.onRecordListRequest(recordId);
    }

    private void initiateFiltering() {
        Log.d("ViewRecordListFragment", "initiateFiltering");
        new GetRecordFilterListTask().execute();

    }

    //Called when a record delete is detected
    private void refreshRecordList() {
        if (mView != null) {
            //update
            if (selectedFilters != null && selectedFilters.size() == 7) {
                new GetRecordFilteredListTask(selectedFilters).execute();//record deleted when filter applied , apply again
            } else {
                new GetRecordListTask().execute();//Load records to list view
            }
        }

    }


    //Parent activity must implement this interface to communicate
    public interface RecordListFragmentListener {
        void onRecordListRequest(int request);
    }

    /**
     * Async task to get filters
     */
    private class GetRecordFilterListTask extends AsyncTask<Void, Void, ArrayList<ArrayList<String>>> {

        @Override
        protected ArrayList<ArrayList<String>> doInBackground(Void... v) {
            Log.d("GetRecordFilterListTask", " doInBackground - query filters");

            ArrayList<ArrayList<String>> filterList = new ArrayList<>();

            ArrayList<BodyArea> bodyAreas = BodyAreaController.getAllBodyAreas(true);
            ArrayList<LifeActivity> activities = LifeActivityController.getAllActivities(true);
            ArrayList<Location> locations = LocationController.getAllLocations(true);
            ArrayList<Medicine> medicines = MedicineController.getAllMedicines(true);
            ArrayList<Relief> reliefs = ReliefController.getAllReliefs(true);
            ArrayList<Symptom> symptoms = SymptomController.getAllSymptoms(true);
            ArrayList<Trigger> triggers = TriggerController.getAllTriggers(true);


            ArrayList<String> bodyAreaFilters = new ArrayList<>();
            ArrayList<String> activityFilters = new ArrayList<>();
            ArrayList<String> locationFilters = new ArrayList<>();
            ArrayList<String> medicineFilters = new ArrayList<>();
            ArrayList<String> reliefFilters = new ArrayList<>();
            ArrayList<String> symptomFilters = new ArrayList<>();
            ArrayList<String> triggerFilters = new ArrayList<>();

            for (BodyArea bodyArea : bodyAreas) {
                bodyAreaFilters.add(bodyArea.getBodyAreaName());
            }
            for (LifeActivity lifeActivity : activities) {
                activityFilters.add(lifeActivity.getActivityName());
            }
            for (Location location : locations) {
                locationFilters.add(location.getLocationName());
            }
            for (Medicine medicine : medicines) {
                medicineFilters.add(medicine.getMedicineName());
            }
            for (Relief relief : reliefs) {
                reliefFilters.add(relief.getReliefName());
            }
            for (Symptom symptom : symptoms) {
                symptomFilters.add(symptom.getSymptomName());
            }
            for (Trigger trigger : triggers) {
                triggerFilters.add(trigger.getTriggerName());
            }

            filterList.add(bodyAreaFilters);
            filterList.add(activityFilters);
            filterList.add(locationFilters);
            filterList.add(medicineFilters);
            filterList.add(reliefFilters);
            filterList.add(symptomFilters);
            filterList.add(triggerFilters);

            return filterList;
        }


        @Override
        protected void onPostExecute(ArrayList<ArrayList<String>> filterList) {
            Log.d("GetRecordFilterListTask", " onPostExecute - update ui");

            FilterDialogFragment filterDialogFragment = new FilterDialogFragment();
            filterDialogFragment.setFilters(filterList);
            if (selectedFilters != null && selectedFilters.size() == 7) {//set selection
                filterDialogFragment.setPreviousSelection(selectedFilters);
            }
            filterDialogFragment.show(getFragmentManager(), "filterDialogFragment");
        }
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

            if (recordViewData == null || recordViewData.length == 0) {//Records in db
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

        private ProgressDialog nDialog;

        @Override
        protected RecordViewData[] doInBackground(String... params) {
            Log.d("GetRecordList", " doInBackground - query records");
            RecordViewData[] recordViewData = RecordController.getRecordViewData();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return recordViewData;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(getActivity());
            nDialog.setMessage("Loading records...");
            nDialog.setTitle("Processing");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(false);
            nDialog.show();
        }

        @Override
        protected void onPostExecute(RecordViewData recordViewData[]) {
            Log.d("GetRecordList", " onPostExecute - update ui");

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

            if (nDialog != null) {
                nDialog.dismiss();
            }
        }
    }
}
