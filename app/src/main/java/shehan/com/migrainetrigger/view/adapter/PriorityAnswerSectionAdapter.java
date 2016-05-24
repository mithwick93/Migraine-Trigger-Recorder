package shehan.com.migrainetrigger.view.adapter;

import android.annotation.SuppressLint;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.utility.recyclerviewhelper.ItemTouchHelperAdapter;
import shehan.com.migrainetrigger.utility.recyclerviewhelper.ItemTouchHelperViewHolder;
import shehan.com.migrainetrigger.utility.recyclerviewhelper.OnStartDragListener;
import shehan.com.migrainetrigger.view.model.AnswerSectionViewData;

/**
 * Created by Shehan on 21/05/2016.
 */
public class PriorityAnswerSectionAdapter extends RecyclerView.Adapter<PriorityAnswerSectionAdapter.ViewHolder>
        implements ItemTouchHelperAdapter {

    private static List<AnswerSectionViewData> answerList;
    private static PriorityAnswersRowClickListener itemClickListener;
    private static PriorityDataItemRemoveListener itemRemoveListener;
    private static DataOrderChangeListener itemReorderListener;
    private final OnStartDragListener onStartDragListener;

    public PriorityAnswerSectionAdapter(
            PriorityAnswersRowClickListener itemClickListener,
            DataOrderChangeListener itemReorderListener,
            PriorityDataItemRemoveListener itemRemoveListener,
            OnStartDragListener dragStartListener,
            List<AnswerSectionViewData> answerList) {

        this.onStartDragListener = dragStartListener;
        PriorityAnswerSectionAdapter.itemClickListener = itemClickListener;
        PriorityAnswerSectionAdapter.itemReorderListener = itemReorderListener;
        PriorityAnswerSectionAdapter.itemRemoveListener = itemRemoveListener;
        PriorityAnswerSectionAdapter.answerList = answerList;
    }

    public void clearData() {
        answerList.clear(); //clear list
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_answer_priority, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textViewName.setText(answerList.get(position).getName());
        holder.textViewPriority.setText(String.format(Locale.getDefault(), "Priority : %d", answerList.get(position).getPriority()));

        // Start a drag whenever the handle view it touched
        holder.handleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    onStartDragListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }

    @Override
    public void onItemDismiss(int position) {
        AnswerSectionViewData answerSectionViewData = answerList.remove(position);
        itemRemoveListener.onPriorityDataItemRemoved(answerSectionViewData);
        notifyItemRemoved(position);
        //notifyDataSetChanged();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(answerList, fromPosition, toPosition);
        itemReorderListener.onDataReordered(answerList);
        notifyItemMoved(fromPosition, toPosition);
        // notifyDataSetChanged();
        return true;
    }

    public void setData(List<AnswerSectionViewData> answerList) {
        PriorityAnswerSectionAdapter.answerList = answerList; //replace list
        notifyDataSetChanged();
    }

    //Listener interface to sent recycler click to containing fragment or activity
    public interface PriorityAnswersRowClickListener {
        void onPriorityAnswerRawClicked(AnswerSectionViewData answerSectionViewData);
    }

    public interface DataOrderChangeListener {
        void onDataReordered(List<AnswerSectionViewData> answerList);
    }

    public interface PriorityDataItemRemoveListener {
        void onPriorityDataItemRemoved(AnswerSectionViewData answerItem);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder, View.OnClickListener {

        public final CardView cardViewAnswerRaw;
        public final ImageView handleView;
        public final TextView textViewName;
        public final TextView textViewPriority;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textViewName = (TextView) itemView.findViewById(R.id.txt_view_answer_name);
            textViewPriority = (TextView) itemView.findViewById(R.id.txt_view_priority);
            handleView = (ImageView) itemView.findViewById(R.id.img_view_drag);
            cardViewAnswerRaw = (CardView) itemView.findViewById(R.id.card_view_answer_raw);
        }

        @Override
        public void onClick(View v) {
            int clickPosition = this.getLayoutPosition();
            if (clickPosition > -1 && clickPosition < answerList.size()) {
                Log.i("AnswerViewAdapter", "Click item found at position: " + clickPosition);
                itemClickListener.onPriorityAnswerRawClicked(answerList.get(clickPosition));
            } else {
                Log.i("AnswerViewAdapter", "Click position error");
            }
        }

        @Override
        public void onItemClear() {
        }

        @Override
        public void onItemSelected() {
        }
    }
}
