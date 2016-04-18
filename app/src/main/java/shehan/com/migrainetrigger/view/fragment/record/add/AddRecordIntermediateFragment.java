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
import shehan.com.migrainetrigger.data.model.LifeActivity;
import shehan.com.migrainetrigger.data.model.Record;
import shehan.com.migrainetrigger.data.model.Symptom;
import shehan.com.migrainetrigger.data.model.Trigger;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddRecordIntermediateFragment extends AddRecordBasicFragment {
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
        //override this in sub classes
        int id = item.getItemId();
        if (id == R.id.action_confirm) {
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

        activities = new ArrayList<>();
        triggers = new ArrayList<>();
        symptoms = new ArrayList<>();

    }


    //Parent activity must implement this interface to communicate
    public interface AddRecordIntermediateListener {
        void onIntermediateRecordInteraction();
    }

}
