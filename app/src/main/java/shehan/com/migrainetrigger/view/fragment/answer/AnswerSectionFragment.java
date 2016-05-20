package shehan.com.migrainetrigger.view.fragment.answer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.utility.AppUtil;


public class AnswerSectionFragment extends Fragment {

    private static final String ARG_ANSWER_SECTION = "answerSection";

    private String answerSection;

    public AnswerSectionFragment() {
        // Required empty public constructor
    }

    /**
     * Static method to get new instance of this fragment
     *
     * @param answerSection answer section
     * @return AnswerSectionFragment
     */
    public static AnswerSectionFragment newInstance(String answerSection) {

        AnswerSectionFragment fragment = new AnswerSectionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ANSWER_SECTION, answerSection);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            answerSection = getArguments().getString(ARG_ANSWER_SECTION);
        } else {
            Log.e("AnswerSectionFragment", "Invalid answer Section");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_answer_section, container, false);
        AppUtil.showToast(getContext(), answerSection);
        init(view);
        return view;
    }

    private void init(View view) {
        //set weather to enable reorder
        //Load from async task the answers
        //show recycler view with answers
        //Show add new answer on toolbar


        //on item click show dialog to rename
        //on long press enter multi selection mode and delete
        //on drag and drop reorder according to priority
        //On add new buton click, show dialog to get name, place at bottom , add to db


    }
}
