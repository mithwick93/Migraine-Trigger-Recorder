package shehan.com.migrainetrigger.view.fragment.main;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.controller.RecordController;
import shehan.com.migrainetrigger.controller.ReportController;
import shehan.com.migrainetrigger.utility.AppUtil;
import shehan.com.migrainetrigger.view.adapter.ReportViewAdapter;
import shehan.com.migrainetrigger.view.model.ReportViewData;

import static shehan.com.migrainetrigger.utility.AppUtil.getTimeStampDate;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReportFragment.OnReportFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ReportFragment extends Fragment {

    private OnReportFragmentInteractionListener mCallback;

    private View mView;

    private ProgressDialog nDialog;

    private TextView txtViewFrom;
    private TextView txtViewTo;
    private TextView txtViewTotal;
    private TextView txtViewIntensity;
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
            //showToast(getContext(), "Refreshing ...");
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

        this.mView = view;

        txtViewFrom = (TextView) view.findViewById(R.id.txt_report_from);
        txtViewTo = (TextView) view.findViewById(R.id.txt_report_to);
        txtViewTotal = (TextView) view.findViewById(R.id.txt_report_total);
        txtViewIntensity = (TextView) view.findViewById(R.id.txt_report_intensity);
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
                                AppUtil.showToast(getContext(), "Please refresh report");
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
                                AppUtil.showToast(getContext(), "Please refresh report");
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
            AppUtil.showMsg(getContext(), "Start Date is past current time");
            return;
        }

        //Check for end date
        Timestamp toTimestamp;

        String tmpTo = String.valueOf(toDate[0]) + "-" + String.valueOf(toDate[1]) + "-" + String.valueOf(toDate[2]) + " 00:00:00";
        toTimestamp = getTimeStampDate(tmpTo);


        if (toTimestamp != null) {
            if (toTimestamp.after(c.getTime())) {
                AppUtil.showMsg(getContext(), "End Date is past current time");
                return;
            }
        }

        //validate times
        if ((toTimestamp != null && fromTimestamp.before(toTimestamp)) || toTimestamp == null) {

            nDialog = new ProgressDialog(getActivity()); //Here I get an error: The constructor ProgressDialog(PFragment) is undefined
            nDialog.setMessage("Refreshing report...");
            nDialog.setTitle("Processing");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(false);
            nDialog.show();

            new LoadReportSummeryTask(fromTimestamp, toTimestamp).execute();
            new LoadReportStatisticsTask(mView, fromTimestamp, toTimestamp).execute();
        } else {
            AppUtil.showMsg(getContext(), "Start time is greater than the end time");
        }

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

            if (mYear == 2016 && mMonth == 1 && mDay == 1) {
                AppUtil.showToast(getContext(), "No records found in database");
                mCallback.onReportFragmentInteraction(0);
                return;
            }

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
            if (total <= 3) {
                txtViewTotal.setTextColor(Color.parseColor("#46cf9a"));
            } else if (total <= 7) {
                txtViewTotal.setTextColor(Color.parseColor("#adcb48"));
            } else if (total <= 10) {
                txtViewTotal.setTextColor(Color.parseColor("#dbb842"));
            } else {
                txtViewTotal.setTextColor(Color.parseColor("#f44336"));
            }
        }

        private void setIntensity(double intensity) {
            txtViewIntensity.setText(String.format(Locale.getDefault(), "%.1f", intensity));
            if (intensity < 2.5) {
                txtViewIntensity.setTextColor(Color.parseColor("#46cf9a"));
            } else if (intensity < 5) {
                txtViewIntensity.setTextColor(Color.parseColor("#adcb48"));
            } else if (intensity < 7.5) {
                txtViewIntensity.setTextColor(Color.parseColor("#dbb842"));
            } else {
                txtViewIntensity.setTextColor(Color.parseColor("#f44336"));
            }
        }

        private void setAverage(String average) {
            txtViewAverage.setText(average);
        }

        @Override
        protected ArrayList<Object> doInBackground(String... params) {
            Log.d("LoadReportSummeryTask", "doInBackground ");
            ArrayList<Object> summeryList = new ArrayList<>();
            summeryList.add(ReportController.getTotalRecords(from, to));
            summeryList.add(ReportController.getIntensity(from, to));
            summeryList.add(ReportController.getAverage(from, to));

            return summeryList;
        }

        @Override
        protected void onPostExecute(ArrayList<Object> summeryList) {
            Log.d("LoadReportSummeryTask", "onPostExecute ");

            if (summeryList.size() > 0) {
                setTotal((int) summeryList.get(0));
                cardViewReportSummery.setVisibility(View.VISIBLE);
            }

            if (summeryList.size() > 1) {
                setIntensity((double) summeryList.get(1));
            }

            if (summeryList.size() == 3) {
                setAverage((String) summeryList.get(2));
            }

        }
    }


    /**
     * Async task to load statistics
     */
    private class LoadReportStatisticsTask extends AsyncTask<String, Void, ReportViewData[]> {

        private View mView;
        private Timestamp from;
        private Timestamp to;

        public LoadReportStatisticsTask(View mView, Timestamp from, Timestamp to) {
            this.mView = mView;
            this.from = from;
            this.to = to;
        }

        private ReportViewData[] getReportViewData(ArrayList<ArrayList<String>> statisticsList) {
            ReportViewData[] reportViewData = new ReportViewData[statisticsList.size()];

            int triggers = 0;
            int symptoms = 1;
            int activities = 2;
            int location = 3;
            int bodyArea = 4;
            int medicine = 5;
            int effectiveMedicine = 6;
            int reliefs = 7;
            int effectiveReliefs = 8;
            //Triggers
            {
                ArrayList<String> topTriggers = statisticsList.get(triggers);
                ReportViewData triggerReportViewData = new ReportViewData("Top triggers");

                for (int i = 0; i < topTriggers.size(); i++) {
                    if (i == 0) {
                        triggerReportViewData.setContent_1(topTriggers.get(i));
                    } else if (i == 1) {
                        triggerReportViewData.setContent_2(topTriggers.get(i));
                    } else if (i == 2) {
                        triggerReportViewData.setContent_3(topTriggers.get(i));
                    }
                }
                reportViewData[triggers] = triggerReportViewData;
            }

            //Symptoms
            {
                ArrayList<String> topSymptoms = statisticsList.get(symptoms);
                ReportViewData symptomReportViewData = new ReportViewData("Top symptoms");

                for (int i = 0; i < topSymptoms.size(); i++) {
                    if (i == 0) {
                        symptomReportViewData.setContent_1(topSymptoms.get(i));
                    } else if (i == 1) {
                        symptomReportViewData.setContent_2(topSymptoms.get(i));
                    } else if (i == 2) {
                        symptomReportViewData.setContent_3(topSymptoms.get(i));
                    }
                }
                reportViewData[symptoms] = symptomReportViewData;
            }

            //Activities
            {
                ArrayList<String> topActivities = statisticsList.get(activities);
                ReportViewData activityReportViewData = new ReportViewData("Top activities");

                for (int i = 0; i < topActivities.size(); i++) {
                    if (i == 0) {
                        activityReportViewData.setContent_1(topActivities.get(i));
                    } else if (i == 1) {
                        activityReportViewData.setContent_2(topActivities.get(i));
                    } else if (i == 2) {
                        activityReportViewData.setContent_3(topActivities.get(i));
                    }
                }
                reportViewData[activities] = activityReportViewData;
            }

            //Location
            {
                ArrayList<String> topLocations = statisticsList.get(location);
                ReportViewData locationReportViewData = new ReportViewData("Top locations");

                for (int i = 0; i < topLocations.size(); i++) {
                    if (i == 0) {
                        locationReportViewData.setContent_1(topLocations.get(i));
                    } else if (i == 1) {
                        locationReportViewData.setContent_2(topLocations.get(i));
                    } else if (i == 2) {
                        locationReportViewData.setContent_3(topLocations.get(i));
                    }
                }
                reportViewData[location] = locationReportViewData;
            }

            //Pain in
            {
                ArrayList<String> topBodyAreas = statisticsList.get(bodyArea);
                ReportViewData bodyAreaReportViewData = new ReportViewData("Top pain areas");

                for (int i = 0; i < topBodyAreas.size(); i++) {
                    if (i == 0) {
                        bodyAreaReportViewData.setContent_1(topBodyAreas.get(i));
                    } else if (i == 1) {
                        bodyAreaReportViewData.setContent_2(topBodyAreas.get(i));
                    } else if (i == 2) {
                        bodyAreaReportViewData.setContent_3(topBodyAreas.get(i));
                    }
                }
                reportViewData[bodyArea] = bodyAreaReportViewData;
            }

            //Medicine
            {
                ArrayList<String> topMedicines = statisticsList.get(medicine);
                ReportViewData medicineReportViewData = new ReportViewData("Top medicine");

                for (int i = 0; i < topMedicines.size(); i++) {
                    if (i == 0) {
                        medicineReportViewData.setContent_1(topMedicines.get(i));
                    } else if (i == 1) {
                        medicineReportViewData.setContent_2(topMedicines.get(i));
                    } else if (i == 2) {
                        medicineReportViewData.setContent_3(topMedicines.get(i));
                    }
                }
                reportViewData[medicine] = medicineReportViewData;
            }

            //Effective medicine
            {
                ArrayList<String> topEffectiveMedicines = statisticsList.get(effectiveMedicine);
                ReportViewData effectiveMedicineReportViewData = new ReportViewData("Top effective medicine");

                for (int i = 0; i < topEffectiveMedicines.size(); i++) {
                    if (i == 0) {
                        effectiveMedicineReportViewData.setContent_1(topEffectiveMedicines.get(i));
                    } else if (i == 1) {
                        effectiveMedicineReportViewData.setContent_2(topEffectiveMedicines.get(i));
                    } else if (i == 2) {
                        effectiveMedicineReportViewData.setContent_3(topEffectiveMedicines.get(i));
                    }
                }
                reportViewData[effectiveMedicine] = effectiveMedicineReportViewData;
            }

            //Relief
            {
                ArrayList<String> topReliefs = statisticsList.get(reliefs);
                ReportViewData reliefReportViewData = new ReportViewData("Top reliefs");

                for (int i = 0; i < topReliefs.size(); i++) {
                    if (i == 0) {
                        reliefReportViewData.setContent_1(topReliefs.get(i));
                    } else if (i == 1) {
                        reliefReportViewData.setContent_2(topReliefs.get(i));
                    } else if (i == 2) {
                        reliefReportViewData.setContent_3(topReliefs.get(i));
                    }
                }
                reportViewData[reliefs] = reliefReportViewData;
            }

            //Effective Relief
            {
                ArrayList<String> topEffectiveReliefs = statisticsList.get(effectiveReliefs);
                ReportViewData effectiveReliefReportViewData = new ReportViewData("Top effective reliefs");

                for (int i = 0; i < topEffectiveReliefs.size(); i++) {
                    if (i == 0) {
                        effectiveReliefReportViewData.setContent_1(topEffectiveReliefs.get(i));
                    } else if (i == 1) {
                        effectiveReliefReportViewData.setContent_2(topEffectiveReliefs.get(i));
                    } else if (i == 2) {
                        effectiveReliefReportViewData.setContent_3(topEffectiveReliefs.get(i));
                    }
                }
                reportViewData[effectiveReliefs] = effectiveReliefReportViewData;
            }

            return reportViewData;
        }

        @Override
        protected ReportViewData[] doInBackground(String... params) {
            Log.d("LoadReportStatistics", "doInBackground ");
            ArrayList<ArrayList<String>> statisticsList = new ArrayList<>();

            //Triggers
            statisticsList.add(ReportController.getTopTriggers(from, to, 3));

            //Symptoms
            statisticsList.add(ReportController.getTopSymptoms(from, to, 3));

            //Activities
            statisticsList.add(ReportController.getTopActivities(from, to, 3));

            //Location
            statisticsList.add(ReportController.getTopLocations(from, to, 3));

            //Pain in
            statisticsList.add(ReportController.getTopBodyAreas(from, to, 3));

            //Medicine
            statisticsList.add(ReportController.getTopMedicines(from, to, 3));

            //Effective medicine
            statisticsList.add(ReportController.getTopEffectiveMedicines(from, to, 3));

            //Relief
            statisticsList.add(ReportController.getTopReliefs(from, to, 3));

            //Effective Relief
            statisticsList.add(ReportController.getTopEffectiveReliefs(from, to, 3));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return getReportViewData(statisticsList);
        }

        @Override
        protected void onPostExecute(ReportViewData[] reportViewData) {
            Log.d("LoadReportStatistics", "onPostExecute ");

            RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.report_recycler_view);
            recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());

            // 2. set layoutManger
            recyclerView.setLayoutManager(new LinearLayoutManager(ReportFragment.this.getActivity()));

            // 3. create an adapter
            ReportViewAdapter reportViewAdapter = new ReportViewAdapter(reportViewData);

            // 4. set adapter
            recyclerView.setAdapter(reportViewAdapter);

            // 5. set item animator to DefaultAnimator
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            if (nDialog != null) {
                nDialog.dismiss();
            }
        }
    }

}
