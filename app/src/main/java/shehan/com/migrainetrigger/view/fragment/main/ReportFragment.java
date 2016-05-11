package shehan.com.migrainetrigger.view.fragment.main;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.controller.RecordController;

import static shehan.com.migrainetrigger.utility.AppUtil.getTimeStampDate;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReportFragment.OnReportFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ReportFragment extends Fragment {

    private OnReportFragmentInteractionListener mCallback;
    private Toast mToast;

    private TextView txtViewFrom;
    private TextView txtViewTo;
    private TextView txtViewTotal;
    private TextView txtViewAverage;

    private CardView cardViewReportSummery;

    private int[] fromDate;
    private int[] toDate;
    private int mYear, mMonth, mDay;

    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        initReport(view);
        loadRecordData(view);

        setHasOptionsMenu(true);
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.view_report_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //override this in sub classes
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            showToast(getContext(), "Refreshing ...");
            refreshSummery();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnReportFragmentInteractionListener) {
            mCallback = (OnReportFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }


    private void initReport(View view) {
        //Load ui
        txtViewFrom = (TextView) view.findViewById(R.id.txt_report_from);
        txtViewTo = (TextView) view.findViewById(R.id.txt_report_to);
        txtViewTotal = (TextView) view.findViewById(R.id.txt_report_total);
        txtViewAverage = (TextView) view.findViewById(R.id.txt_report_average);
        cardViewReportSummery = (CardView) view.findViewById(R.id.card_report_summery);

        cardViewReportSummery.setVisibility(View.GONE);

        fromDate = new int[3];
        toDate = new int[3];

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);


        txtViewFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txtViewFrom.setText(String.format(Locale.getDefault(), "%02d-%02d-%d", dayOfMonth, monthOfYear + 1, year));
                                mYear = fromDate[0] = year;
                                mMonth = fromDate[1] = monthOfYear + 1;
                                mDay = fromDate[2] = dayOfMonth;
                            }
                        }, mYear, mMonth - 1, mDay);
                datePickerDialog.show();
            }


        });

        txtViewTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txtViewTo.setText(String.format(Locale.getDefault(), "%02d-%02d-%d", dayOfMonth, monthOfYear + 1, year));
                                mYear = toDate[0] = year;
                                mMonth = toDate[1] = monthOfYear + 1;
                                mDay = toDate[2] = dayOfMonth;
                            }
                        }, mYear, mMonth - 1, mDay);
                datePickerDialog.show();
            }
        });

    }

    private void loadRecordData(View view) {
        //Load all data
        new LoadReportDatesTask().execute();
    }

    private void refreshSummery() {

        Timestamp fromTimestamp;

        //Check for start date

        String tmpFrom = String.valueOf(fromDate[0]) + "-" + String.valueOf(fromDate[1]) + "-" + String.valueOf(fromDate[2]) + " 00:00:00";
        fromTimestamp = getTimeStampDate(tmpFrom);


        Calendar c = Calendar.getInstance();
        if (fromTimestamp.after(c.getTime())) {
            showMsg(getContext(), "Start Date is past current time");
            return;
        }

        //Check for end date
        Timestamp toTimestamp;

        String tmpTo = String.valueOf(toDate[0]) + "-" + String.valueOf(toDate[1]) + "-" + String.valueOf(toDate[2]) + " 00:00:00";
        toTimestamp = getTimeStampDate(tmpTo);


        if (toTimestamp != null) {
            if (toTimestamp.after(c.getTime())) {
                showMsg(getContext(), "End Date is past current time");
                return;
            }
        }

        //validate times
        if ((toTimestamp != null && fromTimestamp.before(toTimestamp)) || toTimestamp == null) {
            new LoadReportSummeryTask(fromTimestamp, toTimestamp).execute();
        } else {
            showMsg(getContext(), "Start time is greater than the end time");
        }

    }

    protected void showToast(Context context, String message) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        mToast.show();
    }

    /**
     * Show dialog box msg
     *
     * @param context context to show text
     * @param msg     string msg
     */
    protected void showMsg(Context context, String msg) {
        new MaterialDialog.Builder(context)
                .content(msg)
                .negativeText(R.string.cancelButtonDialog)
                .show();
    }

    /**
     * @param context context to show text
     * @param msg     string msg
     * @param title   title of msg dialog
     */
    protected void showMsg(Context context, String msg, String title) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(msg)
                .negativeText(R.string.cancelButtonDialog)
                .show();
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
    public interface OnReportFragmentInteractionListener {
        void onReportFragmentInteraction(int request);
    }

    /**
     * Async task to load data
     */
    private class LoadReportDatesTask extends AsyncTask<String, Void, Timestamp> {


        private void setFromDate(Timestamp startTime) {

            long timestamp = startTime.getTime();
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(timestamp);
            mYear = fromDate[0] = cal.get(Calendar.YEAR);
            mMonth = fromDate[1] = cal.get(Calendar.MONTH) + 1;
            mDay = fromDate[2] = cal.get(Calendar.DAY_OF_MONTH);

            txtViewFrom.setText(String.format(Locale.getDefault(), "%02d-%02d-%d ", mDay, mMonth, mYear));

        }

        private void setToDate() {

            java.util.Date date = new java.util.Date();
            long timestamp = new Timestamp(date.getTime()).getTime();
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(timestamp);
            mYear = toDate[0] = cal.get(Calendar.YEAR);
            mMonth = toDate[1] = cal.get(Calendar.MONTH) + 1;
            mDay = toDate[2] = cal.get(Calendar.DAY_OF_MONTH);

            txtViewTo.setText(String.format(Locale.getDefault(), "%02d-%02d-%d", mDay, mMonth, mYear));
        }

        @Override
        protected Timestamp doInBackground(String... params) {
            Log.d("LoadReportDatesTask", "doInBackground ");
            return RecordController.getFirstRecordStartTimestamp();
        }

        @Override
        protected void onPostExecute(Timestamp timestamp) {
            Log.d("LoadReportDatesTask", "onPostExecute ");

            setFromDate(timestamp);
            setToDate();

            refreshSummery();
        }
    }

    /**
     * Async task to load summery
     */
    private class LoadReportSummeryTask extends AsyncTask<String, Void, ArrayList<Object>> {

        private Timestamp from;
        private Timestamp to;

        public LoadReportSummeryTask(Timestamp from, Timestamp to) {
            this.from = from;
            this.to = to;
        }

        private void setTotal(int total) {
            txtViewTotal.setText(String.valueOf(total));
        }

        private void setAverage(String average) {
            txtViewAverage.setText(average);
        }

        @Override
        protected ArrayList<Object> doInBackground(String... params) {
            Log.d("LoadReportSummeryTask", "doInBackground ");
            ArrayList<Object> summeryList = new ArrayList<>();
            summeryList.add(RecordController.getTotalRecords(from, to));

            summeryList.add(RecordController.getAverage(from, to));

            return summeryList;
        }

        @Override
        protected void onPostExecute(ArrayList<Object> summeryList) {
            Log.d("LoadReportSummeryTask", "onPostExecute ");

            if (summeryList.size() > 0) {
                setTotal((int) summeryList.get(0));
                cardViewReportSummery.setVisibility(View.VISIBLE);
            }
            if (summeryList.size() == 2) {
                setAverage((String) summeryList.get(1));
            }

        }
    }


}
