package shehan.com.migrainetrigger.view.fragment.record.add;


import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.johnhiott.darkskyandroidlib.RequestBuilder;
import com.johnhiott.darkskyandroidlib.models.Request;
import com.johnhiott.darkskyandroidlib.models.WeatherResponse;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.controller.RecordController;
import shehan.com.migrainetrigger.data.builders.RecordBuilder;
import shehan.com.migrainetrigger.data.builders.WeatherDataBuilder;
import shehan.com.migrainetrigger.data.model.WeatherData;
import shehan.com.migrainetrigger.utility.AppUtil;
import shehan.com.migrainetrigger.utility.GeoLocationService;
import shehan.com.migrainetrigger.utility.InternetService;

import static shehan.com.migrainetrigger.utility.AppUtil.getStringWeatherDate;
import static shehan.com.migrainetrigger.utility.AppUtil.getTimeStampDate;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddRecordBasicFragment extends Fragment implements GeoLocationService.GeoLocationListener {

    protected Toast mToast;
    //basic
    protected EditText edit_txt_start_date;
    protected EditText edit_txt_start_time;
    protected EditText edit_txt_end_date;
    protected EditText edit_txt_end_time;
    protected TextView view_txt_intensity;
    protected RelativeLayout view_layout_intensity;
    protected CardView layout_weather;
    protected TextView txt_weather_temp;
    protected TextView txt_weather_humidity;
    protected TextView txt_weather_pressure;
    protected WeatherData weatherData;
    protected Timestamp startTimeStamp;
    protected Timestamp endTimeStamp;
    protected int[] startDate;
    protected int[] startTime;
    protected int[] endDate;
    protected int[] endTime;
    protected int intensity;//Value 1-10
    protected int mYear, mMonth, mDay, mHour, mMinute;
    protected boolean weatherDataLoaded;
    private AddRecordBasicListener mCallback;
    //location
    private GeoLocationService geoLocationService;

    public AddRecordBasicFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_record_basic, container, false);

        initBasicControls(view);

        setHasOptionsMenu(true);

        Log.d("AddRecordBasic-onCreate", "variables initialized, onCreate complete");
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (AddRecordBasicListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement AddRecordBasicListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.new_record_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //override this in sub classes
        int id = item.getItemId();
        if (id == R.id.action_confirm) {
            chooseSaveOrSummery();
            return true;
        } else if (id == R.id.action_refresh) {
            showWeather();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (geoLocationService != null) {
            geoLocationService.connect();
            Log.d("AddRecordBasicFragment", "geoLocationService.connect");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (geoLocationService != null) {
            geoLocationService.connect();
            Log.d("AddRecordBasicFragment", "geoLocationService.connect");
        }
    }

    @Override
    public void onPause() {
        if (geoLocationService != null) {
            geoLocationService.disconnect();
            Log.d("AddRecordBasicFragment", "geoLocationService.disconnect");
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        if (geoLocationService != null) {
            geoLocationService.disconnect();
            Log.d("AddRecordBasicFragment", "geoLocationService.disconnect");
        }
        super.onStop();
    }

    @Override
    public String toString() {
        return "Basic";
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case GeoLocationService.PERMISSION_ACCESS_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // All good!
                } else {
                    showToast(getContext(), "Need your location");
                }
                break;
        }
    }

    /**
     * initiate basic controls
     * call this in sub classes onCreate
     *
     * @param view current view
     */
    protected void initBasicControls(View view) {
        Log.d("AddRecordBasic", "initBasicControls ");

        //only call this method once
        if (edit_txt_start_date != null) {
            return;
        }
        edit_txt_start_date = (EditText) view.findViewById(R.id.txt_record_start_date);
        edit_txt_start_time = (EditText) view.findViewById(R.id.txt_record_start_time);
        edit_txt_end_date = (EditText) view.findViewById(R.id.txt_record_end_date);
        edit_txt_end_time = (EditText) view.findViewById(R.id.txt_record_end_time);
        view_txt_intensity = (TextView) view.findViewById(R.id.txt_record_intensity);
        view_layout_intensity = (RelativeLayout) view.findViewById(R.id.layout_intensity);

        layout_weather = (CardView) view.findViewById(R.id.linear_weather_layout);
        txt_weather_temp = (TextView) view.findViewById(R.id.txt_weather_temp);
        txt_weather_humidity = (TextView) view.findViewById(R.id.txt_weather_humidity);
        txt_weather_pressure = (TextView) view.findViewById(R.id.txt_weather_pressure);

        startDate = new int[3];
        startDate[0] = -1;

        startTime = new int[2];
        startTime[0] = -1;

        endDate = new int[3];
        endDate[0] = -1;

        endTime = new int[2];
        endTime[0] = -1;

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        intensity = -1;
        weatherDataLoaded = false;

        edit_txt_start_time.setEnabled(false);

        edit_txt_end_time.setEnabled(false);

        layout_weather.setVisibility(View.GONE);

//--------------------------------------------------
        //start date
        edit_txt_start_date.setCursorVisible(false);
        edit_txt_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                edit_txt_start_date.setText(String.format(Locale.getDefault(), "%d-%d-%d", dayOfMonth, monthOfYear + 1, year));
                                showToast(getContext(), "Long press to clear date");
                                mYear = startDate[0] = year;
                                mMonth = startDate[1] = monthOfYear + 1;
                                mDay = startDate[2] = dayOfMonth;

                                edit_txt_start_time.setEnabled(true);

                                weatherData = null;
                                weatherDataLoaded = false;
                                layout_weather.setVisibility(View.GONE);


                            }
                        }, mYear, mMonth - 1, mDay);
                datePickerDialog.show();
            }


        });
        edit_txt_start_date.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                edit_txt_start_date.setText("");
                startDate = new int[3];
                startDate[0] = -1;

                edit_txt_start_time.setText("");
                edit_txt_start_time.setEnabled(false);
                startTime = new int[2];
                startTime[0] = -1;

                weatherData = null;
                weatherDataLoaded = false;
                layout_weather.setVisibility(View.GONE);

                return true;
            }
        });
