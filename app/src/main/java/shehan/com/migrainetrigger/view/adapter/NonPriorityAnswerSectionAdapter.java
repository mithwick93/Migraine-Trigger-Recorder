package shehan.com.migrainetrigger.view.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.utility.recyclerviewhelper.ItemTouchHelperAdapter;
import shehan.com.migrainetrigger.view.model.AnswerSectionViewData;

/**
 * Created by Shehan on 21/05/2016.
 */
public class NonPriorityAnswerSectionAdapter
        extends RecyclerView.Adapter<NonPriorityAnswerSectionAdapter.ViewHolder>
        implements ItemTouchHelperAdapter {

    private static List<AnswerSectionViewData> answerList;
    private static NonPriorityRowClickListener itemClickListener;
    private static NonPriorityDataItemRemoveListener itemRemoveListener;

    public NonPriorityAnswerSectionAdapter(NonPriorityRowClickListener itemClickListener, NonPriorityDataItemRemoveListener itemRemoveListener, List<AnswerSectionViewData> answerList) {
        NonPriorityAnswerSectionAdapter.itemClickListener = itemClickListener;
        NonPriorityAnswerSectionAdapter.itemRemoveListener = itemRemoveListener;
        NonPriorityAnswerSectionAdapter.answerList = answerList;
    }

    public void clearData() {
        answerList.clear(); //clear list
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        @SuppressLint("InflateParams") View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_answer_non_priority, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtViewAnswerName.setText(answerList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }

    @Override
    public void onItemDismiss(int position) {
        AnswerSectionViewData answerSectionViewData = answerList.remove(position);
        itemRemoveListener.onNonPriorityDataItemRemoved(answerSectionViewData);
        notifyItemRemoved(position);
        //notifyDataSetChanged();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    public void setData(List<AnswerSectionViewData> answerList) {
        NonPriorityAnswerSectionAdapter.answerList = answerList; //replace list
        notifyDataSetChanged();
    }

    //Listener interface to sent recycler click to containing fragment or activity
    public interface NonPriorityRowClickListener {
        void onNonPriorityAnswerRowClicked(AnswerSectionViewData answerSectionViewData);
    }

    public interface NonPriorityDataItemRemoveListener {
        void onNonPriorityDataItemRemoved(AnswerSectionViewData answerItem);
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtViewAnswerName;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            itemLayoutView.setOnClickListener(this);
            txtViewAnswerName = (TextView) itemLayoutView.findViewById(R.id.txt_view_answer_name);
        }

        @Override
        public void onClick(View v) {
            int clickPosition = this.getLayoutPosition();
            if (clickPosition > -1 && clickPosition < answerList.size()) {
                Log.i("AnswerViewAdapter", "Click item found at position: " + clickPosition);
                itemClickListener.onNonPriorityAnswerRowClicked(answerList.get(clickPosition));
            } else {
                Log.i("AnswerViewAdapter", "Click position error");
            }

        }
    }
}
