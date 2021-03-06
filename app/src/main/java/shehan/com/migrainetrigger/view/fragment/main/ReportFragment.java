package shehan.com.migrainetrigger.view.fragment.main;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.controller.RecordController;
import shehan.com.migrainetrigger.controller.ReportController;
import shehan.com.migrainetrigger.data.model.WeatherData;
import shehan.com.migrainetrigger.utility.AppUtil;
import shehan.com.migrainetrigger.utility.csv.CSVWriter;
import shehan.com.migrainetrigger.view.adapter.ReportViewAdapter;
import shehan.com.migrainetrigger.view.model.ReportViewData;

import static shehan.com.migrainetrigger.utility.AppUtil.getTimeStampDate;
import static shehan.com.migrainetrigger.utility.AppUtil.showToast;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReportFragmentListener} interface
 * to handle interaction events.
 */
public class ReportFragment extends Fragment {

    public static final int PERMISSION_WRITE_EXTERNAL_STORAGE_BACKUP = 10;

    private CardView cardViewReportSummery;
    private CardView cardViewWeatherAvg;
    private int[] fromDate;
    private ReportFragmentListener mCallback;
    private View mView;
    private int mYear, mMonth, mDay;
    private ProgressDialog nDialog;
    private int[] toDate;
    private TextView txtViewAverage;
    private TextView txtViewFrom;
    private TextView txtViewHumidity;
    private TextView txtViewIntensity;
    private TextView txtViewPressure;
    private TextView txtViewTemp;
    private TextView txtViewTo;
    private TextView txtViewTotal;