//--------------------------------------------------
        //start time
        edit_txt_start_time.setCursorVisible(false);
        edit_txt_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                edit_txt_start_time.setText(AppUtil.getFormattedTime(hourOfDay, minute));
                                showToast(getContext(), "Long press to clear time");
                                mHour = startTime[0] = hourOfDay;
                                mMinute = startTime[1] = minute;

                                weatherData = null;
                                weatherDataLoaded = false;
                                layout_weather.setVisibility(View.GONE);

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }

        });
        edit_txt_start_time.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                edit_txt_start_time.setText("");
                startTime = new int[2];
                startTime[0] = -1;

                weatherData = null;
                weatherDataLoaded = false;
                layout_weather.setVisibility(View.GONE);

                return true;
            }
        });

//--------------------------------------------------
        //End date
        edit_txt_end_date.setCursorVisible(false);
        edit_txt_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                edit_txt_end_date.setText(String.format(Locale.getDefault(), "%d-%d-%d", dayOfMonth, monthOfYear + 1, year));
                                showToast(getContext(), "Long press to clear date");
                                mYear = endDate[0] = year;
                                mMonth = endDate[1] = monthOfYear + 1;
                                mDay = endDate[2] = dayOfMonth;

                                edit_txt_end_time.setEnabled(true);
                            }
                        }, mYear, mMonth - 1, mDay);
                datePickerDialog.show();
            }

        });
        edit_txt_end_date.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                edit_txt_end_date.setText("");
                endDate = new int[3];
                endDate[0] = -1;

                edit_txt_end_time.setText("");
                edit_txt_end_time.setEnabled(false);
                endTime = new int[2];
                endTime[0] = -1;

                return true;
            }
        });
//--------------------------------------------------
        //End time
        edit_txt_end_time.setCursorVisible(false);
        edit_txt_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                edit_txt_end_time.setText(AppUtil.getFormattedTime(hourOfDay, minute));
                                showToast(getContext(), "Long press to clear time");
                                mHour = endTime[0] = hourOfDay;
                                mMinute = endTime[1] = minute;
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }

        });
        edit_txt_end_time.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                edit_txt_end_time.setText("");
                endTime = new int[2];
                endTime[0] = -1;
                return true;
            }
        });
