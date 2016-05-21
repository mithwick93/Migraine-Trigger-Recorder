package shehan.com.migrainetrigger.view.fragment.record.add;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.controller.LifeActivityController;
import shehan.com.migrainetrigger.controller.RecordController;
import shehan.com.migrainetrigger.controller.SymptomController;
import shehan.com.migrainetrigger.controller.TriggerController;
import shehan.com.migrainetrigger.data.builders.RecordBuilder;
import shehan.com.migrainetrigger.data.model.LifeActivity;
import shehan.com.migrainetrigger.data.model.Symptom;
import shehan.com.migrainetrigger.data.model.Trigger;
import shehan.com.migrainetrigger.utility.AppUtil;
import shehan.com.migrainetrigger.view.fragment.record.view.ViewRecordSingleFragment;

import static shehan.com.migrainetrigger.utility.AppUtil.getTimeStampDate;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddRecordIntermediateFragment extends AddRecordBasicFragment {

    //Data storage
    protected ArrayList<LifeActivity> activities;
    protected EditText editTxtActivities;
    protected EditText editTxtSymptoms;
    //intermediate
    //Controls
    protected EditText editTxtTriggers;
    //
    protected ArrayList<LifeActivity> selectedActivities;
    //Track selected incises
    protected Integer[] selectedActivityIndexes;
    protected ArrayList<Symptom> selectedSymptoms;
    protected Integer[] selectedSymptomsIndexes;
    protected Integer[] selectedTriggerIndexes;
    protected ArrayList<Trigger> selectedTriggers;
    protected ArrayList<Symptom> symptoms;
    protected ArrayList<Trigger> triggers;
    //Callback
    private AddRecordIntermediateListener mCallback;

    public AddRecordIntermediateFragment() {
        // Required empty public constructor
    }

    //
    //
    //
    public void recordAcceptAction() {

        if (!weatherDataLoaded || weatherData == null) {
            new MaterialDialog.Builder(getContext())
                    .title("Compete record")
                    .content("Do you want to view weather information or save record now?")
                    .negativeText("Save record")
                    .positiveText("Show weather")
                    .neutralText("Cancel")
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            //Save without  summery
                            saveIntermediateRecord();
                        }
                    })
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            //show summery
                            showWeather();
                        }
                    })
                    .show();
        } else {
            //weatherData shown already ,just save record
            saveIntermediateRecord();
        }

    }

    @Override
    public String toString() {
        return "Intermediate";
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof ViewRecordSingleFragment.OnFragmentInteractionListener) {
            Log.w("AddRecordInter-onAttach", "Context instanceof ViewRecordSingleFragment.OnFragmentInteractionListener");
            return;
        }
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (AddRecordIntermediateListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement AddRecordIntermediateListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_record_intermediate, container, false);

        initIntermediateControls(view);

        setHasOptionsMenu(true);

        Log.d("AddRecordInter-onCreate", "variables initialized, onCreate complete");
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    //
    //
    //

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if (id == R.id.action_confirm) {
//            recordAcceptAction();
//            return true;
//        } else
        if (id == R.id.action_refresh) {
            showWeather();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * initiate intermediate controls
     * call this in sub classes onCreate
     *
     * @param view current view
     */
    protected void initIntermediateControls(View view) {
        Log.d("AddRecordInter", "initIntermediateControls ");

        if (editTxtTriggers != null) {
            return;
        }

        super.initBasicControls(view);
        editTxtTriggers = (EditText) view.findViewById(R.id.txt_record_triggers);
        editTxtSymptoms = (EditText) view.findViewById(R.id.txt_record_symptoms);
        editTxtActivities = (EditText) view.findViewById(R.id.txt_record_activities);

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                activities = LifeActivityController.getAllActivities();
                triggers = TriggerController.getAllTriggers();
                symptoms = SymptomController.getAllSymptoms();
                return "";
            }
        }.execute();

        selectedActivities = new ArrayList<>();
        selectedTriggers = new ArrayList<>();
        selectedSymptoms = new ArrayList<>();

        selectedActivityIndexes = null;
        selectedTriggerIndexes = null;
        selectedSymptomsIndexes = null;

//--------------------------------------------------
        //start time
        editTxtTriggers.setCursorVisible(false);
        editTxtTriggers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Show dialog with multi choice to choose items.
                when selected add to selected list
                when unchecked remove from list
                Show the names in edit text
                 */

                new MaterialDialog.Builder(getContext())
                        .title("Select triggers")
                        .items(triggers)
                        .itemsCallbackMultiChoice(selectedTriggerIndexes, new MaterialDialog.ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                String selectedStr = "";
                                selectedTriggers.clear();
                                selectedTriggerIndexes = which;

                                for (Integer integer : which) {
                                    String name = triggers.get(integer).toString();
                                    selectedStr = selectedStr + ", " + name;
                                    selectedTriggers.add(triggers.get(integer));
                                }
                                if (selectedStr.contains(",")) {
                                    selectedStr = selectedStr.replaceFirst(",", "");
                                }

                                editTxtTriggers.setText(selectedStr);
                                return true; // allow selection
                            }
                        })
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                showToast(getContext(), "Selection cleared");
                                dialog.clearSelectedIndices();
                                selectedTriggers.clear();
                                selectedTriggerIndexes = null;
                                editTxtTriggers.setText("");
                            }
                        })
                        .positiveText(R.string.confirmButtonDialog)
                        .neutralText(R.string.clear_selection)
                        .show();
            }
        });

