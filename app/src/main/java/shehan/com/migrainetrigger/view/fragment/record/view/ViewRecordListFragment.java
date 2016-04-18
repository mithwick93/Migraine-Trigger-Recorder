package shehan.com.migrainetrigger.view.fragment.record.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.controller.RecordController;
import shehan.com.migrainetrigger.data.model.Record;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewRecordListFragment extends Fragment {
    private Toast mToast;
    private RecordListListener mCallback;

    public ViewRecordListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_record_list, container, false);

        TableLayout logsTableLayout = (TableLayout) view.findViewById(R.id.record_list_view_table);
        generateRecordTable(view, logsTableLayout, RecordController.getAllRecords());
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (RecordListListener) context;
            ;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement RecordListListener");
        }
    }

    private void generateRecordTable(View rootView,
                                     TableLayout recordsTableLayout,
                                     List<Record> recordList) {

        for (Record record : recordList) {
            TableRow tr = new TableRow(rootView.getContext());
            TextView lDateVal = new TextView(rootView.getContext());

            SimpleDateFormat sdf = new SimpleDateFormat(getActivity().getString(R.string.config_date_log_pattern));

            String date = " - ";
            String duration = " - ";
            String intensity = " - ";

            Timestamp startTime = record.getStartTime();
            Timestamp endTime = record.getEndTime();

            if (startTime != null) {
                date = sdf.format(startTime);

                if (endTime != null) {
                    long difference = endTime.getTime() - startTime.getTime();
                    long differenceInSeconds = difference / DateUtils.SECOND_IN_MILLIS;
                    duration = DateUtils.formatElapsedTime(differenceInSeconds);
                }
            }


            if (record.getIntensity() > 0) {
                intensity = String.valueOf(record.getIntensity());
            }

            TextView txtDate = new TextView(rootView.getContext());
            txtDate.setAllCaps(true);
            txtDate.setText(date + "            ");
            tr.addView(txtDate);

            TextView txtDuration = new TextView(rootView.getContext());
            txtDuration.setText("     "+duration);
            tr.addView(txtDuration);

            TextView txtIntensity = new TextView(rootView.getContext());
            txtIntensity.setText("                        "+intensity);
            tr.addView(txtIntensity);

            recordsTableLayout.addView(tr);
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
    public interface RecordListListener {
        void onRecordListCallBack();
    }


}
