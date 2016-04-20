package shehan.com.migrainetrigger.view.fragment.record.add;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.controller.LifeActivityController;
import shehan.com.migrainetrigger.controller.SymptomController;
import shehan.com.migrainetrigger.controller.TriggerController;
import shehan.com.migrainetrigger.data.model.LifeActivity;
import shehan.com.migrainetrigger.data.model.Record;
import shehan.com.migrainetrigger.data.model.Symptom;
import shehan.com.migrainetrigger.data.model.Trigger;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddRecordIntermediateFragment extends AddRecordBasicFragment {
    /**
     * Parent activity must implement this interface to communicate
     */
    public interface AddRecordIntermediateListener {
        /**
         * Parent activity must implement this method to communicate
         */
        void onIntermediateRecordInteraction();
    }

    private AddRecordIntermediateListener mCallback;

    //intermediate
    protected EditText edit_txt_triggers;
    protected EditText edit_txt_symptoms;
    protected EditText edit_txt_activities;

    //domain objects
    private Record intermediateRecord;

    protected ArrayList<LifeActivity> activities;
    protected ArrayList<Trigger> triggers;
    protected ArrayList<Symptom> symptoms;

    //
    protected ArrayList<LifeActivity> selectedActivities;
    protected ArrayList<Trigger> selectedTriggers;
    protected ArrayList<Symptom> selectedSymptoms;

    //
    protected Integer[] selectedActivityIndexes;
    protected Integer[] selectedTriggerIndexes;
    protected Integer[] selectedSymptomsIndexes;

    public AddRecordIntermediateFragment() {
        // Required empty public constructor
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
    public void onAttach(Context context) {
        super.onAttach(context);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_confirm) {
            //Full record save call
            return true;
        } else if (id == R.id.action_refresh) {
            showWeather();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public String toString() {
        return "Intermediate";
    }


    /**
     * initiate intermediate controls
     * call this in sub classes onCreate
     *
     * @param view current view
     */
    protected void initIntermediateControls(View view) {
        Log.d("AddRecordInter", "initIntermediateControls ");
        super.initBasicControls(view);
        edit_txt_triggers = (EditText) view.findViewById(R.id.txt_record_triggers);
        edit_txt_symptoms = (EditText) view.findViewById(R.id.txt_record_symptoms);
        edit_txt_activities = (EditText) view.findViewById(R.id.txt_record_activities);

        activities = LifeActivityController.getAllActivities();
        triggers = TriggerController.getAllTriggers();
        symptoms = SymptomController.getAllSymptoms();

        selectedActivities = new ArrayList<>();
        selectedTriggers = new ArrayList<>();
        selectedSymptoms = new ArrayList<>();

        selectedActivityIndexes=new Integer[activities.size()];
        selectedActivityIndexes=new Integer[triggers.size()];
        selectedActivityIndexes=new Integer[symptoms.size()];

//--------------------------------------------------
        //start time
        edit_txt_triggers.setCursorVisible(false);
        edit_txt_triggers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Show dialog with multi choice to choose items.
                when selected add to selected list
                when unchecked remove from list
                Show the names in edit text
                 */

//                new MaterialDialog.Builder(getContext())
//                        .title("Select triggers")
//                        .items(triggers)
//                        .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
//                            @Override
//                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
//                                StringBuilder str = new StringBuilder();
//                                selectedTriggers.clear();
//                                for (int i = 0; i < which.length; i++) {
//                                    if (i > 0) str.append('\n');
//                                    str.append(which[i]);
//                                    str.append(": ");
//                                    str.append(text[i]);
//                                    selectedTriggers.add(triggers.get(i));
//                                }
//                                showToast(getContext(), str.toString());
//                                return true; // allow selection
//                            }
//                        })
//                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
//                            @Override
//                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                showToast(getContext(), "selection cleared");
//                                dialog.clearSelectedIndices();
//                                selectedTriggers.clear();
//                            }
//                        })
//                        .positiveText(R.string.confirmButtonDialog)
//                        .neutralText(R.string.clear_selection)
//                        .show();
            }
        });
        edit_txt_triggers.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
           /*
           clear selected list
           clear edit txt
            */
                selectedTriggers.clear();
                return true;
            }
        });
//--------------------------------------------------
        //start time
        edit_txt_symptoms.setCursorVisible(false);
        edit_txt_symptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Show dialog with multi choice to choose items.
                when selected add to selected list
                when unchecked remove from list
                Show the names in edit text
                 */
            }
        });
        edit_txt_symptoms.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
           /*
           clear selected list
           clear edit txt
            */
                return true;
            }
        });
//--------------------------------------------------
        //start time
        edit_txt_activities.setCursorVisible(false);
        edit_txt_activities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Show dialog with multi choice to choose items.
                when selected add to selected list
                when unchecked remove from list
                Show the names in edit text
                 */
            }
        });
        edit_txt_activities.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
           /*
           clear selected list
           clear edit txt
            */
                return true;
            }
        });


    }

}
