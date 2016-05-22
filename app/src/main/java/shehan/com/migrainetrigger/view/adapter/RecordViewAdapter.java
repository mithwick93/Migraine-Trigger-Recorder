package shehan.com.migrainetrigger.view.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.view.model.RecordViewData;

/**
 * Created by Shehan on 4/20/2016.
 */
public class RecordViewAdapter extends RecyclerView.Adapter<RecordViewAdapter.ViewHolder> {

    private static RecordListViewRowClickListener itemListener;
    private static RecordViewData[] recordViewData;

    public RecordViewAdapter(RecordListViewRowClickListener itemListener, RecordViewData[] recordViewData) {
        RecordViewAdapter.recordViewData = recordViewData;
        RecordViewAdapter.itemListener = itemListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecordViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        @SuppressLint("InflateParams") View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_record_list, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData

        viewHolder.txtViewStartDate.setText(String.format("Start time : %s", recordViewData[position].getStartTime()));
        viewHolder.txtViewDuration.setText(String.format("Duration : %s", recordViewData[position].getDuration()));
        viewHolder.imgIntensity.setBackgroundResource(recordViewData[position].getImgUrl());
        if (recordViewData[position].getImgUrl() == 0) {
            viewHolder.imgIntensity.setText("-");
        } else {
            viewHolder.imgIntensity.setText("");
        }

    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return recordViewData.length;
    }

    public void setData(RecordViewData[] recordViewData) {
        RecordViewAdapter.recordViewData = recordViewData;
        notifyDataSetChanged();
    }


    //Listener interface to sent recycler click to containing fragment or activity
    public interface RecordListViewRowClickListener {
        void onRecordListRowClicked(int recordId);
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView imgIntensity;
        public TextView txtViewDuration;
        public TextView txtViewStartDate;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            itemLayoutView.setOnClickListener(this);
            txtViewStartDate = (TextView) itemLayoutView.findViewById(R.id.txt_record_view_date);
            txtViewDuration = (TextView) itemLayoutView.findViewById(R.id.txt_record_view_duration);
            imgIntensity = (TextView) itemLayoutView.findViewById(R.id.txt_record_view_intensity);
        }

        @Override
        public void onClick(View v) {
            int clickPosition = this.getLayoutPosition();
            int recordId = -1;
            if (clickPosition > -1 && clickPosition < recordViewData.length) {
                Log.i("RecordViewAdapter", "Clicked record : " + recordViewData[clickPosition].getRecordId());
                recordId = recordViewData[clickPosition].getRecordId();
            } else {
                Log.i("RecordViewAdapter", "Click position error");
            }
            itemListener.onRecordListRowClicked(recordId);

        }
    }
}
