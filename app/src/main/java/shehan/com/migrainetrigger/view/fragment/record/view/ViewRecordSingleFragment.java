package shehan.com.migrainetrigger.view.fragment.record.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import shehan.com.migrainetrigger.R;
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_record_single, container, false);
        setHasOptionsMenu(true);

        initFullControls(view);
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
}
