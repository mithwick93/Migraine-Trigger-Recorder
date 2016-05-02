package shehan.com.migrainetrigger.view.fragment.record.view;


import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.CalendarDayEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.controller.RecordController;
import shehan.com.migrainetrigger.data.model.Record;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewRecordCalenderFragment extends Fragment {

    private RecordCalenderListener mCallback;

    private CompactCalendarView calenderView;
    private TextView txtViewCalenderHeader;

    private SimpleDateFormat dateFormatForMonth;
    private Calendar currentCalender;

    private Map<Date, List<Integer>> recordsMap;

    public ViewRecordCalenderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_record_calender, container, false);
        initCalenderView(view);

        //loadEvents();
        new GetRecordCalenderListTask().execute();//load calender events
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (RecordCalenderListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement RecordCalenderListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (calenderView != null) {
//            new GetRecordCalenderListTask().execute();//load calender events
            calenderView.showCalendarWithAnimation();
        }
    }

    @Override
    public void onPause() {
        if (calenderView != null) {
            calenderView.hideCalendar();
        }
        super.onPause();
    }

    private void initCalenderView(View view) {
        calenderView = (CompactCalendarView) view.findViewById(R.id.calender_view);
        txtViewCalenderHeader = (TextView) view.findViewById(R.id.calender_header);
        calenderView.hideCalendar();

        //Map to keep records
        recordsMap = new HashMap<>();
        dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
        currentCalender = Calendar.getInstance(Locale.getDefault());

        calenderView.drawSmallIndicatorForEvents(true);
        calenderView.setDayColumnNames(new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"});
        calenderView.setUseThreeLetterAbbreviation(true);

        //set title on calendar scroll
        calenderView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Log.d("ViewRecordCalender", "inside onclick " + dateClicked);
                ArrayList<Integer> recordList = (ArrayList<Integer>) recordsMap.get(dateClicked);
                if (recordList != null) {
                    for (Integer integer : recordList) {
                        Log.d("ViewRecordCalender", "record Id " + integer);
                    }

                    //TODO : Show dialog to choose record on same day, for now show first record in list
                    mCallback.onRecordCalenderCallBack(recordList.get(0));

                } else {
                    Log.i("ViewRecordCalender", "record list not found ");
                }

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                txtViewCalenderHeader.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });

        txtViewCalenderHeader.setText(dateFormatForMonth.format(calenderView.getFirstDayOfCurrentMonth()));

    }

    //Parent activity must implement this interface to communicate
    public interface RecordCalenderListener {
        void onRecordCalenderCallBack(int recordId);
    }

    /**
     * Async task to initialize query db to get records
     */
    private class GetRecordCalenderListTask extends AsyncTask<String, Void, ArrayList<Record>> {

        private final String[] CALENDER_EVENT_COLOURS = {
                "#45d3d3",
                "#46cf9a",
                "#48cb66",
                "#8bc34a",
                "#adcb48",
                "#d3d345",
                "#dbb842",
                "#e4973e",
                "#ec703a",
                "#f44336",
                "#607D8B"
        };

        /**
         * Set all map key dates time to midnight
         *
         * @param calendar calender to change
         */
        private void setToMidnight(Calendar calendar) {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        }

        @Override
        protected ArrayList<Record> doInBackground(String... params) {
            Log.d("GetRecordCalenderList", " doInBackground - query records");

            return RecordController.getAllRecords();
        }

        @Override
        protected void onPostExecute(ArrayList<Record> recordArrayList) {
            Log.d("GetRecordCalenderList", " onPostExecute - update ui");

             /*
            1.Get record list
            2.Get record id and time stamp
            3. Add to hashMap,
            4.Add to calender event list
            5.Add to calender
             */
            List<CalendarDayEvent> calendarDayEvents = new ArrayList<>();
            for (int i = 0; i < recordArrayList.size(); i++) {
                Record record = recordArrayList.get(i);

                int recordId = record.getRecordId();
                long milliseconds = record.getStartTime().getTime();
                int intensityColor;
                switch (record.getIntensity()) {//Set event color
                    case 1:
                        intensityColor = Color.parseColor(CALENDER_EVENT_COLOURS[0]);
                        break;
                    case 2:
                        intensityColor = Color.parseColor(CALENDER_EVENT_COLOURS[1]);
                        break;
                    case 3:
                        intensityColor = Color.parseColor(CALENDER_EVENT_COLOURS[2]);
                        break;
                    case 4:
                        intensityColor = Color.parseColor(CALENDER_EVENT_COLOURS[3]);
                        break;
                    case 5:
                        intensityColor = Color.parseColor(CALENDER_EVENT_COLOURS[4]);
                        break;
                    case 6:
                        intensityColor = Color.parseColor(CALENDER_EVENT_COLOURS[5]);
                        break;
                    case 7:
                        intensityColor = Color.parseColor(CALENDER_EVENT_COLOURS[6]);
                        break;
                    case 8:
                        intensityColor = Color.parseColor(CALENDER_EVENT_COLOURS[7]);
                        break;
                    case 9:
                        intensityColor = Color.parseColor(CALENDER_EVENT_COLOURS[8]);
                        break;
                    case 10:
                        intensityColor = Color.parseColor(CALENDER_EVENT_COLOURS[9]);
                        break;
                    default:
                        Log.d("ViewRecordCalender", "intensityColor : " + record.getIntensity());
                        intensityColor = Color.parseColor(CALENDER_EVENT_COLOURS[10]);
                        break;
                }

                currentCalender.setTimeInMillis(milliseconds);
                setToMidnight(currentCalender);

                if (recordsMap.get(currentCalender.getTime()) != null) {//Already in list
                    Log.d("ViewRecordCalender", "record list found");
                    ArrayList<Integer> recordIdList = (ArrayList<Integer>) recordsMap.get(currentCalender.getTime());
                    recordIdList.add(recordId);
                } else {//Not added
                    Log.d("ViewRecordCalender", "record list not found,adding new list");
                    ArrayList<Integer> recordIdList = new ArrayList<>();
                    recordIdList.add(recordId);
                    recordsMap.put(currentCalender.getTime(), recordIdList);
                }

                CalendarDayEvent calendarDayEvent = new CalendarDayEvent(milliseconds, intensityColor);
                calendarDayEvents.add(calendarDayEvent);
            }

            calenderView.addEvents(calendarDayEvents);
        }
    }
}
