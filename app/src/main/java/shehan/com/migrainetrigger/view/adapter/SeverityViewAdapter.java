package shehan.com.migrainetrigger.view.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.view.model.SeverityViewData;

/**
 * Created by Shehan on 4/15/2016.SeverityViewAdapter
 */
public class SeverityViewAdapter
        extends RecyclerView.Adapter<SeverityViewAdapter.ViewHolder> {


    private SeverityViewData[] severityViewData;

    public SeverityViewAdapter(SeverityViewData[] severityViewData) {
        this.severityViewData = severityViewData;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SeverityViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        @SuppressLint("InflateParams") View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_severity, null);

        // create ViewHolder

        return new ViewHolder(itemLayoutView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData

        viewHolder.txtViewTitle.setText(severityViewData[position].getTitle());
        viewHolder.txtViewDesc.setText(severityViewData[position].getDescription());
        viewHolder.imgViewIcon.setImageResource(severityViewData[position].getImageUrl());

    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return severityViewData.length;
    }


    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgViewIcon;
        public TextView txtViewDesc;
        public TextView txtViewTitle;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.item_title);
            txtViewDesc = (TextView) itemLayoutView.findViewById(R.id.item_desc);
            imgViewIcon = (ImageView) itemLayoutView.findViewById(R.id.item_icon);
        }
    }

}