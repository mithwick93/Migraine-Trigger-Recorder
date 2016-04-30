package shehan.com.migrainetrigger.view.fragment.main;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.controller.RecordController;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private TextView txtStatus;

    public HomeFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        txtStatus = (TextView) rootView.findViewById(R.id.txt_home_status);
        //  updateStatus();
        // Inflate the layout for this fragment

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateStatus();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void updateStatus() {
        String status = RecordController.getStatus();
        txtStatus.setText(status);
        if (status.startsWith("N")) {
            txtStatus.setTextColor(Color.parseColor("#9E9E9E"));
        } else if (status.startsWith("M")) {
            txtStatus.setTextColor(getResources().getColor(R.color.colorAccent));
        } else if (status.startsWith("G")) {
            txtStatus.setTextColor(Color.parseColor("#009688"));
        }
    }
}
