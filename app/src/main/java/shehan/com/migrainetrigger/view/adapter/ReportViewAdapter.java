package shehan.com.migrainetrigger.view.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.view.model.ReportViewData;

/**
 * Created by Shehan on 11/05/2016.
 */
public class ReportViewAdapter extends RecyclerView.Adapter<ReportViewAdapter.ViewHolder> {

    private ReportViewData[] reportViewDatas;

    public ReportViewAdapter(ReportViewData[] reportViewDatas) {
        this.reportViewDatas = reportViewDatas;
    }

    @Override
    public ReportViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        @SuppressLint("InflateParams") View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_report_section, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReportViewAdapter.ViewHolder holder, int position) {
        //Logic to show
        ReportViewData reportViewData = reportViewDatas[position];
        holder.txtViewTopic.setText(reportViewData.getTopic());

        String content_1 = reportViewData.getContent_1();
        if (content_1.equals("EMPTY")) {
            holder.txtViewContent_1.setText(R.string.record_no_enough_records);
            holder.layoutContent_2.setVisibility(View.GONE);
            holder.layoutContent_3.setVisibility(View.GONE);
            return;
        } else {
            holder.txtViewContent_1.setText(content_1);
        }

        String content_2 = reportViewData.getContent_2();
        if (content_2.equals("EMPTY")) {
            holder.layoutContent_2.setVisibility(View.GONE);
            holder.layoutContent_3.setVisibility(View.GONE);
            return;
        } else {
            holder.txtViewContent_2.setText(content_2);
        }

        String content_3 = reportViewData.getContent_3();
        if (content_3.equals("EMPTY")) {
            holder.layoutContent_3.setVisibility(View.GONE);
        } else {
            holder.txtViewContent_3.setText(content_3);
        }

    }

    @Override
    public int getItemCount() {
        return reportViewDatas.length;
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtViewTopic;
        public TextView txtViewContent_1;
        public TextView txtViewContent_2;
        public TextView txtViewContent_3;

        public RelativeLayout layoutContent_1;
        public RelativeLayout layoutContent_2;
        public RelativeLayout layoutContent_3;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewTopic = (TextView) itemLayoutView.findViewById(R.id.txt_report_section_topic);
            txtViewContent_1 = (TextView) itemLayoutView.findViewById(R.id.txt_report_section_content_1);
            txtViewContent_2 = (TextView) itemLayoutView.findViewById(R.id.txt_report_section_content_2);
            txtViewContent_3 = (TextView) itemLayoutView.findViewById(R.id.txt_report_section_content_3);

            layoutContent_1 = (RelativeLayout) itemLayoutView.findViewById(R.id.layout_report_section_content_1);
            layoutContent_2 = (RelativeLayout) itemLayoutView.findViewById(R.id.layout_report_section_content_2);
            layoutContent_3 = (RelativeLayout) itemLayoutView.findViewById(R.id.layout_report_section_content_3);
        }

    }
}
