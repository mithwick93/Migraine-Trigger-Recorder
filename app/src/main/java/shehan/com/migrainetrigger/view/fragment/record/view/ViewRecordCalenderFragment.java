package shehan.com.migrainetrigger.view.fragment.record.view;


import android.content.Context;
import android.graphics.Color;
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

    private CompactCalendarView calender_view;
    private TextView txt_calender_header;

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
        loadEvents();
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
        if (calender_view != null) {
            calender_view.showCalendarWithAnimation();
        }
    }

    @Override
    public void onPause() {
        if (calender_view != null) {
            calender_view.hideCalendar();
        }
        super.onPause();
    }


    private void initCalenderView(View view) {
        calender_view = (CompactCalendarView) view.findViewById(R.id.calender_view);
        txt_calender_header = (TextView) view.findViewById(R.id.calender_header);
        calender_view.hideCalendar();

        recordsMap = new HashMap<>();
        dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
        currentCalender = Calendar.getInstance(Locale.getDefault());

//        calender_view.drawSmallIndicatorForEvents(true);
        calender_view.setDayColumnNames(new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"});
        calender_view.setUseThreeLetterAbbreviation(true);

        //set title on calendar scroll
        calender_view.setListener(new CompactCalendarView.CompactCalendarViewListener() {
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
                    Log.e("ViewRecordCalender", "record list not found ");
                }

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                txt_calender_header.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });

        txt_calender_header.setText(dateFormatForMonth.format(calender_view.getFirstDayOfCurrentMonth()));

    }


    private void loadEvents() {
            /*
            1.Get record list
            2.Get record id and time stamp
            3. Add to hashMap,
            4.Add to calender event list
            5.Add to calender
             */

        ArrayList<Record> recordArrayList = RecordController.getAllRecords();
        List<CalendarDayEvent> calendarDayEvents = new ArrayList<>();
        for (int i = 0; i < recordArrayList.size(); i++) {
            Record record = recordArrayList.get(i);

            int recordId = record.getRecordId();
            long milliseconds = record.getStartTime().getTime();
            int intensityColor;
            switch (record.getIntensity()) {
                case 1:
                    intensityColor = Color.parseColor("#45d3d3");
                    break;
                case 2:
                    intensityColor = Color.parseColor("#46cf9a");
                    break;
                case 3:
                    intensityColor = Color.parseColor("#48cb66");
                    break;
                case 4:
                    intensityColor = Color.parseColor("#8bc34a");
                    break;
                case 5:
                    intensityColor = Color.parseColor("#adcb48");
                    break;
                case 6:
                    intensityColor = Color.parseColor("#d3d345");
                    break;
                case 7:
                    intensityColor = Color.parseColor("#dbb842");
                    break;
                case 8:
                    intensityColor = Color.parseColor("#e4973e");
                    break;
                case 9:
                    intensityColor = Color.parseColor("#ec703a");
                    break;
                case 10:
                    intensityColor = Color.parseColor("#f44336");
                    break;
                default:
                    Log.d("ViewRecordCalender", "intensityColor : " + record.getIntensity());
                    intensityColor = Color.parseColor("#607D8B");
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

        calender_view.addEvents(calendarDayEvents);

    }

    /**
     * @param calendar calender to change
     */
    private void setToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }


    //Parent activity must implement this interface to communicate
    public interface RecordCalenderListener {
        void onRecordCalenderCallBack(int recordId);
    }


}
