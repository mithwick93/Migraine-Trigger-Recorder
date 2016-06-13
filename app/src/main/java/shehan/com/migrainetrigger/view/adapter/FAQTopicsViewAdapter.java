package shehan.com.migrainetrigger.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.view.model.FAQViewData;

/**
 * Created by Shehan on 4/15/2016.
 */
public class FAQTopicsViewAdapter
        extends RecyclerView.Adapter<FAQTopicsViewAdapter.ViewHolder> {

    private static FAQTopicsRowClickListener itemListener;
    private FAQViewData[] faqViewDatas;

    public FAQTopicsViewAdapter(
            FAQTopicsRowClickListener itemListener,
            FAQViewData[] faqViewDatas) {

        FAQTopicsViewAdapter.itemListener = itemListener;
        this.faqViewDatas = faqViewDatas;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FAQTopicsViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        @SuppressLint("InflateParams") View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_faq_topic, null);

        // create ViewHolder

        return new ViewHolder(itemLayoutView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.txtViewDefinition.setText(faqViewDatas[position].getDefinition());
    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return faqViewDatas.length;
    }


    //Listener interface to sent recycler click to containing fragment or activity
    public interface FAQTopicsRowClickListener {
        void onRowClicked(int clickPosition);
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtViewDefinition;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            itemLayoutView.setOnClickListener(this);
            txtViewDefinition = (TextView) itemLayoutView.findViewById(R.id.txt_definition);
        }

        @Override
        public void onClick(View v) {
            itemListener.onRowClicked(this.getLayoutPosition());

        }
    }
}