    public ReportFragment() {
        // Required empty public constructor
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_WRITE_EXTERNAL_STORAGE_BACKUP:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    refreshSummery(true);
                } else {
                    // Permission Denied
                    AppUtil.showToast(getActivity(), "SD card access Denied");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ReportFragmentListener) {
            mCallback = (ReportFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ReportFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        initReport(view);
        loadRecordData();

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
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
            refreshSummery(false);
            return true;
        } else if (id == R.id.action_send_email) {
            refreshSummery(true);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        cardViewWeatherAvg = (CardView) view.findViewById(R.id.card_weather);

        txtViewTemp = (TextView) view.findViewById(R.id.txt_weather_temp);
        txtViewHumidity = (TextView) view.findViewById(R.id.txt_weather_humidity);
        txtViewPressure = (TextView) view.findViewById(R.id.txt_weather_pressure);
        TextView txtViewWeather = (TextView) view.findViewById(R.id.lbl_weather);
        txtViewWeather.setText(R.string.weather_title);

        cardViewReportSummery.setVisibility(View.GONE);
        cardViewWeatherAvg.setVisibility(View.GONE);

        fromDate = new int[3];
        toDate = new int[3];

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);


        txtViewFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFrom();
            }
        });

        txtViewTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTo();
            }
        });

        view.findViewById(R.id.arrow_from).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFrom();
            }
        });

        view.findViewById(R.id.arrow_to).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTo();
            }
        });

    }

    private boolean isStoragePermissionGranted(final int request) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v("ReportFragment", "Permission is granted");
                return true;
            } else {
                Log.v("ReportFragment", "Permission is revoked");
                if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    new MaterialDialog.Builder(getContext())
                            .content("This app wants to access your SD card.")
                            .positiveText("Agree")
                            .negativeText("Disagree")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {

                                @TargetApi(Build.VERSION_CODES.M)
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, request);
                                }
                            })
                            .show();
                    return false;
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, request);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("ReportFragment", "Permission is granted");
            return true;
        }
    }

    private void loadRecordData() {
        //Load all data
        new LoadReportDatesTask().execute();
    }

    private void refreshSummery(boolean sendEmail) {

        Timestamp fromTimestamp;

        //Check for start date

        String tmpFrom = String.valueOf(fromDate[0]) + "-" + String.valueOf(fromDate[1]) + "-" + String.valueOf(fromDate[2]) + " 00:00:00";
        fromTimestamp = getTimeStampDate(tmpFrom);

        if (fromTimestamp == null) {
            Log.e("ReportFragment", "fromTimestamp == null");
            return;
        }

        Calendar c = Calendar.getInstance();
        if (fromTimestamp.after(c.getTime())) {
            AppUtil.showMsg(getContext(), "Start Date is past current time", "Validation error");
            return;
        }

        //Check for end date
        Timestamp toTimestamp;

        String tmpTo = String.valueOf(toDate[0]) + "-" + String.valueOf(toDate[1]) + "-" + String.valueOf(toDate[2]) + " 23:59:59";
        toTimestamp = getTimeStampDate(tmpTo);

        //validate times
        if ((toTimestamp != null && fromTimestamp.before(toTimestamp)) || toTimestamp == null) {

            try {
                nDialog = new ProgressDialog(getActivity()); //Here I get an error: The constructor ProgressDialog(PFragment) is undefined
                nDialog.setMessage("Refreshing report...");
                nDialog.setTitle("Processing");
                nDialog.setIndeterminate(false);
                nDialog.setCancelable(false);
                nDialog.show();
            } catch (Exception ignored) {
            }


            new LoadReportSummeryTask(fromTimestamp, toTimestamp).execute();
            new LoadReportStatisticsTask(mView, fromTimestamp, toTimestamp, sendEmail).execute();
        } else {
            AppUtil.showMsg(getContext(), "Start time is greater than the end time", "Validation error");
        }

    }

    private void setFrom() {
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

    private void setTo() {
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
    public interface ReportFragmentListener {
        void onReportFragmentRequest(int request);
    }

    /**
     * Async task to load data
     */
    private class LoadReportDatesTask extends AsyncTask<String, Void, Timestamp> {


        @Override
        protected Timestamp doInBackground(String... params) {
            Log.d("LoadReportDatesTask", "doInBackground ");
            return RecordController.getFirstRecordStartTimestamp();
        }

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
        protected void onPostExecute(Timestamp timestamp) {
            Log.d("LoadReportDatesTask", "onPostExecute ");

            setFromDate(timestamp);
            setToDate();

            refreshSummery(false);
        }
    }

    /**
     * Async task to load statistics
     */
    private class LoadReportStatisticsTask extends AsyncTask<String, Void, ReportViewData[]> {
        private Timestamp from;
        private View mView;
        private boolean sendEmail;
        private Timestamp to;

        public LoadReportStatisticsTask(View mView, Timestamp from, Timestamp to, boolean sendEmail) {
            this.mView = mView;
            this.from = from;
            this.to = to;
            this.sendEmail = sendEmail;
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
                ReportViewData triggerReportViewData = new ReportViewData("Most common Triggers");

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
                ReportViewData symptomReportViewData = new ReportViewData("Most common symptoms");

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
                ReportViewData activityReportViewData = new ReportViewData("Top activities you missed");

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
                ReportViewData locationReportViewData = new ReportViewData("Most likely locations");

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
                ReportViewData medicineReportViewData = new ReportViewData("Most taken medicines");

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
                ReportViewData effectiveMedicineReportViewData = new ReportViewData("Most effective medicines");

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
                ReportViewData reliefReportViewData = new ReportViewData("Most taken reliefs");

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
                ReportViewData effectiveReliefReportViewData = new ReportViewData("Most effective reliefs");

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

            if (sendEmail) {
                try {
                    if (Integer.valueOf(txtViewTotal.getText().toString()) != 0) {
                        if (isStoragePermissionGranted(PERMISSION_WRITE_EXTERNAL_STORAGE_BACKUP)) {
                            new SendEmailTask(from, to).execute();
                        }
                    }
                } catch (Exception ignore) {
                }
            }
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

        private void setAverage(String average) {
            txtViewAverage.setText(average);
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

        private void setTotal(int total) {
            txtViewTotal.setText(String.valueOf(total).trim());
            if (total <= 3) {
                txtViewTotal.setTextColor(Color.parseColor("#46cf9a"));
            } else if (total <= 7) {
                txtViewTotal.setTextColor(Color.parseColor("#adcb48"));
            } else if (total <= 10) {
                txtViewTotal.setTextColor(Color.parseColor("#dbb842"));
            } else {
                txtViewTotal.setTextColor(Color.parseColor("#f44336"));
            }
            if (total == 0) {
                showToast(ReportFragment.this.getContext(), "No records found in current range");
            }
        }

        private void setWhetherAvg(@NotNull WeatherData whetherAvg) {
            txtViewTemp.setText(String.format(Locale.getDefault(), "%.2f °C", whetherAvg.getTemperature()));
            txtViewHumidity.setText(String.format(Locale.getDefault(), "%.2f %%", whetherAvg.getHumidity()));
            txtViewPressure.setText(String.format(Locale.getDefault(), "%.2f KPa", whetherAvg.getPressure()));
            cardViewWeatherAvg.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Object> doInBackground(String... params) {
            Log.d("LoadReportSummeryTask", "doInBackground ");
            ArrayList<Object> summeryList = new ArrayList<>();
            summeryList.add(ReportController.getTotalRecords(from, to));
            summeryList.add(ReportController.getIntensity(from, to));
            summeryList.add(ReportController.getAverage(from, to));
            summeryList.add(ReportController.getWeatherDataAverage(from, to));

            return summeryList;
        }

        @Override
        protected void onPostExecute(ArrayList<Object> summeryList) {
            Log.d("LoadReportSummeryTask", "onPostExecute");

            if (summeryList.size() > 0) {
                setTotal((int) summeryList.get(0));
                cardViewReportSummery.setVisibility(View.VISIBLE);
            }

            if (summeryList.size() > 1) {
                setIntensity((double) summeryList.get(1));
            }

            if (summeryList.size() > 2) {
                setAverage((String) summeryList.get(2));
            }

            cardViewWeatherAvg.setVisibility(View.GONE);
            if (summeryList.size() > 3) {
                Object o = summeryList.get(3);
                if (o != null) {
                    setWhetherAvg((WeatherData) o);
                }

            }

        }
    }

    /**
     * Async task to create temporary db on external and open email
     */
    private class SendEmailTask extends AsyncTask<String, Void, File> {

        private Timestamp from;
        private ProgressDialog nDialog;
        private Timestamp to;

        public SendEmailTask(Timestamp from, Timestamp to) {
            this.from = from;
            this.to = to;
        }

        /* Checks if external storage is available for read and write */
        public boolean isExternalStorageWritable() {
            String state = Environment.getExternalStorageState();
            return Environment.MEDIA_MOUNTED.equals(state);
        }

        private ArrayList<String[]> arrayListToString(ArrayList<ArrayList<String>> statisticsList, ArrayList<String[]> stringStatistics, int index, String title) {

            ArrayList<String> lst = statisticsList.get(index);
            if (lst.size() > 0) {
                stringStatistics.add(new String[]{title});
                for (String str : lst) {
                    stringStatistics.add(new String[]{"", str});
                }
            }
            return stringStatistics;
        }

        /**
         * get human friendly date string
         *
         * @param str raw date string
         * @return friendly date string
         */
        private String getFriendlyStringDate(String str) {
            String result = str;
            if (str != null && !str.equals("") && !str.equals("-") && !str.isEmpty()) {
                try {
                    Timestamp timestamp = AppUtil.getTimeStampDate(str);
                    result = AppUtil.getFriendlyStringDate(timestamp);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return result;
        }

        /**
         * get report statistics
         *
         * @return ArrayList String
         */
        private ArrayList<ArrayList<String>> getStatistics() {
            Log.d("SendEmailTask", "doInBackground ");
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

            return statisticsList;
        }

        /**
         * Get report summery
         *
         * @return ArrayList<Object>
         */
        private ArrayList<Object> getSummery() {
            Log.d("SendEmailTask", "getSummery");
            ArrayList<Object> summeryList = new ArrayList<>();
            summeryList.add(ReportController.getTotalRecords(from, to));
            summeryList.add(ReportController.getIntensity(from, to));
            summeryList.add(ReportController.getAverage(from, to));
            summeryList.add(ReportController.getWeatherDataAverage(from, to));

            return summeryList;
        }

        /**
         * Calculate duration of migraine record
         *
         * @param from start time
         * @param to   end time
         * @return duration
         */
        private String getTimeDuration(String from, String to) {
            String duration = "N/A";

            if (from != null && to != null && !from.equals("") && !to.equals("") && !from.isEmpty() && !to.isEmpty()) {
                try {
                    Timestamp startTime = AppUtil.getTimeStampDate(from);
                    Timestamp endTime = AppUtil.getTimeStampDate(to);

                    if (startTime != null) {
                        if (endTime != null) {
                            long difference = endTime.getTime() - startTime.getTime();
                            long differenceInSeconds = difference / DateUtils.SECOND_IN_MILLIS;
                            duration = AppUtil.getFriendlyDuration(differenceInSeconds);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            return duration;
        }

        /**
         * get CSV compatible statistics
         *
         * @return ArrayList String
         */
        private ArrayList<String[]> getWritableStatistics() {
            ArrayList<ArrayList<String>> statisticsList = getStatistics();
            ArrayList<String[]> stringStatistics = new ArrayList<>();

            if (statisticsList.size() > 0) {

                //Triggers
                arrayListToString(statisticsList, stringStatistics, 0, "Top triggers");

                //Symptoms
                arrayListToString(statisticsList, stringStatistics, 1, "Top symptoms");

                //Activities
                arrayListToString(statisticsList, stringStatistics, 2, "Top activities");

                //Location
                arrayListToString(statisticsList, stringStatistics, 3, "Top locations");

                //Pain in
                arrayListToString(statisticsList, stringStatistics, 4, "Top pain areas");

                //Medicine
                arrayListToString(statisticsList, stringStatistics, 5, "Top medicines");

                //Effective medicine
                arrayListToString(statisticsList, stringStatistics, 6, "Top effective medicine");

                //Relief
                arrayListToString(statisticsList, stringStatistics, 7, "Top relief methods");

                //Effective Relief
                arrayListToString(statisticsList, stringStatistics, 8, "Top effective relief methods");


            }
            return stringStatistics;
        }

        /**
         * get CSV compatible summery
         *
         * @return ArrayList String
         */
        private ArrayList<String[]> getWritableSummery() {

            ArrayList<Object> summeryList = getSummery();
            ArrayList<String[]> stringSummery = new ArrayList<>();

            if (summeryList.size() > 0) {//Total
                stringSummery.add(new String[]{"Total migraines", String.valueOf((int) summeryList.get(0))});
            }

            if (summeryList.size() > 1) {//Intensity
                stringSummery.add(new String[]{"Average intensity", String.valueOf((double) summeryList.get(1))});
            }

            if (summeryList.size() > 2) {//Average
                stringSummery.add(new String[]{"Average duration", String.valueOf(summeryList.get(2))});
            }

            if (summeryList.size() > 3) {//Weather
                Object o = summeryList.get(3);
                if (o != null) {
                    WeatherData weatherData = (WeatherData) o;
                    stringSummery.add(new String[]{"Average Weather"});
                    stringSummery.add(new String[]{"", "Temperature", String.format(Locale.getDefault(), "%.2f °C", weatherData.getTemperature())});
                    stringSummery.add(new String[]{"", "Humidity", String.format(Locale.getDefault(), "%.2f %%", weatherData.getHumidity())});
                    stringSummery.add(new String[]{"", "Pressure", String.format(Locale.getDefault(), "%.2f KPa", weatherData.getPressure())});

                }

            }
            return stringSummery;
        }

        /**
         * Create copy of records to csv file
         */
        @Nullable
        private File performCSVCopy() {
            try {

                ArrayList<String[]> tmpLst = RecordController.getAllRecordsOrderByDateRAW();
                ArrayList<String[]> records = new ArrayList<>();

                if (tmpLst.size() < 1) {
                    throw new Exception("No records found to send");
                } else {

                    for (String[] strArray : tmpLst) {
                        records.add(new String[]{
                                strArray[0],
                                getFriendlyStringDate(strArray[1]),
                                getFriendlyStringDate(strArray[2]),
                                getTimeDuration(strArray[1], strArray[2]),
                                strArray[3]});//Add duration
                    }
                }

                if (!isExternalStorageWritable()) {
                    throw new Exception("Cannot access sd card");
                }

                File sd = Environment.getExternalStorageDirectory();
                if (sd.canWrite()) {

                    sd = new File(Environment.getExternalStorageDirectory() + "/tmp");
                    //Create app directory if not exists
                    boolean result = false;
                    if (!sd.exists()) {
                        result = sd.mkdir();
                    }

                    if (sd.exists() || result) {

                        String csvName = "MigraineTrigger.csv"; // From SD directory.
                        File csvFile = new File(sd, csvName);

                        final boolean createResult = csvFile.createNewFile();

                        CSVWriter csvWrite = new CSVWriter(new FileWriter(csvFile));

                        csvWrite.writeNext(new String[]{"Migraine Trigger Report"});
                        csvWrite.writeNext(new String[]{""});

                        csvWrite.writeNext(new String[]{"From", AppUtil.getStringDate(from).split(" ")[0]});
                        csvWrite.writeNext(new String[]{"To", AppUtil.getStringDate(to).split(" ")[0]});

                        //Record summery
                        {
                            ArrayList<String[]> stringSummery = getWritableStatistics();
                            if (stringSummery.size() > 0) {
                                csvWrite.writeNext(new String[]{""});
                                csvWrite.writeNext(new String[]{"Report summery"});
                                csvWrite.writeNext(new String[]{""});
                                for (String[] strArray : stringSummery) {
                                    csvWrite.writeNext(strArray);
                                }
                            }
                        }

                        //Record Statistics
                        {
                            ArrayList<String[]> stringStatistics = getWritableSummery();
                            if (stringStatistics.size() > 0) {
                                csvWrite.writeNext(new String[]{""});
                                csvWrite.writeNext(new String[]{"Report statistics"});
                                csvWrite.writeNext(new String[]{""});
                                for (String[] strArray : stringStatistics) {
                                    csvWrite.writeNext(strArray);
                                }
                            }
                        }

                        csvWrite.writeNext(new String[]{""});

                        csvWrite.writeNext(new String[]{"All records"});

                        csvWrite.writeNext(new String[]{""});

                        csvWrite.writeNext(new String[]{"Record No", "Start", "End", "Duration", "Intensity"});

                        for (String[] strArray : records) {
                            csvWrite.writeNext(strArray);
                        }

                        csvWrite.close();

                        if (csvFile.exists()) {
                            return csvFile;
                        }

                        throw new Exception("Tmp file does not exist");
                    } else {
                        throw new Exception("Cannot create app folder");
                    }

                } else {
                    throw new Exception("Cannot access sdcard");
                }

            } catch (FileNotFoundException e) {
                Log.e("SettingsFragment", "File Not Found exception");
                e.printStackTrace();
                return null;

            } catch (Exception e) {
                Log.e("SettingsFragment", "Create temp exception");
                e.printStackTrace();
                return null;
            }
        }


        @Override
        protected File doInBackground(String... params) {
            Log.d("SendEmailTask", "doInBackground");

            File tmp = performCSVCopy();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return tmp;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(getActivity());
            nDialog.setMessage("Preparing records ...");
            nDialog.setTitle("Processing");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(false);
            nDialog.show();
        }

        @Override
        protected void onPostExecute(File tmp) {
            Log.d("SendEmailTask", " onPostExecute - show email intent");
            if (nDialog != null) {
                nDialog.dismiss();
            }
            if (tmp != null) {

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy h:mm a", Locale.getDefault());
                Date date = new Date();
                String currentTime = dateFormat.format(date);//get current time

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Migraine Trigger Report");
                intent.putExtra(Intent.EXTRA_TEXT, String.format("Report date : %s", currentTime));
                intent.setType("application/octet-stream");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(tmp));
                startActivity(Intent.createChooser(intent, "Send via e-mail"));

            } else {
                Log.e("SettingsFragment", "null temp file");
                AppUtil.showToast(ReportFragment.this.getActivity(), "Something went wrong");
            }
        }
    }

}
