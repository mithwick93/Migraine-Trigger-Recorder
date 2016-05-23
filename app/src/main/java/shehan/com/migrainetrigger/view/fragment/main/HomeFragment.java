package shehan.com.migrainetrigger.view.fragment.main;


import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
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
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        new GetStatusTask(txtStatus).execute();//Run async task to obtain record status
    }

    /**
     * Async task to initialize db and get last record
     */
    private class GetStatusTask extends AsyncTask<String, Void, String> {

        private TextView mTxtStatus;

        GetStatusTask(TextView mTxtStatus) {
            Log.d("GetStatusTask", " constructor");
            this.mTxtStatus = mTxtStatus;
        }

        @Override
        protected String doInBackground(String... params) {
            Log.d("GetStatusTask", " doInBackground - query status");
            return RecordController.getStatus();//
        }

        @Override
        protected void onPostExecute(String status) {
            Log.d("GetStatusTask", " onPostExecute - update ui");
            mTxtStatus.setText(status);
            if (status.startsWith("N")) {
                mTxtStatus.setTextColor(Color.parseColor("#9E9E9E"));
            } else if (status.startsWith("M")) {
                mTxtStatus.setTextColor(fetchAccentColor());
            } else if (status.startsWith("G")) {
                mTxtStatus.setTextColor(Color.parseColor("#009688"));
            }
        }

        private int fetchAccentColor() {
            TypedValue typedValue = new TypedValue();

            TypedArray a = HomeFragment.this.getActivity().obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorAccent});
            int color = a.getColor(0, 0);

            a.recycle();

            return color;
        }
    }
}
