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
import shehan.com.migrainetrigger.data.model.BodyArea;
import shehan.com.migrainetrigger.data.model.Location;
import shehan.com.migrainetrigger.data.model.Medicine;
import shehan.com.migrainetrigger.data.model.Relief;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddRecordFullFragment extends AddRecordIntermediateFragment {
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

        bodyAreas = new ArrayList<>();
        medicines = new ArrayList<>();
        reliefs = new ArrayList<>();
    }

    //Parent activity must implement this interface to communicate
    public interface AddRecordFullListener {
        void onFullRecordInteraction();
    }
}
