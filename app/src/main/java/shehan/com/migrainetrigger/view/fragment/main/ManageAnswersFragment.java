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
 * {@link ManageAnswersFragmentListener} interface
 * to handle interaction events.
 */
public class ManageAnswersFragment extends Fragment {

    private ManageAnswersFragmentListener mCallback;

    public ManageAnswersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ManageAnswersFragmentListener) {
            mCallback = (ManageAnswersFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ManageAnswersFragmentListener");
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
        CardView cardViewTriggers = (CardView) view.findViewById(R.id.card_view_answers_triggers);
        CardView cardViewSymptoms = (CardView) view.findViewById(R.id.card_view_answers_symptoms);
        CardView cardViewActivities = (CardView) view.findViewById(R.id.card_view_answers_activities);
        CardView cardViewLocations = (CardView) view.findViewById(R.id.card_view_answers_locations);
        CardView cardViewPainAreas = (CardView) view.findViewById(R.id.card_view_answers_pain_areas);
        CardView cardViewMedicines = (CardView) view.findViewById(R.id.card_view_answers_medicines);
        CardView cardViewReliefs = (CardView) view.findViewById(R.id.card_view_answers_reliefs);

        cardViewTriggers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.OnAnswerRawClick("Triggers");
            }
        });
        cardViewSymptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.OnAnswerRawClick("Symptoms");
            }
        });
        cardViewActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.OnAnswerRawClick("Activities");
            }
        });
        cardViewLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.OnAnswerRawClick("Locations");
            }
        });
        cardViewPainAreas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.OnAnswerRawClick("Pain areas");
            }
        });
        cardViewMedicines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.OnAnswerRawClick("Medicines");
            }
        });
        cardViewReliefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.OnAnswerRawClick("Reliefs");
            }
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface ManageAnswersFragmentListener {
        void OnAnswerRawClick(String answer);
    }
}
