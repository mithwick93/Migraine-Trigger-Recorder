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
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

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
import shehan.com.migrainetrigger.view.fragment.record.view.ViewRecordSingleFragment;

import static shehan.com.migrainetrigger.utility.AppUtil.getTimeStampDate;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddRecordFullFragment extends AddRecordIntermediateFragment {
    //full
    //Controls
    protected EditText editTxtLocation;
    protected EditText editTxtBodyAreas;
    protected EditText editTxtMedicine;
    protected EditText editTxtMedicineEffective;
    protected EditText editTxtRelief;
    protected EditText editTxtReliefEffective;
    protected RelativeLayout viewLayoutRecordEffectiveMedicine;
    protected RelativeLayout viewLayoutRecordEffectiveRelief;

    //Data storage
    protected ArrayList<Location> locations;
    protected ArrayList<BodyArea> bodyAreas;
    protected ArrayList<Medicine> medicines;
    protected ArrayList<Relief> reliefs;
    //Choices
    protected Location location;
    protected ArrayList<BodyArea> selectedBodyAreas;
    protected ArrayList<Medicine> selectedMedicines;
    protected ArrayList<Relief> selectedReliefs;
    //Maintain indexes
    protected int selectedLocation;
    protected Integer[] selectedBodyIndexes;
    protected Integer[] selectedMedicineIndexes;
    protected Integer[] selectedReliefIndexes;
    protected Integer[] selectedEffectiveMedicineIndexes;
    protected Integer[] selectedEffectiveReliefIndexes;

    //Callback
    private AddRecordFullListener mCallback;

    public AddRecordFullFragment() {
        // Required empty public constructor
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
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof ViewRecordSingleFragment.OnFragmentInteractionListener) {
            Log.w("AddRecordFull-onAttach", "Context instanceof ViewRecordSingleFragment.OnFragmentInteractionListener");
            return;
        }
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (AddRecordFullListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement AddRecordFullListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //override this in sub classes
        int id = item.getItemId();
        if (id == R.id.action_confirm) {
            chooseSaveOrSummery();
            return true;
        } else if (id == R.id.action_refresh) {
            showWeather();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public String toString() {
        return "Full";
    }

    //
    //
    //

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
    //
    protected void initFullControls(View view) {
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
                locations = LocationController.getAllLocations();
                bodyAreas = BodyAreaController.getAllBodyAreas();
                medicines = MedicineController.getAllMedicines();
                reliefs = ReliefController.getAllReliefs();
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

//--------------------------------------------------

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
                                showToast(getContext(), "Selection cleared");
                                location = null;
                                selectedLocation = -1;
                                editTxtLocation.setText("");
                                dialog.dismiss();
                            }
                        })
                        .alwaysCallSingleChoiceCallback()
                        .negativeText(R.string.clear_selection)
                        .show();
            }
        });


//--------------------------------------------------

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
                                showToast(getContext(), "Selection cleared");
                                dialog.clearSelectedIndices();
                                selectedBodyAreas.clear();
                                selectedBodyIndexes = null;
                                editTxtBodyAreas.setText("");
                            }
                        })
                        .positiveText(R.string.confirmButtonDialog)
                        .neutralText(R.string.clear_selection)
                        .show();
            }
        });

//--------------------------------------------------

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
                                showToast(getContext(), "Selection cleared");
                                dialog.clearSelectedIndices();
                                selectedMedicines.clear();
                                selectedMedicineIndexes = null;
                                selectedEffectiveMedicineIndexes = null;
                                viewLayoutRecordEffectiveMedicine.setVisibility(View.GONE);
                                editTxtMedicine.setText("");
                                editTxtMedicineEffective.setText("");

                            }
                        })
                        .positiveText(R.string.confirmButtonDialog)
                        .neutralText(R.string.clear_selection)
                        .show();
            }
        });

//--------------------------------------------------

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
                                showToast(getContext(), "Selection cleared");
                                dialog.clearSelectedIndices();
                                selectedReliefs.clear();
                                selectedReliefIndexes = null;
                                selectedEffectiveReliefIndexes = null;
                                viewLayoutRecordEffectiveRelief.setVisibility(View.GONE);
                                editTxtRelief.setText("");
                                editTxtReliefEffective.setText("");

                            }
                        })
                        .positiveText(R.string.confirmButtonDialog)
                        .neutralText(R.string.clear_selection)
                        .show();
            }
        });


//--------------------------------------------------

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
                                    showToast(getContext(), "Selection cleared");
                                    dialog.clearSelectedIndices();

                                    selectedMedicines.clear();
                                    for (Integer integer : selectedMedicineIndexes) {
                                        Medicine m = medicines.get(integer);
                                        selectedMedicines.add(new MedicineBuilder().setMedicineId(m.getMedicineId()).setMedicineName(m.getMedicineName()).setPriority(m.getPriority()).createMedicine());
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

//--------------------------------------------------

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
                                    showToast(getContext(), "Selection cleared");
                                    dialog.clearSelectedIndices();

                                    selectedReliefs.clear();
                                    for (Integer integer : selectedReliefIndexes) {
                                        Relief r = reliefs.get(integer);
                                        selectedReliefs.add(new ReliefBuilder().setReliefId(r.getReliefId()).setReliefName(r.getReliefName()).setPriority(r.getPriority()).createRelief());
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

//--------------------------------------------------
        //start time

    }

    /**
     * Choose to save record or get weather
     */
    private void chooseSaveOrSummery() {

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
                String tmpStr = String.valueOf(startDate[2]) + "/" + String.valueOf(startDate[1]) + "/" + String.valueOf(startDate[0]) + " "
                        + String.valueOf(startTime[0]) + ":" + String.valueOf(startTime[1]) + ":0";

                startTimestamp = getTimeStampDate(tmpStr);
            } else {
                String tmpStr = String.valueOf(startDate[2]) + "/" + String.valueOf(startDate[1]) + "/" + String.valueOf(startDate[0]) + " 0:0:0";
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
                String tmpStr = String.valueOf(endDate[2]) + "/" + String.valueOf(endDate[1]) + "/" + String.valueOf(endDate[0]) + " "
                        + String.valueOf(endTime[0]) + ":" + String.valueOf(endTime[1]) + ":0";
                endTimestamp = getTimeStampDate(tmpStr);
            } else {
                String tmpStr = String.valueOf(endDate[2]) + "/" + String.valueOf(endDate[1]) + "/" + String.valueOf(endDate[0]) + " 0:0:0";
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
                    return RecordController.addNewRecord(getFullRecordBuilder().createRecord(), 2);//Level 2
                }

                @Override
                protected void onPostExecute(Boolean result) {
                    if (result) {
                        showToast(getContext(), "Record was saved successfully");
                        if (mCallback != null) {
                            mCallback.onFullRecordInteraction(0);
                        }

                    } else {
                        showToast(getContext(), "Record save failed");
                    }
                }
            }.execute();

        } else {
            showMsg(getContext(), "Start time is greater than the end time");
        }
    }
    //
    //
    //

    /**
     * Parent activity must implement this interface to communicate
     */
    public interface AddRecordFullListener {
        /**
         * Parent activity must implement this method to communicate
         */
        void onFullRecordInteraction(int request);
    }

}