//--------------------------------------------------
        //start time
        editTxtSymptoms.setCursorVisible(false);
        editTxtSymptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Show dialog with multi choice to choose items.
                when selected add to selected list
                when unchecked remove from list
                Show the names in edit text
                 */
                new MaterialDialog.Builder(getContext())
                        .title("Select Symptoms")
                        .items(symptoms)
                        .itemsCallbackMultiChoice(selectedSymptomsIndexes, new MaterialDialog.ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                String selectedStr = "";
                                selectedSymptoms.clear();
                                selectedSymptomsIndexes = which;

                                for (Integer integer : which) {
                                    String name = symptoms.get(integer).toString();
                                    selectedStr = selectedStr + ", " + name;
                                    selectedSymptoms.add(symptoms.get(integer));
                                }
                                if (selectedStr.contains(",")) {
                                    selectedStr = selectedStr.replaceFirst(",", "");
                                }

                                editTxtSymptoms.setText(selectedStr);
                                return true; // allow selection
                            }
                        })
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                showToast(getContext(), "Selection cleared");
                                dialog.clearSelectedIndices();
                                selectedSymptoms.clear();
                                selectedSymptomsIndexes = null;
                                editTxtSymptoms.setText("");
                            }
                        })
                        .positiveText(R.string.confirmButtonDialog)
                        .neutralText(R.string.clear_selection)
                        .show();
            }
        });

//--------------------------------------------------
        //activities
        editTxtActivities.setCursorVisible(false);
        editTxtActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Show dialog with multi choice to choose items.
                when selected add to selected list
                when unchecked remove from list
                Show the names in edit text
                 */
                new MaterialDialog.Builder(getContext())
                        .title("Select Activities")
                        .items(activities)
                        .itemsCallbackMultiChoice(selectedActivityIndexes, new MaterialDialog.ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                String selectedStr = "";
                                selectedActivities.clear();
                                selectedActivityIndexes = which;

                                for (Integer integer : which) {
                                    String name = activities.get(integer).toString();
                                    selectedStr = selectedStr + ", " + name;
                                    selectedActivities.add(activities.get(integer));
                                }
                                if (selectedStr.contains(",")) {
                                    selectedStr = selectedStr.replaceFirst(",", "");
                                }

                                editTxtActivities.setText(selectedStr);
                                return true; // allow selection
                            }
                        })
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                showToast(getContext(), "Selection cleared");
                                dialog.clearSelectedIndices();
                                selectedActivities.clear();
                                selectedActivityIndexes = null;
                                editTxtActivities.setText("");
                            }
                        })
                        .positiveText(R.string.confirmButtonDialog)
                        .neutralText(R.string.clear_selection)
                        .show();
            }
        });
    }

    /**
     * Save record,
     * In subclasses handle this separately
     */
    private void saveIntermediateRecord() {
        Log.d("AddRecordInterFragment", "saveIntermediateRecord");
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
            AppUtil.showMsg(getContext(), "Record must have start time");
            return;
        }

        Calendar c = Calendar.getInstance();
        if (startTimestamp.after(c.getTime())) {
            AppUtil.showMsg(getContext(), "Start Date is past current time");
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
                AppUtil.showMsg(getContext(), "End Date is past current time");
                return;
            }
        }

        //validate times
        if ((endTimestamp != null && startTimestamp.before(endTimestamp)) || endTimestamp == null) {

            new AsyncTask<String, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(String... params) {
                    return RecordController.addNewRecord(getIntermediateRecordBuilder().createRecord(), 1);//Level 1
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    if (result) {
                        showToast(getContext(), "Record was saved successfully");
                        if (mCallback != null) {
                            mCallback.onIntermediateRecordInteraction(0);
                        }
                    } else {
                        showToast(getContext(), "Record save failed");
                    }
                }
            }.execute();
        } else {
            AppUtil.showMsg(getContext(), "Start time is greater than the end time");
        }
    }

    /**
     * Get basic data of record
     * Does not check constraints
     *
     * @return record builder with intermediate data saved
     */
    protected RecordBuilder getIntermediateRecordBuilder() {
        Log.d("AddRecordInterFragment", "getIntermediateRecordBuilder");

        //Call parent method to get basic info
        RecordBuilder recordBuilder = getBasicRecordBuilder();

        if (selectedActivities.size() > 0) {
            Log.d("AddRecordInterFragment", "getIntermediateRecordBuilder - selectedActivities");
            recordBuilder = recordBuilder.setActivities(selectedActivities);
        }

        if (selectedTriggers.size() > 0) {
            Log.d("AddRecordInterFragment", "getIntermediateRecordBuilder - selectedTriggers");
            recordBuilder = recordBuilder.setTriggers(selectedTriggers);
        }

        if (selectedSymptoms.size() > 0) {
            Log.d("AddRecordInterFragment", " - selectedSymptoms");
            recordBuilder = recordBuilder.setSymptoms(selectedSymptoms);
        }

        return recordBuilder;
    }

    //
    //
    //

    /**
     * Parent activity must implement this interface to communicate
     */
    public interface AddRecordIntermediateListener {
        /**
         * Parent activity must implement this method to communicate
         */
        void onIntermediateRecordInteraction(int request);
    }

}
