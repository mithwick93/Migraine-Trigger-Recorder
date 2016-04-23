package shehan.com.migrainetrigger.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.view.model.FAQTopic;

/**
 * Created by Shehan on 4/15/2016.
 */
public class FAQTopicsViewAdapter
        extends RecyclerView.Adapter<FAQTopicsViewAdapter.ViewHolder> {

    private static FAQTopicsViewClickListener itemListener;
    private Context context;
    private FAQTopic[] faqTopics;

    public FAQTopicsViewAdapter(
            Context context,
            FAQTopicsViewClickListener itemListener,
            FAQTopic[] faqTopics) {

        this.context = context;
        FAQTopicsViewAdapter.itemListener = itemListener;
        this.faqTopics = faqTopics;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FAQTopicsViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        @SuppressLint("InflateParams") View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_faq_topic, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.txtViewDefinition.setText(faqTopics[position].getDefinition());
    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return faqTopics.length;
    }


    //Listener interface to sent recycler click to containing fragment or activity
    public interface FAQTopicsViewClickListener {
        void recyclerViewListClicked(int clickPosition);
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
            itemListener.recyclerViewListClicked(this.getLayoutPosition());

        }
    }
}