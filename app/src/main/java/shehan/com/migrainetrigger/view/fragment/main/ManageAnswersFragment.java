package shehan.com.migrainetrigger.view.fragment.main;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import shehan.com.migrainetrigger.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ManageAnswersFragment.OnManageAnswersFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ManageAnswersFragment extends Fragment {

    private CardView cardViewActivities;
    private CardView cardViewLocations;
    private CardView cardViewMedicines;
    private CardView cardViewPainAreas;
    private CardView cardViewReliefs;
    private CardView cardViewSymptoms;
    private CardView cardViewTriggers;
    private OnManageAnswersFragmentInteractionListener mCallback;

    public ManageAnswersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnManageAnswersFragmentInteractionListener) {
            mCallback = (OnManageAnswersFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnManageAnswersFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manage_answers, container, false);
        init(view);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }


    private void init(View view) {
        cardViewTriggers = (CardView) view.findViewById(R.id.card_view_answers_triggers);
        cardViewSymptoms = (CardView) view.findViewById(R.id.card_view_answers_symptoms);
        cardViewActivities = (CardView) view.findViewById(R.id.card_view_answers_activities);
        cardViewLocations = (CardView) view.findViewById(R.id.card_view_answers_locations);
        cardViewPainAreas = (CardView) view.findViewById(R.id.card_view_answers_pain_areas);
        cardViewMedicines = (CardView) view.findViewById(R.id.card_view_answers_medicines);
        cardViewReliefs = (CardView) view.findViewById(R.id.card_view_answers_reliefs);

        cardViewTriggers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.OnManageAnswersInteraction("Triggers");
            }
        });
        cardViewSymptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.OnManageAnswersInteraction("Symptoms");
            }
        });
        cardViewActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.OnManageAnswersInteraction("Activities");
            }
        });
        cardViewLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.OnManageAnswersInteraction("Locations");
            }
        });
        cardViewPainAreas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.OnManageAnswersInteraction("Pain areas");
            }
        });
        cardViewMedicines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.OnManageAnswersInteraction("Medicines");
            }
        });
        cardViewReliefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.OnManageAnswersInteraction("Reliefs");
            }
        });
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
    public interface OnManageAnswersFragmentInteractionListener {
        void OnManageAnswersInteraction(String answer);
    }
}
