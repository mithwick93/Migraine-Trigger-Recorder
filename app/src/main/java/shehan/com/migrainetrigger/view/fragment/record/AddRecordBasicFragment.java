package shehan.com.migrainetrigger.view.fragment.record;


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

import java.util.Calendar;
import java.util.Date;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.data.model.Record;
import shehan.com.migrainetrigger.view.utility.viewUtilities;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddRecordBasicFragment extends Fragment {
    private Toast mToast;
    private AddRecordBasicListener mCallback;

    private EditText edit_txt_start_date;
    private EditText edit_txt_start_time;
    private EditText edit_txt_end_date;
    private EditText edit_txt_end_time;
    private TextView view_txt_intensity;
    private RelativeLayout view_layout_intensity;

    private CardView layout_weather;
    private TextView txt_weather_temp;
    private TextView txt_weather_humidity;
    private TextView txt_weather_pressure;

    private Record basicRecord;

    private Date startDate;
    private Date startTime;
    private Date endDate;
    private Date endTime;
    private int intensity;//Value 1-10

    private int mYear, mMonth, mDay, mHour, mMinute;

    public AddRecordBasicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_record_basic, container, false);


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

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        intensity = -1;

        initControls();

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
                    + " must implement OnTopicSelectedListener");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.new_record_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_confirm) {
            //Do whatever you want to do
            saveRecord();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveRecord() {
        new MaterialDialog.Builder(getContext())
                .title("Compete record")
                .content("Do you want to view record summery ?")
                .negativeText("No")
                .positiveText("Yes")
                .neutralText("Cancel")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        showToast("Record save - not implemented");
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        showToast("Record show summery - not implemented");
                        layout_weather.setVisibility(View.VISIBLE);
                    }
                })
                .show();

//        Log.d("test-db", "record start");
//
//        Record record= new RecordBuilder()
//                .setStartTime(new Timestamp(Calendar.getInstance().getTime().getTime()))
//                .setEndTime(new Timestamp(Calendar.getInstance().getTime().getTime()))
//                .setIntensity(5)
//                .createRecord();
//        RecordController.addNewRecord(record);
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
//        Log.d("test-db", "test finish");
    }

    @Override
    public String toString() {
        return "Basic";
    }


    private void initControls() {
        Log.d("AddRecordBasic-init", "initControls ");

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
                                mYear = year;
                                mMonth = monthOfYear;
                                mDay = dayOfMonth;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }


        });
        edit_txt_start_date.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                edit_txt_start_date.setText("");
                return true;
            }
        });

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
                                mHour = hourOfDay;
                                mMinute = minute;
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }

        });
        edit_txt_start_time.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                edit_txt_start_time.setText("");
                return true;
            }
        });

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
                                mYear = year;
                                mMonth = monthOfYear;
                                mDay = dayOfMonth;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }

        });
        edit_txt_end_date.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                edit_txt_end_date.setText("");
                return true;
            }
        });

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
                                mHour = hourOfDay;
                                mMinute = minute;
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }

        });
        edit_txt_end_time.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                edit_txt_end_time.setText("");
                return true;
            }
        });

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

    private void showToast(String message) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        mToast.show();
    }


    //Parent activity must implement this interface to communicate
    public interface AddRecordBasicListener {
        void onBasicRecordInteraction();
    }

}
