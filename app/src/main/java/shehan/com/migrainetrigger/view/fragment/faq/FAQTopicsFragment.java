package shehan.com.migrainetrigger.view.fragment.faq;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.view.adapter.FAQTopicsViewAdapter;
import shehan.com.migrainetrigger.view.model.FAQTopic;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnTopicSelectedListener } interface
 * to handle interaction events.
 */
public class FAQTopicsFragment extends Fragment implements FAQTopicsViewAdapter.FAQTopicsViewClickListener {

    private OnTopicSelectedListener mCallback;

    public FAQTopicsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_faq_list, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.topics_recycler_view);

        // this is data fpr recycler view
        FAQTopic faqTopicsData[] = {
                new FAQTopic("Definition"),
                new FAQTopic("Symptoms"),
                new FAQTopic("Causes"),
                new FAQTopic("Risk factors"),
                new FAQTopic("Complications"),
                new FAQTopic("Tests and diagnosis"),
                new FAQTopic("Treatments and drugs"),
                new FAQTopic("Lifestyle and home remedies"),
                new FAQTopic("Alternative medicine"),
                new FAQTopic("Prevention")
        };

        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 3. create an adapter
        FAQTopicsViewAdapter faqTopicsViewAdapter = new FAQTopicsViewAdapter(getContext(), this, faqTopicsData);

        // 4. set adapter
        recyclerView.setAdapter(faqTopicsViewAdapter);

        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnTopicSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnTopicSelectedListener");
        }
    }

    @Override
    public void recyclerViewListClicked(int clickPosition) {
        mCallback.onFragmentInteraction(clickPosition);
    }

    //Parent activity must implement this interface to communicate
    public interface OnTopicSelectedListener {
        void onFragmentInteraction(int clickPosition);
    }
}
