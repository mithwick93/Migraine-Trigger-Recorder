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
import shehan.com.migrainetrigger.view.model.FAQViewData;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TopicSelectedListener } interface
 * to handle interaction events.
 */
public class FAQTopicsFragment extends Fragment implements FAQTopicsViewAdapter.FAQTopicsRowClickListener {

    private TopicSelectedListener mCallback;

    public FAQTopicsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (TopicSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement TopicSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_faq_list, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.topics_recycler_view);

        // this is data for recycler view
        FAQViewData faqTopicsData[] = {
                new FAQViewData("Definition"),
                new FAQViewData("Symptoms"),
                new FAQViewData("Causes"),
                new FAQViewData("Risk factors"),
                new FAQViewData("Complications"),
                new FAQViewData("Tests and diagnosis"),
                new FAQViewData("Treatments and drugs"),
                new FAQViewData("Lifestyle and home remedies"),
                new FAQViewData("Alternative medicine"),
                new FAQViewData("Prevention")
        };

        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 3. create an adapter
        FAQTopicsViewAdapter faqTopicsViewAdapter = new FAQTopicsViewAdapter(this, faqTopicsData);

        // 4. set adapter
        recyclerView.setAdapter(faqTopicsViewAdapter);

        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onRowClicked(int clickPosition) {
        mCallback.onTopicRawClicked(clickPosition);
    }

    //Parent activity must implement this interface to communicate
    public interface TopicSelectedListener {
        void onTopicRawClicked(int clickPosition);
    }
}