//--------------------------------------------------
        //intensity
        view_txt_intensity.setCursorVisible(false);
        View.OnClickListener intensityListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getContext())
                        .title(R.string.migraineIntensityLevelDialog)
                        .items(R.array.migraineIntensityLevel)
                        .itemsCallbackSingleChoice(intensity - 1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                intensity = which + 1;
                                setIntensityIcon(intensity);
                                showToast(getContext(), "Long press to clear");
                                return true; // allow selection
                            }
                        })
                        .negativeText(R.string.cancelButtonDialog)
                        .show();
            }
        };
        View.OnLongClickListener intensityLongListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                intensity = -1;
                setIntensityIcon(intensity);
                return true;
            }
        };

        view_txt_intensity.setOnClickListener(intensityListener);
        view_layout_intensity.setOnClickListener(intensityListener);

        view_txt_intensity.setOnLongClickListener(intensityLongListener);
        view_layout_intensity.setOnLongClickListener(intensityLongListener);
    }

    /**
     * Get basic data of record
     * Does not check constraints
     *
     * @return record builder with basic data saved
     */
    protected RecordBuilder getBasicRecordBuilder() {
        Log.d("AddRecordBasic", "getBasicRecordBuilder");
        RecordBuilder recordBuilder = new RecordBuilder().setRecordId(RecordController.getLastId() + 1);

        if (weatherData != null) {
            Log.d("AddRecordBasic", "getBasicRecordBuilder - weatherData");
            recordBuilder = recordBuilder.setWeatherData(weatherData);
        }

        if (intensity > 0) {
            Log.d("AddRecordBasic", "getBasicRecordBuilder - intensity ");
            recordBuilder = recordBuilder.setIntensity(intensity);
        }

        if (startDate[0] != -1) {
            Log.d("AddRecordBasic", "getBasicRecordBuilder - startDate");
            Timestamp startTimestamp;
            if (startTime[0] != -1) {
                String tmpStr = String.valueOf(startDate[2]) + "/" + String.valueOf(startDate[1]) + "/" + String.valueOf(startDate[0]) + " "
                        + String.valueOf(startTime[0]) + ":" + String.valueOf(startTime[1]) + ":0";

                startTimestamp = getTimeStampDate(tmpStr);
            } else {
                String tmpStr = String.valueOf(startDate[2]) + "/" + String.valueOf(startDate[1]) + "/" + String.valueOf(startDate[0]) + " 0:0:0";

                startTimestamp = getTimeStampDate(tmpStr);
            }
            recordBuilder = recordBuilder.setStartTime(startTimestamp);
        }

        if (endDate[0] != -1) {
            Log.d("AddRecordBasic", "getBasicRecordBuilder - endDate");
            Timestamp endTimestamp;
            if (endTime[0] != -1) {
                String tmpStr = String.valueOf(endDate[2]) + "/" + String.valueOf(endDate[1]) + "/" + String.valueOf(endDate[0]) + " "
                        + String.valueOf(endTime[0]) + ":" + String.valueOf(endTime[1]) + ":0";
                endTimestamp = getTimeStampDate(tmpStr);
            } else {
                String tmpStr = String.valueOf(endDate[2]) + "/" + String.valueOf(endDate[1]) + "/" + String.valueOf(endDate[0]) + " 0:0:0";
                endTimestamp = getTimeStampDate(tmpStr);
            }
            recordBuilder = recordBuilder.setEndTime(endTimestamp);
        }

        return recordBuilder;
    }

    /**
     * Show weather data
     * use this in subclasses also
     */
    protected void showWeather() {
        Log.d("AddRecordBasic", "showWeather");
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
        }
        try {
            if (geoLocationService != null) {
                geoLocationService.disconnect();
            }

            geoLocationService = new GeoLocationService(getActivity(), this);
            Log.d("GeoLocationService", "GeoLocationService - created googleApiClient");
        } catch (Exception e) {
            if (geoLocationService != null) {
                geoLocationService.disconnect();
            }
            Log.e("AddRecordBasic", "exception :");
            showToast(getContext(), "Something went wrong");
            e.printStackTrace();
        }


    }

    public void onLocationReceived(Location location) {
        Log.d("AddRecordBasic", "onLocationReceived ");
        Timestamp startTimestamp;
        if (startDate[0] != -1) {
            if (startTime[0] != -1) {
                String tmpStr = String.valueOf(startDate[2]) + "/" + String.valueOf(startDate[1]) + "/" + String.valueOf(startDate[0]) + " "
                        + String.valueOf(startTime[0]) + ":" + String.valueOf(startTime[1]) + ":0";
                startTimestamp = getTimeStampDate(tmpStr);
            } else {
                String tmpStr = String.valueOf(startDate[2]) + "/" + String.valueOf(startDate[1]) + "/" + String.valueOf(startDate[0]) + " 0:0:0";
                startTimestamp = getTimeStampDate(tmpStr);
            }
        } else {
            //showToast(getContext(), "Showing current weather data");
            startTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        }
        if (location != null) {
            new GetWeatherTask(location.getLatitude(), location.getLongitude(), startTimestamp).execute();

        } else {
            Log.d("AddRecordBasic", "fallback to default coordinates ");
            showToast(getContext(), "Using default coordinates");
            new GetWeatherTask(6.6839861, 79.9275146, startTimestamp).execute();
        }
    }

    /**
     * Show a toast
     *
     * @param context context to show toast
     * @param message string msg
     */
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

    private void requestLocationPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {

            showToast(getContext(), "Location data allows to get better weather predictions. Please allow in App Settings for accurate functionality.");

        }
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GeoLocationService.PERMISSION_ACCESS_FINE_LOCATION);
    }

    /**
     * Change intensity indicator
     *
     * @param intensity value of intensity Range 1:10, on clear -1
     */
    private void setIntensityIcon(int intensity) {

        view_txt_intensity.setHint("");
        switch (intensity) {
            case 1:
                view_txt_intensity.setBackgroundResource(R.drawable.num_1);
                break;
            case 2:
                view_txt_intensity.setBackgroundResource(R.drawable.num_2);
                break;
            case 3:
                view_txt_intensity.setBackgroundResource(R.drawable.num_3);
                break;
            case 4:
                view_txt_intensity.setBackgroundResource(R.drawable.num_4);
                break;
            case 5:
                view_txt_intensity.setBackgroundResource(R.drawable.num_5);
                break;
            case 6:
                view_txt_intensity.setBackgroundResource(R.drawable.num_6);
                break;
            case 7:
                view_txt_intensity.setBackgroundResource(R.drawable.num_7);
                break;
            case 8:
                view_txt_intensity.setBackgroundResource(R.drawable.num_8);
                break;
            case 9:
                view_txt_intensity.setBackgroundResource(R.drawable.num_9);
                break;
            case 10:
                view_txt_intensity.setBackgroundResource(R.drawable.num_10);
                break;
            default:
                view_txt_intensity.setBackgroundResource(0);
                view_txt_intensity.setHint(R.string.record_txt_intensity_hint);
                break;
        }
    }

    private void chooseSaveOrSummery() {

        if (!weatherDataLoaded || weatherData == null) {
            new MaterialDialog.Builder(getContext())
                    .title("Compete record")
                    .content("Do you want to view weather information or save record now?")
                    .negativeText("Save record")
                    .positiveText("Show weather")
                    .neutralText("Cancel")
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            //Save without  summery
                            saveBasicRecord();
                        }
                    })
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            //show summery
                            showWeather();
                        }
                    })
                    .show();
        } else {
            //weatherData shown already ,just save record
            saveBasicRecord();
        }

    }

    /**
     * save record,
     * In subclasses handle this separately
     */
    private void saveBasicRecord() {
        Log.d("AddRecordBasic", "saveRecord");
        //validations
        //check start<end
        Timestamp startTimestamp = null;

        //Check for start date
        if (startDate[0] != -1) {

            if (startTime[0] != -1) {
                String tmpStr = String.valueOf(startDate[2]) + "/" + String.valueOf(startDate[1]) + "/" + String.valueOf(startDate[0]) + " "
                        + String.valueOf(startTime[0]) + ":" + String.valueOf(startTime[1]) + ":0";

                startTimestamp = getTimeStampDate(tmpStr);
            } else {
                String tmpStr = String.valueOf(startDate[2]) + "/" + String.valueOf(startDate[1]) + "/" + String.valueOf(startDate[0]) + " 0:0:0";
                startTimestamp = getTimeStampDate(tmpStr);
            }
        } else {
            showMsg(getContext(), "Record must have start time");
            return;
        }

        Calendar c = Calendar.getInstance();
        if (startTimestamp.after(c.getTime())) {
            showMsg(getContext(), "Start Date is past current time");
            return;
        }

        //Check for end date
        Timestamp endTimestamp = null;
        if (endDate[0] != -1) {

            if (endTime[0] != -1) {
                String tmpStr = String.valueOf(endDate[2]) + "/" + String.valueOf(endDate[1]) + "/" + String.valueOf(endDate[0]) + " "
                        + String.valueOf(endTime[0]) + ":" + String.valueOf(endTime[1]) + ":0";
                endTimestamp = getTimeStampDate(tmpStr);
            } else {
                String tmpStr = String.valueOf(endDate[2]) + "/" + String.valueOf(endDate[1]) + "/" + String.valueOf(endDate[0]) + " 0:0:0";
                endTimestamp = getTimeStampDate(tmpStr);
            }

        }

        if (endTimestamp != null) {
            if (endTimestamp.after(c.getTime())) {
                showMsg(getContext(), "End Date is past current time");
                return;
            }
        }

        //validate times
        if ((endTimestamp != null && startTimestamp.before(endTimestamp)) || endTimestamp == null) {

            boolean result = RecordController.addNewRecord(getBasicRecordBuilder().createRecord(), 0);//Level 0
            if (result) {
                showToast(getContext(), "Record was saved successfully");
                mCallback.onBasicRecordInteraction(0);
            } else {
                showToast(getContext(), "Record save failed");
            }
        } else {
            showMsg(getContext(), "Start time is greater than the end time");
        }
    }

    /**
     * Parent activity must implement this interface to communicate
     */
    public interface AddRecordBasicListener {
        /**
         * Parent activity must implement this method to communicate
         *
         * @param request inform parent about request (0 - dismiss activity)
         */
        void onBasicRecordInteraction(int request);
    }

    private class GetWeatherTask extends AsyncTask<String, Void, WeatherData> implements InternetService {
        double latitude;
        double longitude;
        Timestamp timestamp;

        WeatherData tmpWeatherData;

        private ProgressDialog nDialog;

        private volatile boolean cancelTask;
        private volatile boolean networkProblem;

        GetWeatherTask(double latitude, double longitude, Timestamp timestamp) {
            super();
            Log.d("GetWeatherTask", "constructor");
            this.latitude = latitude;
            this.longitude = longitude;
            this.timestamp = timestamp;
            this.cancelTask = false;
            this.networkProblem = false;

        }

        @Override
        public void getWeatherData(double wLatitude, double wLongitude, Timestamp wTimestamp) {
            RequestBuilder weather = new RequestBuilder();
            Request request = new Request();
            request.setLat(String.valueOf(wLatitude));
            request.setLng(String.valueOf(wLongitude));
            request.setTime(getStringWeatherDate(wTimestamp));
            request.setUnits(Request.Units.SI);
            request.setLanguage(Request.Language.PIG_LATIN);
            request.addExcludeBlock(Request.Block.CURRENTLY);
            request.removeExcludeBlock(Request.Block.CURRENTLY);

            weather.getWeather(request, new Callback<WeatherResponse>()

                    {
                        @Override
                        public void success(WeatherResponse weatherResponse, Response response) {
                            try {
                                tmpWeatherData = new WeatherDataBuilder()
                                        .setHumidity(Double.valueOf(weatherResponse.getCurrently().getHumidity()) * 100)
                                        .setPressure(Double.valueOf(weatherResponse.getCurrently().getPressure()) / 10)
                                        .setTemperature(weatherResponse.getCurrently().getTemperature())
                                        .createWeatherData();//Pressure given as hecto pascal
                            } catch (Exception e) {
                                Log.e("getWeatherData", "fatal error");
                                e.printStackTrace();
                                //cancel(true);
                                cancelTask = true;
                                networkProblem = true;
                            }
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            Log.d("getWeatherData", "Error while calling: " + retrofitError.getUrl());
                            Log.d("getWeatherData", retrofitError.toString());
                            // cancel(true);
                            cancelTask = true;
                            networkProblem = true;
                        }
                    }
            );
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(getActivity()); //Here I get an error: The constructor ProgressDialog(PFragment) is undefined
            nDialog.setMessage("Loading weather data...");
            nDialog.setTitle("Processing");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    // actually could set running = false; right here, but I'll
                    // stick to contract.
                    cancelTask = true;
                }
            });
            nDialog.show();
        }

        @Override
        protected WeatherData doInBackground(String... params) {

            //get weather from Internet service interface
            getWeatherData(latitude, longitude, timestamp);

            //Loop till weather is fetched
            while (tmpWeatherData == null) {
                if (cancelTask) {
                    Log.d("doInBackground", "Task canceled");
                    break;
                }
            }

            return tmpWeatherData;
        }

        @Override
        protected void onPostExecute(WeatherData wd) {
            nDialog.dismiss();
            if (cancelTask) {

                weatherData = null;
                weatherDataLoaded = false;
                layout_weather.setVisibility(View.GONE);

                if (networkProblem) {
                    showMsg(getContext(), "There was an error connecting to the weather service. Please check network connectivity and try again.", "Could not get weather data");
                } else {
                    Toast.makeText(getContext(), "Task canceled", Toast.LENGTH_SHORT).show();
                }


                return;
            }
            if (tmpWeatherData != null) {

                txt_weather_temp.setText(String.format(Locale.getDefault(), "%.2f °C", tmpWeatherData.getTemperature()));
                txt_weather_humidity.setText(String.format(Locale.getDefault(), "%.2f %%", tmpWeatherData.getHumidity()));
                txt_weather_pressure.setText(String.format(Locale.getDefault(), "%.2f KPa", tmpWeatherData.getPressure()));

                weatherData = tmpWeatherData;
                weatherDataLoaded = true;

                layout_weather.setVisibility(View.VISIBLE);

            } else {
                Log.d("showWeather", "null weather data");
                showMsg(getContext(), "network service disconnected");
            }
        }
    }

}
