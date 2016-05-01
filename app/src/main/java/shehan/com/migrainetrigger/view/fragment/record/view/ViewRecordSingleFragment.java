package shehan.com.migrainetrigger.view.fragment.record.view;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.Locale;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.controller.RecordController;
import shehan.com.migrainetrigger.data.model.Record;
import shehan.com.migrainetrigger.utility.AppUtil;
import shehan.com.migrainetrigger.view.fragment.record.add.AddRecordFullFragment;


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

    private OnFragmentInteractionListener mListener;

    public ViewRecordSingleFragment() {
        // Required empty public constructor
    }

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
    public boolean onOptionsItemSelected(MenuItem item) {
        //override this in sub classes
        int id = item.getItemId();
        if (id == R.id.action_confirm) {
            //TODO: Update record call
            return true;
        } else if (id == R.id.action_refresh) {
            //TODO: Update weather
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public String toString() {
        return "Single record view";
    }

    private void initSingleRecordView(View view) {
        Log.d("ViewRecordSingle", "initSingleRecordView ");
        super.initFullControls(view);

        new LoadRecordTask().execute();
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
    private class LoadRecordTask extends AsyncTask<String, Void, Record> {

        @Override
        protected Record doInBackground(String... params) {
            Log.d("LoadRecordTask", "doInBackground ");
            return RecordController.getRecordAll(recordId);
        }

        @Override
        protected void onPostExecute(Record record) {
            Log.d("LoadRecordTask", "onPostExecute ");


            //Get to start time
            if (record.getStartTime() != null) {

                long timestamp = record.getStartTime().getTime();
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(timestamp);
                mYear = startDate[0] = cal.get(Calendar.YEAR);
                mMonth = startDate[1] = cal.get(Calendar.MONTH) + 1;
                mDay = startDate[2] = cal.get(Calendar.DAY_OF_MONTH);

                editTxtStartDate.setText(String.format(Locale.getDefault(), "%02d-%02d-%d", mDay, mMonth, mYear));

                editTxtStartTime.setEnabled(true);

                mHour = startTime[0] = cal.get(Calendar.HOUR);
                mMinute = startTime[1] = cal.get(Calendar.MINUTE);

                editTxtStartTime.setText(AppUtil.getFormattedTime(mHour, mMinute));
            }

            //Get to end time
            if (record.getEndTime() != null) {

                long timestamp = record.getEndTime().getTime();
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(timestamp);

                mYear = endDate[0] = cal.get(Calendar.YEAR);
                mMonth = endDate[1] = cal.get(Calendar.MONTH) + 1;
                mDay = endDate[2] = cal.get(Calendar.DAY_OF_MONTH);

                editTxtEndDate.setText(String.format(Locale.getDefault(), "%02d-%02d-%d", mDay, mMonth, mYear));

                editTxtEndTime.setEnabled(true);

                mHour = endTime[0] = cal.get(Calendar.HOUR);
                mMinute = endTime[1] = cal.get(Calendar.MINUTE);

                editTxtEndTime.setText(AppUtil.getFormattedTime(mHour, mMinute));
            }

            //Get intensity
            setIntensityIcon(record.getIntensity());

            //Get weather data
            if (record.getWeatherData() != null) {
                weatherData = record.getWeatherData();

                txtViewWeatherTemp.setText(String.format(Locale.getDefault(), "%.2f °C", weatherData.getTemperature()));
                txtViewWeatherHumidity.setText(String.format(Locale.getDefault(), "%.2f %%", weatherData.getHumidity()));
                txtViewWeatherPressure.setText(String.format(Locale.getDefault(), "%.2f KPa", weatherData.getPressure()));
                weatherDataLoaded = true;

                layoutWeather.setVisibility(View.VISIBLE);
            }

            //Get triggers
            if (record.getTriggers() != null) {
                selectedTriggers = record.getTriggers();
            }

            //Get symptoms
            if (record.getSymptoms() != null) {
                selectedSymptoms = record.getSymptoms();
            }

            //Get activities
            if (record.getActivities() != null) {
                selectedActivities = record.getActivities();
            }

            //Get location
            if (record.getLocation() != null) {
                location = record.getLocation();
            }

            //Get pain in
            if (record.getBodyAreas() != null) {
                selectedBodyAreas = record.getBodyAreas();
            }

            //Get medicine
            if (record.getMedicines() != null) {
                selectedMedicines = record.getMedicines();

                //Get effective medicine
            }


            //Get reliefs
            if (record.getReliefs() != null) {
                selectedReliefs = record.getReliefs();
                //Get effective reliefs
            }


        }
    }
}
