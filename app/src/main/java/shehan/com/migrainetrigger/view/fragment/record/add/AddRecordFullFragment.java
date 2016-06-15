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
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.controller.BodyAreaController;
import shehan.com.migrainetrigger.controller.LocationController;
import shehan.com.migrainetrigger.controller.MedicineController;
import shehan.com.migrainetrigger.controller.RecordController;
import shehan.com.migrainetrigger.controller.ReliefController;
import shehan.com.migrainetrigger.data.builders.MedicineBuilder;
import shehan.com.migrainetrigger.data.builders.RecordBuilder;
import shehan.com.migrainetrigger.data.builders.ReliefBuilder;
import shehan.com.migrainetrigger.data.model.BodyArea;
import shehan.com.migrainetrigger.data.model.Location;
import shehan.com.migrainetrigger.data.model.Medicine;
import shehan.com.migrainetrigger.data.model.Relief;
import shehan.com.migrainetrigger.utility.AppUtil;
import shehan.com.migrainetrigger.view.fragment.record.view.ViewRecordSingleFragment;

import static shehan.com.migrainetrigger.utility.AppUtil.getTimeStampDate;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddRecordFullFragment extends AddRecordIntermediateFragment {
    protected ArrayList<BodyArea> bodyAreas;
    protected EditText editTxtBodyAreas;
    protected EditText editTxtLocation;
    protected EditText editTxtMedicine;
    protected EditText editTxtMedicineEffective;
    protected EditText editTxtRelief;
    protected EditText editTxtReliefEffective;
    protected Location location;
    protected ArrayList<Location> locations;
    protected ArrayList<Medicine> medicines;
    protected ArrayList<Relief> reliefs;
    protected ArrayList<BodyArea> selectedBodyAreas;
    protected Integer[] selectedBodyIndexes;
    protected Integer[] selectedEffectiveMedicineIndexes;
    protected Integer[] selectedEffectiveReliefIndexes;
    protected int selectedLocation;
    protected Integer[] selectedMedicineIndexes;
    protected ArrayList<Medicine> selectedMedicines;
    protected Integer[] selectedReliefIndexes;
    protected ArrayList<Relief> selectedReliefs;
    protected RelativeLayout viewLayoutRecordEffectiveMedicine;
    protected RelativeLayout viewLayoutRecordEffectiveRelief;
    private AddRecordFullFragmentListener mCallback;

    public AddRecordFullFragment() {
        // Required empty public constructor
    }

    /**
     * Choose to save record or get weather
     */
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
                            saveFullRecord();
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
            saveFullRecord();
        }

    }

    @Override
    public String toString() {
        return "Full";
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof ViewRecordSingleFragment.SingleRecordViewFragmentListener) {
            Log.w("AddRecordFull-onAttach", "Context instanceof ViewRecordSingleFragment.SingleRecordViewFragmentListener");
            return;
        }
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (AddRecordFullFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement AddRecordFullFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_record_full, container, false);

        initFullControls(view);
        setHasOptionsMenu(true);

        Log.d("AddRecordFull-onCreate", "variables initialized, onCreate complete");
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
        //override this in sub classes
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
     * @return record builder with full data saved
     */
    protected RecordBuilder getFullRecordBuilder() {
        Log.d("AddFullFragment", "getFullRecordBuilder");

        //Call parent method to get intermediate info
        RecordBuilder recordBuilder = getIntermediateRecordBuilder();

        if (location != null) {
            Log.d("AddFullFragment", "getFullRecordBuilder - location");
            recordBuilder = recordBuilder.setLocation(location);
            recordBuilder = recordBuilder.setLocationId(location.getLocationId());
        } else {
            recordBuilder = recordBuilder.setLocationId(0);
        }

        if (selectedBodyAreas.size() > 0) {
            Log.d("AddFullFragment", "getFullRecordBuilder - selectedBodyAreas");
            recordBuilder = recordBuilder.setBodyAreas(selectedBodyAreas);
        }

        if (selectedMedicines.size() > 0) {
            Log.d("AddFullFragment", " - selectedMedicines");
            recordBuilder = recordBuilder.setMedicines(selectedMedicines);
        }

        if (selectedReliefs.size() > 0) {
            Log.d("AddFullFragment", " - selectedReliefs");
            recordBuilder = recordBuilder.setReliefs(selectedReliefs);
        }

        return recordBuilder;
    }

    //
    //
    protected void initFullControls(final View view) {
        Log.d("AddRecordFullFragment", "initFullControls ");

        if (editTxtLocation != null) {
            return;
        }

        super.initIntermediateControls(view);

        editTxtLocation = (EditText) view.findViewById(R.id.txt_record_location);
        editTxtBodyAreas = (EditText) view.findViewById(R.id.txt_record_affected_areas);
        editTxtMedicine = (EditText) view.findViewById(R.id.txt_record_medicine);
        editTxtMedicineEffective = (EditText) view.findViewById(R.id.txt_record_effective_medicine);
        editTxtRelief = (EditText) view.findViewById(R.id.txt_record_relief);
        editTxtReliefEffective = (EditText) view.findViewById(R.id.txt_record_effective_reliefs);

        viewLayoutRecordEffectiveMedicine = (RelativeLayout) view.findViewById(R.id.record_full_layout_effective_medicine);
        viewLayoutRecordEffectiveRelief = (RelativeLayout) view.findViewById(R.id.record_full_layout_effective_relief);

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                locations = LocationController.getAllLocations(true);
                bodyAreas = BodyAreaController.getAllBodyAreas(true);
                medicines = MedicineController.getAllMedicines(true);
                reliefs = ReliefController.getAllReliefs(true);
                return "";
            }
        }.execute();

        location = null;
        selectedBodyAreas = new ArrayList<>();
        selectedMedicines = new ArrayList<>();
        selectedReliefs = new ArrayList<>();

        selectedLocation = -1;
        selectedBodyIndexes = null;
        selectedMedicineIndexes = null;
        selectedReliefIndexes = null;
        selectedEffectiveMedicineIndexes = null;
        selectedEffectiveReliefIndexes = null;

        viewLayoutRecordEffectiveMedicine.setVisibility(View.GONE);
        viewLayoutRecordEffectiveRelief.setVisibility(View.GONE);

        locationSetup();
        painAreaSetup();
        medicineSetup();
        reliefSetup();
        effectiveMedicineSetup();
        effectiveReliefSetup();


    }

    private void effectiveMedicineSetup() {
        //--------------------------------------------------
        //Effective medicines
        editTxtMedicineEffective.setCursorVisible(false);
        editTxtMedicineEffective.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Show dialog with multi choice to choose items.
                when selected add to selected list
                when unchecked remove from list
                Show the names in edit text
                 */
                if (selectedMedicines.size() > 0 && selectedMedicineIndexes.length > 0) {
                    new MaterialDialog.Builder(getContext())
                            .title("Select effective medicine")
                            .items(selectedMedicines)
                            .itemsCallbackMultiChoice(selectedEffectiveMedicineIndexes, new MaterialDialog.ListCallbackMultiChoice() {
                                @Override
                                public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                    String selectedStr = "";
                                    selectedEffectiveMedicineIndexes = which;

                                    selectedMedicines.clear();
                                    for (Integer integer : selectedMedicineIndexes) {
                                        Medicine m = medicines.get(integer);
                                        selectedMedicines.add(new MedicineBuilder().setMedicineId(m.getMedicineId()).setMedicineName(m.getMedicineName()).setPriority(m.getPriority()).createMedicine());
                                    }

                                    for (Integer integer : selectedEffectiveMedicineIndexes) {
                                        String name = selectedMedicines.get(integer).toString();
                                        selectedStr = selectedStr + ", " + name;
                                        selectedMedicines.get(integer).setEffective(true);
                                    }

                                    if (selectedStr.contains(",")) {
                                        selectedStr = selectedStr.replaceFirst(",", "");
                                    }

                                    editTxtMedicineEffective.setText(selectedStr);


                                    return true; // allow selection
                                }
                            })
                            .onNeutral(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    AppUtil.showToast(getContext(), "Selection cleared");
                                    if (selectedMedicines.size() > 0) {
                                        dialog.clearSelectedIndices();
                                        selectedMedicines.clear();
                                        for (Integer integer : selectedMedicineIndexes) {
                                            Medicine m = medicines.get(integer);
                                            selectedMedicines.add(new MedicineBuilder().setMedicineId(m.getMedicineId()).setMedicineName(m.getMedicineName()).setPriority(m.getPriority()).createMedicine());
                                        }
                                    }
                                    selectedEffectiveMedicineIndexes = null;
                                    editTxtMedicineEffective.setText("");

                                }
                            })
                            .positiveText(R.string.confirmButtonDialog)
                            .neutralText(R.string.clear_selection)
                            .show();
                }

            }
        });
    }

    private void effectiveReliefSetup() {
        //--------------------------------------------------
        //Effective Reliefs
        editTxtReliefEffective.setCursorVisible(false);
        editTxtReliefEffective.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Show dialog with multi choice to choose items.
                when selected add to selected list
                when unchecked remove from list
                Show the names in edit text
                 */
                if (selectedReliefs.size() > 0 && selectedReliefIndexes.length > 0) {
                    new MaterialDialog.Builder(getContext())
                            .title("Select effective reliefs")
                            .items(selectedReliefs)
                            .itemsCallbackMultiChoice(selectedEffectiveReliefIndexes, new MaterialDialog.ListCallbackMultiChoice() {
                                @Override
                                public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                    String selectedStr = "";
                                    selectedEffectiveReliefIndexes = which;

                                    selectedReliefs.clear();
                                    for (Integer integer : selectedReliefIndexes) {
                                        Relief r = reliefs.get(integer);
                                        selectedReliefs.add(new ReliefBuilder().setReliefId(r.getReliefId()).setReliefName(r.getReliefName()).setPriority(r.getPriority()).createRelief());
                                    }

                                    for (Integer integer : which) {
                                        String name = selectedReliefs.get(integer).toString();
                                        selectedStr = selectedStr + ", " + name;
                                        selectedReliefs.get(integer).setEffective(true);
                                    }

                                    if (selectedStr.contains(",")) {
                                        selectedStr = selectedStr.replaceFirst(",", "");
                                    }

                                    editTxtReliefEffective.setText(selectedStr);
                                    return true; // allow selection
                                }
                            })
                            .onNeutral(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    AppUtil.showToast(getContext(), "Selection cleared");

                                    if (selectedReliefs.size() > 0) {
                                        dialog.clearSelectedIndices();

                                        selectedReliefs.clear();
                                        for (Integer integer : selectedReliefIndexes) {
                                            Relief r = reliefs.get(integer);
                                            selectedReliefs.add(new ReliefBuilder().setReliefId(r.getReliefId()).setReliefName(r.getReliefName()).setPriority(r.getPriority()).createRelief());
                                        }
                                    }
                                    selectedEffectiveReliefIndexes = null;
                                    editTxtReliefEffective.setText("");

                                }
                            })
                            .positiveText(R.string.confirmButtonDialog)
                            .neutralText(R.string.clear_selection)
                            .show();
                }
            }
        });
    }

    private void handleAnswerAdd(final String answerSection) {
        //On add new button click, show dialog to get name, place at bottom , add to db
        String content = answerSection.substring(0, answerSection.length() - 1).toLowerCase(Locale.getDefault());
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
                        new AddNewAnswerFullTask(answerSection).execute(input.toString());
                    }
                }).show();
    }

    private void locationSetup() {
        //--------------------------------------------------
        //Location
        editTxtLocation.setCursorVisible(false);
        editTxtLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Show dialog with single choice.
                when selected set to location
                when unchecked remove to location
                Show on edit txt
                 */
                new MaterialDialog.Builder(getContext())
                        .title("Select Location")
                        .items(locations)
                        .itemsCallbackSingleChoice(selectedLocation, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                                location = locations.get(which);
                                selectedLocation = which;
                                editTxtLocation.setText(location.toString());
                                return true; // allow selection
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                AppUtil.showToast(getContext(), "Selection cleared");
                                location = null;
                                selectedLocation = -1;
                                editTxtLocation.setText("");
                                dialog.dismiss();
                            }
                        })
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                AppUtil.showToast(getContext(), "Selection cleared");
//                                location = null;
//                                selectedLocation = -1;
//                                editTxtLocation.setText("");
//                                dialog.dismiss();
                                handleAnswerAdd("Locations");
                            }
                        })
                        .alwaysCallSingleChoiceCallback()
                        .negativeText(R.string.clear_selection)
                        .neutralText(R.string.addNewAnswer)
                        .show();
            }
        });
    }

    private void medicineSetup() {
        //--------------------------------------------------
        //Medicine
        editTxtMedicine.setCursorVisible(false);
        editTxtMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Show dialog with multi choice to choose items.
                when selected add to selected list
                when unchecked remove from list
                Show the names in edit text
                Enable effective list
                 */
                new MaterialDialog.Builder(getContext())
                        .title("Select medicine taken")
                        .items(medicines)
                        .itemsCallbackMultiChoice(selectedMedicineIndexes, new MaterialDialog.ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                String selectedStr = "";
                                selectedMedicines.clear();
                                selectedMedicineIndexes = which;

                                viewLayoutRecordEffectiveMedicine.setVisibility(View.GONE);
                                editTxtMedicineEffective.setText("");
                                selectedEffectiveMedicineIndexes = null;

                                for (Integer integer : which) {
                                    String name = medicines.get(integer).toString();
                                    selectedStr = selectedStr + ", " + name;
                                    selectedMedicines.add(medicines.get(integer));
                                }
                                if (selectedStr.contains(",")) {
                                    selectedStr = selectedStr.replaceFirst(",", "");
                                }

                                editTxtMedicine.setText(selectedStr);
                                if (selectedMedicines.size() > 0) {
                                    viewLayoutRecordEffectiveMedicine.setVisibility(View.VISIBLE);
                                }

                                return true; // allow selection
                            }
                        })
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                AppUtil.showToast(getContext(), "Selection cleared");
//
//                                if (medicines.size() > 0) {
//                                    selectedMedicines.clear();
//                                    dialog.clearSelectedIndices();
//                                }
//                                selectedMedicineIndexes = null;
//                                selectedEffectiveMedicineIndexes = null;
//                                viewLayoutRecordEffectiveMedicine.setVisibility(View.GONE);
//                                editTxtMedicine.setText("");
//                                editTxtMedicineEffective.setText("");
                                handleAnswerAdd("Medicines");
                            }
                        })
                        .positiveText(R.string.confirmButtonDialog)
                        .neutralText(R.string.addNewAnswer)
                        .show();
            }
        });
    }

    private void painAreaSetup() {
        //--------------------------------------------------
        //Body area
        editTxtBodyAreas.setCursorVisible(false);
        editTxtBodyAreas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Show dialog with multi choice to choose items.
                when selected add to selected list
                when unchecked remove from list
                Show the names in edit text
                Enable effective list
                 */
                new MaterialDialog.Builder(getContext())
                        .title("Select pain areas")
                        .items(bodyAreas)
                        .itemsCallbackMultiChoice(selectedBodyIndexes, new MaterialDialog.ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                String selectedStr = "";
                                selectedBodyAreas.clear();
                                selectedBodyIndexes = which;

                                for (Integer integer : which) {
                                    String name = bodyAreas.get(integer).toString();
                                    selectedStr = selectedStr + ", " + name;
                                    selectedBodyAreas.add(bodyAreas.get(integer));
                                }
                                if (selectedStr.contains(",")) {
                                    selectedStr = selectedStr.replaceFirst(",", "");
                                }

                                editTxtBodyAreas.setText(selectedStr);
                                return true; // allow selection
                            }
                        })
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                    AppUtil.showToast(getContext(), "Selection cleared");
//
//                                    if (bodyAreas.size() > 0) {
//                                        selectedBodyAreas.clear();
//                                        dialog.clearSelectedIndices();
//                                    }
//                                    selectedBodyIndexes = null;
//                                    editTxtBodyAreas.setText("");
                                handleAnswerAdd("Pain areas");
                            }
                        })
                        .positiveText(R.string.confirmButtonDialog)
                        .neutralText(R.string.addNewAnswer)
                        .show();
            }
        });
    }

    private void reliefSetup() {
        //--------------------------------------------------
        //Reliefs
        editTxtRelief.setCursorVisible(false);
        editTxtRelief.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Show dialog with multi choice to choose items.
                when selected add to selected list
                when unchecked remove from list
                Show the names in edit text
                Enable effective list
                 */
                new MaterialDialog.Builder(getContext())
                        .title("Select reliefs taken")
                        .items(reliefs)
                        .itemsCallbackMultiChoice(selectedReliefIndexes, new MaterialDialog.ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                String selectedStr = "";
                                selectedReliefs.clear();
                                selectedReliefIndexes = which;

                                viewLayoutRecordEffectiveRelief.setVisibility(View.GONE);
                                editTxtReliefEffective.setText("");
                                selectedEffectiveReliefIndexes = null;

                                for (Integer integer : which) {
                                    String name = reliefs.get(integer).toString();
                                    selectedStr = selectedStr + ", " + name;
                                    selectedReliefs.add(reliefs.get(integer));
                                }
                                if (selectedStr.contains(",")) {
                                    selectedStr = selectedStr.replaceFirst(",", "");
                                }

                                editTxtRelief.setText(selectedStr);
                                if (selectedReliefs.size() > 0) {
                                    viewLayoutRecordEffectiveRelief.setVisibility(View.VISIBLE);
                                }

                                return true; // allow selection
                            }
                        })
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                AppUtil.showToast(getContext(), "Selection cleared");
//
//                                if (reliefs.size() > 0) {
//                                    selectedReliefs.clear();
//                                    dialog.clearSelectedIndices();
//                                }
//                                selectedReliefIndexes = null;
//                                selectedEffectiveReliefIndexes = null;
//                                viewLayoutRecordEffectiveRelief.setVisibility(View.GONE);
//                                editTxtRelief.setText("");
//                                editTxtReliefEffective.setText("");
                                handleAnswerAdd("Reliefs");
                            }
                        })
                        .positiveText(R.string.confirmButtonDialog)
                        .neutralText(R.string.addNewAnswer)
                        .show();
            }
        });
    }

    /**
     * Save record,
     * In subclasses handle this separately
     */
    private void saveFullRecord() {
        Log.d("AddRecordFullFragment", "saveFullRecord");
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
                    return RecordController.addNewRecord(getFullRecordBuilder().createRecord(), 2);//Level 2
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    if (result) {
                        AppUtil.showToast(getContext(), "Record was saved successfully");
                        if (mCallback != null) {
                            mCallback.onAddRecordFullRequest(0);
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

    //
    //
    //

    /**
     * Parent activity must implement this interface to communicate
     */
    public interface AddRecordFullFragmentListener {
        /**
         * Parent activity must implement this method to communicate
         */
        void onAddRecordFullRequest(int request);
    }

    /**
     * Async task to add new Answer
     */
    private class AddNewAnswerFullTask extends AsyncTask<String, Void, Long> {

        private String answerSection;

        private AddNewAnswerFullTask(String answerSection) {
            this.answerSection = answerSection;
        }

        @Override
        protected Long doInBackground(String... answer) {
            Log.d("AddNewAnswerTask", " doInBackground - save answers");

            String name = answer[0].trim();
            long response = -1;
            int id;
            switch (answerSection) {
                case "Locations": {
                    id = LocationController.getLastRecordId() + 1;
                    response = LocationController.addLocation(new Location(id, name));
                    break;
                }
                case "Pain areas": {
                    id = BodyAreaController.getLastRecordId() + 1;
                    response = BodyAreaController.addBodyArea(new BodyArea(id, name));
                    break;
                }
                case "Medicines": {
                    int priority = -1;
                    id = MedicineController.getLastRecordId() + 1;
                    ArrayList<Medicine> tmpLst = MedicineController.getAllMedicines(false);
                    if (tmpLst.size() > 0) {
                        Medicine medicine = tmpLst.get(tmpLst.size() - 1);
                        if (medicine != null) {
                            priority = medicine.getPriority();
                        }
                    }
                    priority++;
                    response = MedicineController.addMedicine(new Medicine(id, name, priority, false));
                    break;
                }
                case "Reliefs": {
                    int priority = -1;
                    id = ReliefController.getLastRecordId() + 1;
                    ArrayList<Relief> tmpLst = ReliefController.getAllReliefs(false);
                    if (tmpLst.size() > 0) {
                        Relief relief = tmpLst.get(tmpLst.size() - 1);
                        if (relief != null) {
                            priority = relief.getPriority();
                        }
                    }
                    priority++;
                    response = ReliefController.addRelief(new Relief(id, name, priority, false));
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
                    case "Locations": {
                        new AsyncTask<String, Void, String>() {
                            @Override
                            protected String doInBackground(String... params) {
                                locations = LocationController.getAllLocations(true);
                                return "";
                            }
                        }.execute();
                        break;
                    }
                    case "Pain areas": {
                        new AsyncTask<String, Void, String>() {
                            @Override
                            protected String doInBackground(String... params) {
                                bodyAreas = BodyAreaController.getAllBodyAreas(true);
                                return "";
                            }
                        }.execute();
                        break;
                    }
                    case "Medicines": {
                        new AsyncTask<String, Void, String>() {
                            @Override
                            protected String doInBackground(String... params) {
                                medicines = MedicineController.getAllMedicines(true);
                                return "";
                            }
                        }.execute();
                        break;
                    }
                    case "Reliefs": {
                        new AsyncTask<String, Void, String>() {
                            @Override
                            protected String doInBackground(String... params) {
                                reliefs = ReliefController.getAllReliefs(true);
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
