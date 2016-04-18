package shehan.com.migrainetrigger.view.fragment.record.add;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.sql.Timestamp;
import java.util.Calendar;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.controller.RecordController;
import shehan.com.migrainetrigger.data.builders.RecordBuilder;
import shehan.com.migrainetrigger.data.model.Record;
import shehan.com.migrainetrigger.data.model.WeatherData;
import shehan.com.migrainetrigger.view.utility.viewUtilities;

import static shehan.com.migrainetrigger.view.utility.viewUtilities.getTimeStampDate;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddRecordBasicFragment extends Fragment {
    protected Toast mToast;
    private AddRecordBasicListener mCallback;

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

    //domain objects
    private Record basicRecord;

    protected WeatherData weatherData;

    protected Timestamp startTimeStamp;
    protected Timestamp endTimeStamp;

    protected int[] startDate;
    protected int[] startTime;
    protected int[] endDate;
    protected int[] endTime;

    protected int intensity;//Value 1-10

    protected int mYear, mMonth, mDay, mHour, mMinute;

    private boolean weatherDataLoaded;

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
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public String toString() {
        return "Basic";
    }


    /**
     * initiate basic controls
     * call this in sub classes onCreate
     *
     * @param view current view
     */
    protected void initBasicControls(View view) {
        Log.d("AddRecordBasic", "initBasicControls ");

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

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH)+1;
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        intensity = -1;
        weatherDataLoaded = false;

        edit_txt_start_time.setEnabled(false);

        edit_txt_end_time.setEnabled(false);

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

                                edit_txt_start_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                showToast("Long press to clear date");
                                mYear = startDate[0] = year;
                                mMonth = startDate[1] = monthOfYear+1;
                                mDay = startDate[2] = dayOfMonth;

                                edit_txt_start_time.setEnabled(true);

                            }
                        }, mYear, mMonth-1, mDay);
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

                                edit_txt_start_time.setText(viewUtilities.getFormattedTime(hourOfDay, minute));
                                showToast("Long press to clear time");
                                mHour = startTime[0] = hourOfDay;
                                mMinute = startTime[1] = minute;
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

                                edit_txt_end_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                showToast("Long press to clear date");
                                mYear = endDate[0] = year;
                                mMonth = endDate[1] = monthOfYear+1;
                                mDay = endDate[2] = dayOfMonth;

                                edit_txt_end_time.setEnabled(true);
                            }
                        }, mYear, mMonth-1, mDay);
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

                                edit_txt_end_time.setText(viewUtilities.getFormattedTime(hourOfDay, minute));
                                showToast("Long press to clear time");
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
                                return true; // allow selection
                            }
                        })
                        .negativeText(R.string.cancelButtonDialog)
                        .show();
            }
        };
        view_txt_intensity.setOnClickListener(intensityListener);
        view_layout_intensity.setOnClickListener(intensityListener);
    }

    protected void showToast(String message) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        mToast.show();
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
                view_txt_intensity.setHint("Click to set");
                break;
        }
    }

    private void chooseSaveOrSummery() {

        if (!weatherDataLoaded) {
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
                            saveRecord();
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
            //weatherdata shown already ,just save record
            saveRecord();
        }


//        Log.d("test-db", "test start");
//
//        Log.d("test-db", "before : "+String.valueOf(RecordController.getLastId()));
//        Record record= new RecordBuilder()
//                .setRecordId(RecordController.getLastId()+1)
//                .setStartTime(new Timestamp(2016,4,14,6,0,0,0))
//                .setEndTime(new Timestamp(2016,4,14,12,0,0,0))
//                .setIntensity(3)
//                .createRecord();
//        RecordController.addNewRecord(record,0);

//        Log.d("test-db", "Middle : "+String.valueOf(RecordController.getLastId()));
//
//        record= new RecordBuilder()
//                .setRecordId(RecordController.getLastId()+1)
//                .setStartTime(new Timestamp(Calendar.getInstance().getTime().getTime()))
//                .setEndTime(new Timestamp(Calendar.getInstance().getTime().getTime()))
//                .setIntensity(3)
//                .createRecord();
//        RecordController.addNewRecord(record,0);
//
//
//        Log.d("test-db", "LifeActivity Controller :" + String.valueOf(LifeActivityController.getAllActivities().size()));
//        Log.d("test-db", "BodyArea Controller :" + String.valueOf(BodyAreaController.getAllBodyAreas().size()));
//        Log.d("test-db", "Location Controller :" + String.valueOf(LocationController.getAllLocations().size()));
//        Log.d("test-db", "Medicine Controller :" + String.valueOf(MedicineController.getAllMedicines().size()));
//        Log.d("test-db", "Relief Controller :" + String.valueOf(ReliefController.getAllReliefs().size()));
//        Log.d("test-db", "Symptom Controller :" + String.valueOf(SymptomController.getAllSymptoms().size()));
//        Log.d("test-db", "Trigger Controller :" + String.valueOf(TriggerController.getAllTriggers().size()));
//
//
//        Log.d("test-db", "After : "+String.valueOf(RecordController.getLastId()));
//        Log.d("test-db", "test finish");
    }

    /**
     * Show weather data
     * use this in subclasses also
     */
    protected void showWeather() {
        Log.d("AddRecordBasic", "showWeather");
        // layout_weather.setVisibility(View.VISIBLE);
    }


    /**
     * save record,
     * In subclasses handle this separately
     */
    private void saveRecord() {
        Log.d("AddRecordBasic", "saveRecord");
        //validations
        //check start<end
        Timestamp startTimestamp = null;
        if (startDate[0] != -1) {

            if (startTime[0] != -1) {
                Log.d("saveRecord", " sYear: " + startDate[0] + " " + " sMonth: " + startDate[1] + " " + " sDay: " + startDate[2] + " " + " sHour: " + startTime[0] + " " + " sMinute: " + startTime[1]);
                //"dd/MM/yyyy HH:mm:ss"
                String tmpStr = String.valueOf(startDate[2]) + "/" + String.valueOf(startDate[1]) + "/" + String.valueOf(startDate[0]) + " "
                        + String.valueOf(startTime[0]) + ":" + String.valueOf(startTime[1]) + ":0";

                startTimestamp = getTimeStampDate(tmpStr);
                Log.d("saveRecord", " startTimestamp : " + startTimestamp.getTime());
            } else {
                Log.d("saveRecord", " sYear: " + startDate[0] + " " + " sMonth: " + startDate[1] + " " + " sDay: " + startDate[2]);
                String tmpStr = String.valueOf(startDate[2]) + "/" + String.valueOf(startDate[1]) + "/" + String.valueOf(startDate[0])+" 0:0:0";
                startTimestamp = getTimeStampDate(tmpStr);
                Log.d("saveRecord", " startTimestamp : " + startTimestamp.getTime());
            }
        } else {
            showMsg("Record must have start time");
            return;
        }

        Timestamp endTimestamp = null;
        if (endDate[0] != -1) {

            if (endTime[0] != -1) {
                Log.d("saveRecord", " eYear: " + endDate[0] + " " + " eMonth: " + endDate[1] + " " + " eDay: " + endDate[2] + " " + " eHour: " + endTime[0] + " " + " eMinute: " + endTime[1]);
                String tmpStr = String.valueOf(endDate[2]) + "/" + String.valueOf(endDate[1]) + "/" + String.valueOf(endDate[0]) + " "
                        + String.valueOf(endTime[0]) + ":" + String.valueOf(endTime[1]) + ":0";
                endTimestamp = getTimeStampDate(tmpStr);
                Log.d("saveRecord", " endTimestamp : " + endTimestamp.getTime());
            } else {
                Log.d("saveRecord", " eYear: " + endDate[0] + " " + " eMonth: " + endDate[1] + " " + " eDay: " + endDate[2]);
                String tmpStr = String.valueOf(endDate[2]) + "/" + String.valueOf(endDate[1]) + "/" + String.valueOf(endDate[0])+" 0:0:0";
                endTimestamp = getTimeStampDate(tmpStr);
                Log.d("saveRecord", " endTimestamp : " + endTimestamp.getTime());
            }
        }

        if ((endTimestamp != null && startTimestamp.before(endTimestamp)) || endTimestamp == null) {
            boolean result = RecordController.addNewRecord(getBasicRecordBuilder().createRecord(), 0);
            if (result) {
                showToast("Record was saved successfully");
                mCallback.onBasicRecordInteraction(0);
            } else {
                showToast("Record save failed");
            }
        } else {
            showMsg("Start time is greater than the end time");
        }
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
            recordBuilder = recordBuilder.setWeatherData(weatherData);
        }

        if (intensity > 0) {
            recordBuilder = recordBuilder.setIntensity(intensity);
        }

        if (startDate[0] != -1) {
            Timestamp startTimestamp;
            if (startTime[0] != -1) {
                String tmpStr = String.valueOf(startDate[2]) + "/" + String.valueOf(startDate[1]) + "/" + String.valueOf(startDate[0]) + " "
                        + String.valueOf(startTime[0]) + ":" + String.valueOf(startTime[1]) + ":0";

                startTimestamp = getTimeStampDate(tmpStr);
            } else {
                String tmpStr = String.valueOf(startDate[2]) + "/" + String.valueOf(startDate[1]) + "/" + String.valueOf(startDate[0])+" 0:0:0";

                startTimestamp = getTimeStampDate(tmpStr);
            }
            recordBuilder = recordBuilder.setStartTime(startTimestamp);
        }

        if (endDate[0] != -1) {
            Timestamp endTimestamp;
            if (endTime[0] != -1) {
                String tmpStr = String.valueOf(endDate[2]) + "/" + String.valueOf(endDate[1]) + "/" + String.valueOf(endDate[0]) + " "
                        + String.valueOf(endTime[0]) + ":" + String.valueOf(endTime[1]) + ":0";
                endTimestamp = getTimeStampDate(tmpStr);
            } else {
                String tmpStr = String.valueOf(endDate[2]) + "/" + String.valueOf(endDate[1]) + "/" + String.valueOf(endDate[0])+" 0:0:0";
                endTimestamp = getTimeStampDate(tmpStr);
            }
            recordBuilder = recordBuilder.setEndTime(endTimestamp);
        }

        return recordBuilder;
    }

    protected void showMsg(String msg) {
        new MaterialDialog.Builder(getContext())
                .content(msg)
                .negativeText(R.string.cancelButtonDialog)
                .show();
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

}
