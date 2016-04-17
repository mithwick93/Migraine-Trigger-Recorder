package shehan.com.migrainetrigger.view.fragment.record;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import shehan.com.migrainetrigger.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddRecordFullFragment extends Fragment {
    private Toast mToast;
    private AddRecordFullListener mCallback;

    private EditText edit_txt_start_date;
    private EditText edit_txt_start_time;
    private EditText edit_txt_end_date;
    private EditText edit_txt_end_time;
    private TextView view_txt_intensity;

    private EditText edit_txt_triggers;
    private EditText edit_txt_symptoms;
    private EditText edit_txt_activities;


    private EditText edit_txt_location;
    private EditText edit_txt_body_areas;
    private EditText edit_txt_medicine;
    private EditText edit_txt_medicine_effective;
    private EditText edit_txt_relief;
    private EditText edit_txt_relief_effective;

    public AddRecordFullFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_record_full, container, false);


        edit_txt_start_date = (EditText) view.findViewById(R.id.txt_record_start_date);
        edit_txt_start_time = (EditText) view.findViewById(R.id.txt_record_start_time);
        edit_txt_end_date = (EditText) view.findViewById(R.id.txt_record_end_date);
        edit_txt_end_time = (EditText) view.findViewById(R.id.txt_record_end_time);
        view_txt_intensity = (TextView) view.findViewById(R.id.txt_record_intensity);

        edit_txt_triggers = (EditText) view.findViewById(R.id.txt_record_triggers);
        edit_txt_symptoms = (EditText) view.findViewById(R.id.txt_record_symptoms);
        edit_txt_activities = (EditText) view.findViewById(R.id.txt_record_activities);

        edit_txt_location = (EditText) view.findViewById(R.id.txt_record_location);
        edit_txt_body_areas = (EditText) view.findViewById(R.id.txt_record_affected_areas);
        edit_txt_medicine = (EditText) view.findViewById(R.id.txt_record_medicine);
        edit_txt_medicine_effective = (EditText) view.findViewById(R.id.txt_record_effective_medicine);
        edit_txt_relief = (EditText) view.findViewById(R.id.txt_record_relief);
        edit_txt_relief_effective = (EditText) view.findViewById(R.id.txt_record_effective_reliefs);

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
                    + " must implement OnTopicSelectedListener");
        }
    }

    @Override
    public String toString() {
        return "Full";
    }

    private void showToast(String message) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        mToast.show();
    }

    //Parent activity must implement this interface to communicate
    public interface AddRecordFullListener {
        void onFullRecordInteraction();
    }
}
