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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.controller.RecordController;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ImageView imgImoji;
    private TextView txtHelp;
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
        txtHelp = (TextView) rootView.findViewById(R.id.txt_home_help);
        imgImoji = (ImageView) rootView.findViewById(R.id.img_view_emoji);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        new GetStatusTask().execute();//Run async task to obtain record status
    }

    /**
     * Async task to initialize db and get last record
     */
    private class GetStatusTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Log.d("GetStatusTask", " doInBackground - query status");
            return RecordController.getStatus();//
        }

        @Override
        protected void onPostExecute(String status) {
            Log.d("GetStatusTask", " onPostExecute - update ui");

            String[] randomStr = {"We hope you stay migraine free ", "Check out the reports section to analyse your migraines ", "Customize answers in answers section", "See severity section to understand the migraine intensity scale", "Customize application appearance in settings for better personalisation", "See F.A.Q. to get to know Migraines better "};

            String[] randomWish = {"Get well soon", "Hope you feel better soon", "See F.A.Q. for effective medicine,treatments and remedies"};

            final int maxRandomStr = randomStr.length;
            final int maxRandomWish = randomWish.length;


            if (status.startsWith("N")) {//No data
                txtStatus.setText(status);
                txtStatus.setTextColor(Color.parseColor("#9E9E9E"));
                txtHelp.setText(R.string.home_help_no_data);
                imgImoji.setBackgroundResource(R.drawable.ic_emoji_no_data);
            } else if (status.startsWith("M")) {//Migraine free
                txtStatus.setText(status);
                txtStatus.setTextColor(fetchAccentColor());
                //random
                int random = new Random().nextInt(maxRandomStr);
                txtHelp.setText(randomStr[random]);
                imgImoji.setBackgroundResource(R.drawable.ic_emoji_free);
            } else if (status.startsWith("S")) {//Migraine in action
                txtStatus.setTextColor(Color.parseColor("#009688"));
                int random = new Random().nextInt(maxRandomWish);
                txtStatus.setText(randomWish[random]);
                txtHelp.setText(status);
                imgImoji.setBackgroundResource(R.drawable.ic_emoji_sick);
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
