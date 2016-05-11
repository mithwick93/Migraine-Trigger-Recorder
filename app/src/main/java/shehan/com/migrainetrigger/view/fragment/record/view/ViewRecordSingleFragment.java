package shehan.com.migrainetrigger.view.fragment.record.view;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.controller.RecordController;
import shehan.com.migrainetrigger.data.model.BodyArea;
import shehan.com.migrainetrigger.data.model.LifeActivity;
import shehan.com.migrainetrigger.data.model.Medicine;
import shehan.com.migrainetrigger.data.model.Record;
import shehan.com.migrainetrigger.data.model.Relief;
import shehan.com.migrainetrigger.data.model.Symptom;
import shehan.com.migrainetrigger.data.model.Trigger;
import shehan.com.migrainetrigger.utility.AppUtil;
import shehan.com.migrainetrigger.view.fragment.record.add.AddRecordFullFragment;

import static shehan.com.migrainetrigger.utility.AppUtil.getTimeStampDate;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewRecordSingleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewRecordSingleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewRecordSingleFragment extends AddRecordFullFragment {
    private static final String ARG_RECORD_ID = "recordId";

    private int recordId;

    private OnFragmentInteractionListener mCallback;

    public ViewRecordSingleFragment() {
        // Required empty public constructor
    }

    /**
     * Static method to get new instance of this fragment
     *
     * @param recordId record Id selected
     * @ ViewRecordSingleFragment fragment
     */
    public static ViewRecordSingleFragment newInstance(int recordId) {

        ViewRecordSingleFragment fragment = new ViewRecordSingleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_RECORD_ID, recordId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recordId = getArguments().getInt(ARG_RECORD_ID);
        } else {
            Log.e("ViewRecordSingleFrag", "Invalid record Id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_record_single, container, false);
        setHasOptionsMenu(true);

        initSingleRecordView(view);
        Log.d("ViewRecord-onCreate", "variables initialized, onCreate complete");
        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.view_record_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //override this in sub classes
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            deleteRecord();
            return true;
        } else if (id == R.id.action_refresh) {
            showWeather();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mCallback = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public String toString() {
        return "Single record view";
    }

    private void initSingleRecordView(View view) {
        Log.d("ViewRecordSingle", "initSingleRecordView ");
        super.initFullControls(view);

        new LoadRecordTask().execute();//Load record data to view
    }

    /**
     * update record
     */
    public void updateRecord() {
        Log.d("ViewRecordSingle", "updateRecord");

        //validations
        //check start<end
        Timestamp startTimestamp;

        //Check for start date
        if (startDate[0] != -1) {

            if (startTime[0] != -1) {
                String tmpStr = String.valueOf(startDate[0]) + "-" + String.valueOf(startDate[1]) + "-" + String.valueOf(startDate[2]) + " "
                        + String.valueOf(startTime[0]) + ":" + String.valueOf(startTime[1]) + ":00";

                startTimestamp = getTimeStampDate(tmpStr);
            } else {
                String tmpStr = String.valueOf(startDate[0]) + "-" + String.valueOf(startDate[1]) + "-" + String.valueOf(startDate[2]) + " 00:00:00";
                startTimestamp = getTimeStampDate(tmpStr);
            }
        } else {
            showMsg(getContext(), "Record must have start time");
            return;
        }

        Calendar c = Calendar.getInstance();
        if (startTimestamp.after(c.getTime())) {
            showMsg(getContext(), "Start Date is past current time");
            return;
        }

        //Check for end date
        Timestamp endTimestamp = null;
        if (endDate[0] != -1) {

            if (endTime[0] != -1) {
                String tmpStr = String.valueOf(endDate[0]) + "-" + String.valueOf(endDate[1]) + "-" + String.valueOf(endDate[2]) + " "
                        + String.valueOf(endTime[0]) + ":" + String.valueOf(endTime[1]) + ":00";
                endTimestamp = getTimeStampDate(tmpStr);
            } else {
                String tmpStr = String.valueOf(endDate[0]) + "-" + String.valueOf(endDate[1]) + "-" + String.valueOf(endDate[2]) + " 00:00:00";
                endTimestamp = getTimeStampDate(tmpStr);
            }

        }

        if (endTimestamp != null) {
            if (endTimestamp.after(c.getTime())) {
                showMsg(getContext(), "End Date is past current time");
                return;
            }
        }

        //validate times
        if ((endTimestamp != null && startTimestamp.before(endTimestamp)) || endTimestamp == null) {

            new AsyncTask<String, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(String... params) {
                    return RecordController.updateRecord(getFullRecordBuilder().setRecordId(recordId).createRecord());
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    if (result) {
                        showToast(getContext(), "Record was updated successfully");
                        if (mCallback != null) {
                            mCallback.onFragmentInteraction(0);
                        }

                    } else {
                        showToast(getContext(), "Record update failed");
                    }
                }
            }.execute();

        } else {
            showMsg(getContext(), "Start time is greater than the end time");
        }


    }

    /**
     * delete record
     */
    public void deleteRecord() {
        Log.d("ViewRecordSingle", "deleteRecord");


        new MaterialDialog.Builder(getContext())
                .title("Delete record")
                .content("Do you want to delete record permanently ?")
                .negativeText("Cancel")
                .positiveText("Delete weather")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        new AsyncTask<String, Void, Boolean>() {
                            @Override
                            protected Boolean doInBackground(String... params) {
                                return RecordController.deleteRecord(recordId);
                            }

                            @Override
                            protected void onPostExecute(Boolean result) {
                                if (result) {
                                    showToast(getContext(), "Record deleted successfully");
                                    if (mCallback != null) {
                                        mCallback.onFragmentInteraction(0);
                                    }

                                } else {
                                    showToast(getContext(), "Record delete failed");
                                }
                            }
                        }.execute();
                    }
                })
                .show();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int request);
    }

    //
    //
    //

    /**
     * Asynk task to load record from db and show in UI
     */
    private class LoadRecordTask extends AsyncTask<String, Void, Record> {


        private void setStartTime(Record record) {
            //Get start time
            if (record.getStartTime() != null) {
                Log.d("LoadRecordTask", "setStartTime - " + AppUtil.getStringDate(record.getStartTime()));
                long timestamp = record.getStartTime().getTime();
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(timestamp);
                mYear = startDate[0] = cal.get(Calendar.YEAR);
                mMonth = startDate[1] = cal.get(Calendar.MONTH) + 1;
                mDay = startDate[2] = cal.get(Calendar.DAY_OF_MONTH);

                editTxtStartDate.setText(String.format(Locale.getDefault(), "%02d-%02d-%d", mDay, mMonth, mYear));

                editTxtStartTime.setEnabled(true);

                mHour = startTime[0] = cal.get(Calendar.HOUR_OF_DAY);
                mMinute = startTime[1] = cal.get(Calendar.MINUTE);

                editTxtStartTime.setText(AppUtil.getFormattedTime(mHour, mMinute));
            }
        }

        private void setEndTime(Record record) {
            //Get end time
            if (record.getEndTime() != null) {
                Log.d("LoadRecordTask", "setEndTime - " + AppUtil.getStringDate(record.getEndTime()));
                long timestamp = record.getEndTime().getTime();
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(timestamp);

                mYear = endDate[0] = cal.get(Calendar.YEAR);
                mMonth = endDate[1] = cal.get(Calendar.MONTH) + 1;
                mDay = endDate[2] = cal.get(Calendar.DAY_OF_MONTH);

                editTxtEndDate.setText(String.format(Locale.getDefault(), "%02d-%02d-%d", mDay, mMonth, mYear));

                editTxtEndTime.setEnabled(true);

                mHour = endTime[0] = cal.get(Calendar.HOUR_OF_DAY);
                mMinute = endTime[1] = cal.get(Calendar.MINUTE);

                editTxtEndTime.setText(AppUtil.getFormattedTime(mHour, mMinute));
            }
        }

        private void setIntensity(Record record) {
            //Get intensity
            intensity = record.getIntensity();
            setIntensityIcon(intensity);
        }

        private void setWeatherData(Record record) {
            //Get weather data
            if (record.getWeatherData() != null) {
                weatherData = record.getWeatherData();//load weather to variable

                txtViewWeatherTemp.setText(String.format(Locale.getDefault(), "%.2f °C", weatherData.getTemperature()));
                txtViewWeatherHumidity.setText(String.format(Locale.getDefault(), "%.2f %%", weatherData.getHumidity()));
                txtViewWeatherPressure.setText(String.format(Locale.getDefault(), "%.2f KPa", weatherData.getPressure()));
                weatherDataLoaded = true;

                layoutWeather.setVisibility(View.VISIBLE);
            }
        }


        private void setTriggers(Record record) {
            //Get triggers
            if (record.getTriggers() != null) {
                selectedTriggers = record.getTriggers();

                Integer[] selectedIndexes = new Integer[selectedTriggers.size()];
                String selectedStr = "";

                for (int i = 0; i < selectedTriggers.size(); i++) {//Adding indexes
                    Trigger tmp = selectedTriggers.get(i);
                    int index = triggers.indexOf(tmp);
                    if (index > -1) {//if contain tmp
                        selectedIndexes[i] = index;

                        String name = tmp.toString();
                        selectedStr = selectedStr + ", " + name;
                    }
                }

                selectedTriggerIndexes = selectedIndexes;
                if (selectedStr.contains(",")) {
                    selectedStr = selectedStr.replaceFirst(",", "");
                }
                editTxtTriggers.setText(selectedStr);

            }
        }

        private void setSymptoms(Record record) {
            //Get symptoms
            if (record.getSymptoms() != null) {
                selectedSymptoms = record.getSymptoms();

                Integer[] selectedIndexes = new Integer[selectedSymptoms.size()];
                String selectedStr = "";

                for (int i = 0; i < selectedSymptoms.size(); i++) {//Adding indexes
                    Symptom tmp = selectedSymptoms.get(i);
                    int index = symptoms.indexOf(tmp);
                    if (index > -1) {//if contain tmp
                        selectedIndexes[i] = index;

                        String name = tmp.toString();
                        selectedStr = selectedStr + ", " + name;
                    }
                }

                selectedSymptomsIndexes = selectedIndexes;
                if (selectedStr.contains(",")) {
                    selectedStr = selectedStr.replaceFirst(",", "");
                }
                editTxtSymptoms.setText(selectedStr);
            }
        }

        private void setActivities(Record record) {
            //Get activities
            if (record.getActivities() != null) {
                selectedActivities = record.getActivities();
                Integer[] selectedIndexes = new Integer[selectedActivities.size()];
                String selectedStr = "";

                for (int i = 0; i < selectedActivities.size(); i++) {//Adding indexes
                    LifeActivity tmp = selectedActivities.get(i);
                    int index = activities.indexOf(tmp);
                    if (index > -1) {//if contain tmp
                        selectedIndexes[i] = index;

                        String name = tmp.toString();
                        selectedStr = selectedStr + ", " + name;
                    }
                }

                selectedActivityIndexes = selectedIndexes;
                if (selectedStr.contains(",")) {
                    selectedStr = selectedStr.replaceFirst(",", "");
                }
                editTxtActivities.setText(selectedStr);

            }
        }


        private void setLocation(Record record) {
            //Get location
            if (record.getLocation() != null) {
                location = record.getLocation();
                int index = locations.indexOf(location);
                if (index > -1) {
                    selectedLocation = index;
                }
                editTxtLocation.setText(location.toString());
            }
        }

        private void setBodyAreas(Record record) {
            //Get pain in
            if (record.getBodyAreas() != null) {
                selectedBodyAreas = record.getBodyAreas();

                Integer[] selectedIndexes = new Integer[selectedBodyAreas.size()];
                String selectedStr = "";

                for (int i = 0; i < selectedBodyAreas.size(); i++) {//Adding indexes
                    BodyArea tmp = selectedBodyAreas.get(i);
                    int index = bodyAreas.indexOf(tmp);
                    if (index > -1) {//if contain tmp
                        selectedIndexes[i] = index;

                        String name = tmp.toString();
                        selectedStr = selectedStr + ", " + name;
                    }
                }

                selectedBodyIndexes = selectedIndexes;
                if (selectedStr.contains(",")) {
                    selectedStr = selectedStr.replaceFirst(",", "");
                }
                editTxtBodyAreas.setText(selectedStr);
            }
        }

        private void setMedicine(Record record) {
            //Get medicine
            if (record.getMedicines() != null) {
                selectedMedicines = record.getMedicines();

                Integer[] selectedIndexes = new Integer[selectedMedicines.size()];
                Integer[] selectedIndexesEffective;
                int effectiveCounter = 0;

                int effectiveMedCount = 0;
                for (Medicine medicine : selectedMedicines) {
                    if (medicine.isEffective()) {
                        effectiveMedCount++;
                    }
                }

                selectedIndexesEffective = new Integer[effectiveMedCount];

                String selectedStr = "";
                String selectedStrEffective = "";

                for (int i = 0; i < selectedMedicines.size(); i++) {//Adding indexes
                    Medicine tmp = selectedMedicines.get(i);
                    int index = medicines.indexOf(tmp);
                    if (index > -1) {//if contain tmp
                        selectedIndexes[i] = index;

                        String name = tmp.toString();
                        selectedStr = selectedStr + ", " + name;
                    }
                    if (tmp.isEffective() && effectiveMedCount > 0) {
                        selectedIndexesEffective[effectiveCounter] = i;
                        effectiveCounter++;
                        String name = tmp.toString();
                        selectedStrEffective = selectedStrEffective + ", " + name;
                    }
                }

                selectedMedicineIndexes = selectedIndexes;
                if (selectedStr.contains(",")) {
                    selectedStr = selectedStr.replaceFirst(",", "");
                }
                editTxtMedicine.setText(selectedStr);

                //Get effective medicine
                viewLayoutRecordEffectiveMedicine.setVisibility(View.VISIBLE);

                if (selectedIndexesEffective.length > 0) {
                    selectedEffectiveMedicineIndexes = selectedIndexesEffective;

                    if (selectedStrEffective.contains(",")) {
                        selectedStrEffective = selectedStrEffective.replaceFirst(",", "");
                    }
                    editTxtMedicineEffective.setText(selectedStrEffective);
                }
            }
        }

        private void setReliefs(Record record) {
            //Get reliefs
            if (record.getReliefs() != null) {
                selectedReliefs = record.getReliefs();

                Integer[] selectedIndexes = new Integer[selectedReliefs.size()];
                Integer[] selectedIndexesEffective;
                int effectiveCounter = 0;

                int effectiveReliefCount = 0;
                for (Relief relief : selectedReliefs) {
                    if (relief.isEffective()) {
                        effectiveReliefCount++;
                    }
                }

                selectedIndexesEffective = new Integer[effectiveReliefCount];

                String selectedStr = "";
                String selectedStrEffective = "";

                for (int i = 0; i < selectedReliefs.size(); i++) {//Adding indexes
                    Relief tmp = selectedReliefs.get(i);
                    int index = reliefs.indexOf(tmp);
                    if (index > -1) {//if contain tmp
                        selectedIndexes[i] = index;

                        String name = tmp.toString();
                        selectedStr = selectedStr + ", " + name;
                    }
                    if (tmp.isEffective() && effectiveReliefCount > 0) {
                        selectedIndexesEffective[effectiveCounter] = i;
                        effectiveCounter++;
                        String name = tmp.toString();
                        selectedStrEffective = selectedStrEffective + ", " + name;
                    }
                }

                selectedReliefIndexes = selectedIndexes;
                if (selectedStr.contains(",")) {
                    selectedStr = selectedStr.replaceFirst(",", "");
                }
                editTxtRelief.setText(selectedStr);

                //Get effective reliefs
                viewLayoutRecordEffectiveRelief.setVisibility(View.VISIBLE);
                if (selectedIndexesEffective.length > 0) {
                    selectedEffectiveReliefIndexes = selectedIndexesEffective;

                    if (selectedStrEffective.contains(",")) {
                        selectedStrEffective = selectedStrEffective.replaceFirst(",", "");
                    }
                    editTxtReliefEffective.setText(selectedStrEffective);
                }
            }
        }

        @Override
        protected Record doInBackground(String... params) {
            Log.d("LoadRecordTask", "doInBackground ");
            return RecordController.getRecordAll(recordId);
        }

        @Override
        protected void onPostExecute(Record record) {
            Log.d("LoadRecordTask", "onPostExecute ");

            setStartTime(record);
            setEndTime(record);
            setIntensity(record);
            setWeatherData(record);

            setTriggers(record);
            setSymptoms(record);
            setActivities(record);

            setLocation(record);
            setBodyAreas(record);
            setMedicine(record);
            setReliefs(record);
        }
    }
}
