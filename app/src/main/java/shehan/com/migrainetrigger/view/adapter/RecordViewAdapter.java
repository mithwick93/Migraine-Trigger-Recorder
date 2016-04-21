package shehan.com.migrainetrigger.view.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
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

    private RecordViewData[] recordViewData;

    public RecordViewAdapter(RecordViewData[] recordViewData) {
        this.recordViewData = recordViewData;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecordViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        @SuppressLint("InflateParams") View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_record_view, null);

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


    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtViewStartDate;
        public TextView txtViewDuration;
        public TextView imgIntensity;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewStartDate = (TextView) itemLayoutView.findViewById(R.id.txt_record_view_date);
            txtViewDuration = (TextView) itemLayoutView.findViewById(R.id.txt_record_view_duration);
            imgIntensity = (TextView) itemLayoutView.findViewById(R.id.txt_record_view_intensity);
        }
    }
}
