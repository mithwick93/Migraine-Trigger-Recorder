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
import shehan.com.migrainetrigger.controller.BodyAreaController;
import shehan.com.migrainetrigger.controller.MedicineController;
import shehan.com.migrainetrigger.controller.ReliefController;
import shehan.com.migrainetrigger.data.model.BodyArea;
import shehan.com.migrainetrigger.data.model.Location;
import shehan.com.migrainetrigger.data.model.Medicine;
import shehan.com.migrainetrigger.data.model.Relief;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddRecordFullFragment extends AddRecordIntermediateFragment {
    /**
     * Parent activity must implement this interface to communicate
     */
    public interface AddRecordFullListener {
        /**
         * Parent activity must implement this method to communicate
         */
        void onFullRecordInteraction();
    }

    private AddRecordFullListener mCallback;

    //full
    private EditText edit_txt_location;
    private EditText edit_txt_body_areas;
    private EditText edit_txt_medicine;
    private EditText edit_txt_medicine_effective;
    private EditText edit_txt_relief;
    private EditText edit_txt_relief_effective;


    private Location location;
    private ArrayList<BodyArea> bodyAreas;
    private ArrayList<Medicine> medicines;
    private ArrayList<Relief> reliefs;

    //
    private ArrayList<Medicine> effectiveMedicines;
    private ArrayList<Relief> effectiveReliefs;

    private ArrayList<BodyArea> selectedBodyAreas;
    private ArrayList<Medicine> selectedMedicines;
    private ArrayList<Relief> selectedReliefs;
    private ArrayList<Medicine> selectedEffectiveMedicines;
    private ArrayList<Relief> selectedEffectiveReliefs;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        //override this in sub classes
        int id = item.getItemId();
        if (id == R.id.action_confirm) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public String toString() {
        return "Full";
    }


    private void initFullControls(View view) {
        Log.d("AddRecordFullFragment", "initFullControls ");
        super.initIntermediateControls(view);

        edit_txt_location = (EditText) view.findViewById(R.id.txt_record_location);
        edit_txt_body_areas = (EditText) view.findViewById(R.id.txt_record_affected_areas);
        edit_txt_medicine = (EditText) view.findViewById(R.id.txt_record_medicine);
        edit_txt_medicine_effective = (EditText) view.findViewById(R.id.txt_record_effective_medicine);
        edit_txt_relief = (EditText) view.findViewById(R.id.txt_record_relief);
        edit_txt_relief_effective = (EditText) view.findViewById(R.id.txt_record_effective_reliefs);

        location = null;
        bodyAreas = BodyAreaController.getAllBodyAreas();
        medicines = MedicineController.getAllMedicines();
        reliefs = ReliefController.getAllReliefs();

        effectiveMedicines = new ArrayList<>();
        effectiveReliefs = new ArrayList<>();

        selectedBodyAreas = new ArrayList<>();
        selectedMedicines = new ArrayList<>();
        selectedReliefs = new ArrayList<>();
        selectedEffectiveMedicines = new ArrayList<>();
        selectedEffectiveReliefs = new ArrayList<>();

        edit_txt_medicine_effective.setEnabled(false);

        edit_txt_relief_effective.setEnabled(false);


//--------------------------------------------------
        //start time
        edit_txt_location.setCursorVisible(false);
        edit_txt_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Show dialog with single choice.
                when selected set to location
                when unchecked remove to location
                Show on edit txt
                 */
            }
        });
        edit_txt_location.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
           /*
           clear selected list
           remove location
           clear edit txt
            */
                return true;
            }
        });


//--------------------------------------------------
        //start time
        edit_txt_body_areas.setCursorVisible(false);
        edit_txt_body_areas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Show dialog with multi choice to choose items.
                when selected add to selected list
                when unchecked remove from list
                Show the names in edit text
                Enable effective list
                 */
            }
        });
        edit_txt_body_areas.setOnLongClickListener(new View.OnLongClickListener() {

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
        edit_txt_medicine.setCursorVisible(false);
        edit_txt_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Show dialog with multi choice to choose items.
                when selected add to selected list
                when unchecked remove from list
                Show the names in edit text
                Enable effective list
                 */
            }
        });
        edit_txt_medicine.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
           /*
           clear selected list
           clear effective list
           clear edit txt
           clear effective edit list
           disable effective list
            */
                return true;
            }
        });
//--------------------------------------------------
        //start time
        edit_txt_relief.setCursorVisible(false);
        edit_txt_relief.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Show dialog with multi choice to choose items.
                when selected add to selected list
                when unchecked remove from list
                Show the names in edit text
                Enable effective list
                 */
            }
        });
        edit_txt_relief.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
           /*
           clear selected list
           clear effective list
           clear edit txt
           clear effective edit list
           disable effective list
            */
                return true;
            }
        });

//--------------------------------------------------
//start time
        edit_txt_medicine_effective.setCursorVisible(false);
        edit_txt_medicine_effective.setOnClickListener(new View.OnClickListener() {
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
        edit_txt_medicine_effective.setOnLongClickListener(new View.OnLongClickListener() {

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
        edit_txt_relief_effective.setCursorVisible(false);
        edit_txt_relief_effective.setOnClickListener(new View.OnClickListener() {
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
        edit_txt_relief_effective.setOnLongClickListener(new View.OnLongClickListener() {

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

    }


}
