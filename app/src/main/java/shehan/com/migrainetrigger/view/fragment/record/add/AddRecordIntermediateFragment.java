package shehan.com.migrainetrigger.view.fragment.record.add;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.InputType;
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
import java.util.Locale;

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

    protected ArrayList<LifeActivity> activities;
    protected EditText editTxtActivities;
    protected EditText editTxtSymptoms;
    protected EditText editTxtTriggers;
    protected ArrayList<LifeActivity> selectedActivities;
    protected Integer[] selectedActivityIndexes;
    protected ArrayList<Symptom> selectedSymptoms;
    protected Integer[] selectedSymptomsIndexes;
    protected Integer[] selectedTriggerIndexes;
    protected ArrayList<Trigger> selectedTriggers;
    protected ArrayList<Symptom> symptoms;
    protected ArrayList<Trigger> triggers;
    private AddRecordIntermediateFragmentListener mCallback;

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

        if (context instanceof ViewRecordSingleFragment.SingleRecordViewFragmentListener) {
            Log.w("AddRecordInter-onAttach", "Context instanceof ViewRecordSingleFragment.SingleRecordViewFragmentListener");
            return;
        }
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (AddRecordIntermediateFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement AddRecordIntermediateFragmentListener");
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
        if (id == R.id.action_refresh) {
            showWeather();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                activities = LifeActivityController.getAllActivities(true);
                triggers = TriggerController.getAllTriggers(true);
                symptoms = SymptomController.getAllSymptoms(true);
                return "";
            }
        }.execute();

        selectedActivities = new ArrayList<>();
        selectedTriggers = new ArrayList<>();
        selectedSymptoms = new ArrayList<>();

        selectedActivityIndexes = null;
        selectedTriggerIndexes = null;
        selectedSymptomsIndexes = null;

        triggerSetup();
        symptomSetup();
        activitySetup();


    }

    private void activitySetup() {
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
                                handleAnswerAdd("Activities");
                            }
                        })
                        .positiveText(R.string.confirmButtonDialog)
                        .neutralText(R.string.addNewAnswer)
                        .show();
            }
        });
    }

    private void handleAnswerAdd(final String answerSection) {
        //On add new button click, show dialog to get name, place at bottom , add to db
        String content = answerSection.substring(0, answerSection.length() - 1).toLowerCase(Locale.getDefault());
        if (answerSection.equals("Activities")) {
            content = "activity";
        }
        new MaterialDialog.Builder(this.getContext())
                .title("Add new answer")
                .content("Enter new " + content)
                .inputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
                .inputRange(1, 64)
                .positiveText("Submit")
                .negativeText("Cancel")
                .input("New answer", "", false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        new AddNewAnswerIntermediateTask(answerSection).execute(input.toString());
                    }
                }).show();
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
            AppUtil.showMsg(getContext(), "Record must have start time", "Validation error");
            return;
        }

        Calendar c = Calendar.getInstance();
        if (startTimestamp != null && startTimestamp.after(c.getTime())) {
            AppUtil.showMsg(getContext(), "Start Date is past current time", "Validation error");
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
                AppUtil.showMsg(getContext(), "End Date is past current time", "Validation error");
                return;
            }
        }

        //validate times
        if ((startTimestamp != null && endTimestamp != null && startTimestamp.before(endTimestamp)) || endTimestamp == null) {

            new AsyncTask<String, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(String... params) {
                    return RecordController.addNewRecord(getIntermediateRecordBuilder().createRecord(), 1);//Level 1
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    if (result) {
                        AppUtil.showToast(getContext(), "Record was saved successfully");
                        if (mCallback != null) {
                            mCallback.onAddRecordIntermediateRequest(0);
                        }
                    } else {
                        AppUtil.showToast(getContext(), "Record save failed");
                    }
                }
            }.execute();
        } else {
            AppUtil.showMsg(getContext(), "Start time is greater than the end time", "Validation error");
        }
    }

    private void symptomSetup() {
        //--------------------------------------------------
        //Symptoms
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
//                                AppUtil.showToast(getContext(), "Selection cleared");
//
//                                if (symptoms.size() > 0) {
//                                    selectedSymptoms.clear();
//                                    dialog.clearSelectedIndices();
//                                }
//                                selectedSymptomsIndexes = null;
//                                editTxtSymptoms.setText("");
                                handleAnswerAdd("Symptoms");
                            }
                        })
                        .positiveText(R.string.confirmButtonDialog)
                        .neutralText(R.string.addNewAnswer)
                        .show();
            }
        });
    }

    private void triggerSetup() {
        //--------------------------------------------------
        //Triggers
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
                //if (triggers.size() > 0) {
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
                                handleAnswerAdd("Triggers");
                            }
                        })
                        .positiveText(R.string.confirmButtonDialog)
                        .neutralText(R.string.addNewAnswer)
                        .show();
            }
        });
    }

    //
    //
    //

    /**
     * Parent activity must implement this interface to communicate
     */
    public interface AddRecordIntermediateFragmentListener {
        /**
         * Parent activity must implement this method to communicate
         */
        void onAddRecordIntermediateRequest(int request);
    }


    /**
     * Async task to add new Answer
     */
    private class AddNewAnswerIntermediateTask extends AsyncTask<String, Void, Long> {

        private String answerSection;

        private AddNewAnswerIntermediateTask(String answerSection) {
            this.answerSection = answerSection;
        }

        @Override
        protected Long doInBackground(String... answer) {
            Log.d("AddNewAnswerTask", " doInBackground - save answers");

            String name = answer[0].trim();
            long response = -1;
            int id;
            switch (answerSection) {
                case "Triggers": {
                    int priority = -1;
                    id = TriggerController.getLastRecordId() + 1;
                    ArrayList<Trigger> tmpLst = TriggerController.getAllTriggers(false);
                    if (tmpLst.size() > 0) {
                        Trigger trigger = tmpLst.get(tmpLst.size() - 1);
                        if (trigger != null) {
                            priority = trigger.getPriority();
                        }
                    }
                    priority++;
                    response = TriggerController.addTrigger(new Trigger(id, name, priority));
                    break;
                }
                case "Symptoms": {
                    int priority = -1;
                    id = SymptomController.getLastRecordId() + 1;
                    ArrayList<Symptom> tmpLst = SymptomController.getAllSymptoms(false);
                    if (tmpLst.size() > 0) {
                        Symptom symptom = tmpLst.get(tmpLst.size() - 1);
                        if (symptom != null) {
                            priority = symptom.getPriority();
                        }
                    }
                    priority++;
                    response = SymptomController.addSymptom(new Symptom(id, name, priority));
                    break;
                }
                case "Activities": {
                    int priority = -1;
                    id = LifeActivityController.getLastRecordId() + 1;
                    ArrayList<LifeActivity> tmpLst = LifeActivityController.getAllActivities(false);
                    if (tmpLst.size() > 0) {
                        LifeActivity lifeActivity = tmpLst.get(tmpLst.size() - 1);
                        if (lifeActivity != null) {
                            priority = lifeActivity.getPriority();
                        }
                    }
                    priority++;
                    response = LifeActivityController.addActivity(new LifeActivity(id, name, priority));
                    break;
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(Long response) {
            Log.d("AddNewAnswerTask", " onPostExecute - show result");

            if (response > 0) {
                AppUtil.showToast(getContext(), "Answer added successfully");
                switch (answerSection) {
                    case "Triggers": {
                        new AsyncTask<String, Void, String>() {
                            @Override
                            protected String doInBackground(String... params) {
                                triggers = TriggerController.getAllTriggers(true);
                                return "";
                            }
                        }.execute();
                        break;
                    }
                    case "Symptoms": {
                        new AsyncTask<String, Void, String>() {
                            @Override
                            protected String doInBackground(String... params) {
                                symptoms = SymptomController.getAllSymptoms(true);
                                return "";
                            }
                        }.execute();
                        break;
                    }
                    case "Activities": {
                        new AsyncTask<String, Void, String>() {
                            @Override
                            protected String doInBackground(String... params) {
                                activities = LifeActivityController.getAllActivities(true);
                                return "";
                            }
                        }.execute();
                        break;
                    }
                }
            } else {
                AppUtil.showToast(getContext(), "Answer add failed");
            }
        }
    }
}
