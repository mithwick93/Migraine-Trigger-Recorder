package shehan.com.migrainetrigger.view.fragment.main;

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
import shehan.com.migrainetrigger.view.adapter.SeverityViewAdapter;
import shehan.com.migrainetrigger.view.model.SeverityData;

/**
 * A simple {@link Fragment} subclass.
 */
public class SeverityFragment extends Fragment {

    public SeverityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_severity, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.severity_recycler_view);

        // this is data for recycler view
        SeverityData severityData[] = {
                new SeverityData("Hurts a bit", "Pain is present but does not limit activity", R.drawable.num_1),
                new SeverityData("Hurts a little", "Pain is present but does not limit activity", R.drawable.num_2),
                new SeverityData("Hurts little more", "Can do most activities", R.drawable.num_3),
                new SeverityData("Mild", "Can do most activities", R.drawable.num_4),
                new SeverityData("Hurts more", "Unable to do some activities", R.drawable.num_5),
                new SeverityData("Moderate", "Unable to do some activities", R.drawable.num_6),
                new SeverityData("Hurts a lot", "Unable to do most activities", R.drawable.num_7),
                new SeverityData("Severe", "Unable to do most activities", R.drawable.num_8),
                new SeverityData("Hurts the most ", "Unable to do any activity", R.drawable.num_9),
                new SeverityData("Worst ever", "Unable to do any activity", R.drawable.num_10)
        };


        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 3. create an adapter
        SeverityViewAdapter severityViewAdapter = new SeverityViewAdapter(severityData);

        // 4. set adapter
//      recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(severityViewAdapter);

        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
