package shehan.com.migrainetrigger.view.fragment.filter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import shehan.com.migrainetrigger.R;

public class FilterDialogFragment extends DialogFragment implements View.OnClickListener {

    private static final String ARG_FILTERS = "param1";
    private static TextView filterDetailsTextView;
    private String appTheme;
    private ArrayList<ArrayList<String>> filterList;
    private FilterUpdateListener mCallback;
    private ArrayList<ArrayList<String>> selectedFilters;
    private int viewIndex;

    public FilterDialogFragment() {
        this.filterList = new ArrayList<>();
        this.selectedFilters = new ArrayList<>();
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (FilterUpdateListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FilterUpdateListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_filter_dialog, container);

        ViewGroup insertPoint = (ViewGroup) view.findViewById(R.id.filterDialogLayout);

        filterDetailsTextView = (TextView) view.findViewById(R.id.textView_currentFilterDetails);
        filterDetailsTextView.setText(R.string.record_filter_title);

        appTheme = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("pref_appTheme", "Light");

        updateFilterSelection();

        if (selectedFilters.size() != 7) {
            selectedFilters.clear();
            for (int itr = 0; itr < 7; itr++) {
                this.selectedFilters.add(new ArrayList<String>());
            }
        }

        this.viewIndex = 0;

        if (filterList.get(6).size() > 0) {
            insertPoint.addView(addTagTypeGroup(inflater, "Triggers", 6), this.viewIndex);
            this.viewIndex++;
        }

        if (filterList.get(5).size() > 0) {
            insertPoint.addView(addTagTypeGroup(inflater, "Symptoms", 5), this.viewIndex);
            this.viewIndex++;
        }

        if (filterList.get(1).size() > 0) {
            insertPoint.addView(addTagTypeGroup(inflater, "Activities", 1), this.viewIndex);
            this.viewIndex++;
        }

        if (filterList.get(2).size() > 0) {
            insertPoint.addView(addTagTypeGroup(inflater, "Locations", 2), this.viewIndex);
            this.viewIndex++;
        }

        if (filterList.get(0).size() > 0) {
            insertPoint.addView(addTagTypeGroup(inflater, "Pain areas", 0), this.viewIndex);
            this.viewIndex++;
        }

        if (filterList.get(3).size() > 0) {
            insertPoint.addView(addTagTypeGroup(inflater, "Medicines", 3), this.viewIndex);
            this.viewIndex++;
        }
        if (filterList.get(4).size() > 0) {
            insertPoint.addView(addTagTypeGroup(inflater, "Reliefs", 4), this.viewIndex);
            this.viewIndex++;
        }


        view.findViewById(R.id.button_dialog_cancel_filter_change).setOnClickListener(this);
        view.findViewById(R.id.button_dialog_filter).setOnClickListener(this);
        view.findViewById(R.id.button_dialog_clear_filter).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_dialog_cancel_filter_change:
                dismiss();
                break;
            case R.id.button_dialog_clear_filter:
                selectedFilters = null;
                mCallback.onUpdateFilter(null);
                dismiss();
                break;
            case R.id.button_dialog_filter:
                mCallback.onUpdateFilter(selectedFilters);
                dismiss();
                break;
            default:
        }
    }

    public void setFilters(ArrayList<ArrayList<String>> filterList) {
        this.filterList = filterList;
    }

    public void setPreviousSelection(ArrayList<ArrayList<String>> previousSelectList) {
        if (previousSelectList != null && previousSelectList.size() == 7) {
            this.selectedFilters = previousSelectList;
        } else {
            Log.e("FilterTagClickListener", "Trying to set null on incompatible list to selectedFilters");
        }

    }

    private View addTagTypeGroup(LayoutInflater inflater, String category, int tagTypeKey) {

        @SuppressLint("InflateParams") LinearLayout tagTypeGroup = (LinearLayout) inflater.inflate(R.layout.container_record_filter, null);
        tagTypeGroup.setId(tagTypeKey);
        if (this.viewIndex == 0) {
            tagTypeGroup.findViewById(R.id.viewDivider).setVisibility(View.GONE);
        }
        //new category
        LinearLayout tagTypeContainer = (LinearLayout) tagTypeGroup.findViewById(R.id.linearLayout_tagTypeContainer);
        TextView tagTypeLabel = (TextView) tagTypeGroup.findViewById(R.id.textView_tagTypeLabel);
        tagTypeLabel.setText(category);
        tagTypeContainer.setVisibility(View.GONE);
        tagTypeLabel.setOnClickListener(new CategoryClickListener());

        //Add filter items for category
        ArrayList<String> tmpLst = filterList.get(tagTypeKey);
        for (int itr = 0; itr < tmpLst.size(); itr++) {
            String tag = tmpLst.get(itr);
            @SuppressLint("InflateParams") LinearLayout tagCheckboxLayout = (LinearLayout) inflater.inflate(R.layout.item_record_filter_tag, null);
            CheckBox tagCheckBox = (CheckBox) tagCheckboxLayout.findViewById(R.id.checkBox_tagFilter);
            tagCheckBox.setText(tag);
            tagCheckBox.setId(itr);
            if (selectedFilters != null && selectedFilters.get(tagTypeKey).contains(tag)) {
                tagCheckBox.setChecked(true);
            }

            tagCheckBox.setOnClickListener(new FilterTagClickListener(tagTypeKey));
            tagTypeContainer.addView(tagCheckboxLayout);
        }
        return tagTypeGroup;
    }

    /**
     * Builder to add filter tags for each category
     *
     * @param strFilter  editing filter string
     * @param category   category of filter
     * @param arrayIndex index of selectedFilters arrayList to access
     * @return built filter string
     */
    private String prepareFilterString(String strFilter, String category, int arrayIndex) {
        ArrayList<String> lst = selectedFilters.get(arrayIndex);
        if (lst.size() > 0) {
            strFilter += "\n\t" + category + " :";
            for (String str : lst) {
                strFilter += " " + str + ",";
            }
            strFilter = strFilter.substring(0, strFilter.length() - 1);
        }
        return strFilter;
    }

    /**
     * Show summery of selected filter tags
     */
    private void updateFilterSelection() {

        String strFilter;

        if (selectedFilters.size() == 7) {
            strFilter = "Filters :";
        } else {
            filterDetailsTextView.setText(R.string.record_filter_title);
            return;
        }

        //Triggers 6
        strFilter = prepareFilterString(strFilter, "Triggers", 6);

        //Symptoms 5
        strFilter = prepareFilterString(strFilter, "Symptoms", 5);

        //Activities 1
        strFilter = prepareFilterString(strFilter, "Activities", 1);

        //Locations 2
        strFilter = prepareFilterString(strFilter, "Locations", 2);

        //Pain areas 0
        strFilter = prepareFilterString(strFilter, "Pain areas", 0);

        //Medicines 3
        strFilter = prepareFilterString(strFilter, "Medicines", 3);

        //Reliefs 4
        strFilter = prepareFilterString(strFilter, "Reliefs", 4);

        if (strFilter.equals("Filters :")) {
            filterDetailsTextView.setText(R.string.record_filter_title);
        } else {
            filterDetailsTextView.setText(strFilter);
        }
    }

    public interface FilterUpdateListener {
        void onUpdateFilter(ArrayList<ArrayList<String>> arrayList);
    }

    //    on group click
    private class CategoryClickListener implements View.OnClickListener {
        @SuppressWarnings("deprecation")
        public void onClick(View view) {

            if (view.isActivated()) {
                view.setActivated(false);
                if (appTheme.equals("Dark")) {
                    ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(null, null, FilterDialogFragment.this.getActivity().getResources().getDrawable(R.drawable.ic_action_arrow_down_inverse), null);
                } else {
                    ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(null, null, FilterDialogFragment.this.getActivity().getResources().getDrawable(R.drawable.ic_action_arrow_down), null);

                }
                ((View) view.getParent()).findViewById(R.id.linearLayout_tagTypeContainer).setVisibility(View.GONE);
                return;
            }
            view.setActivated(true);
            if (appTheme.equals("Dark")) {
                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(null, null, FilterDialogFragment.this.getActivity().getResources().getDrawable(R.drawable.ic_action_arrow_up_inverse), null);
            } else {
                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(null, null, FilterDialogFragment.this.getActivity().getResources().getDrawable(R.drawable.ic_action_arrow_up), null);
            }

            ((View) view.getParent()).findViewById(R.id.linearLayout_tagTypeContainer).setVisibility(View.VISIBLE);
        }
    }

    //on check box item clicked
    private class FilterTagClickListener implements View.OnClickListener {
        final int tagTypeKey;

        FilterTagClickListener(int i) {
            tagTypeKey = i;
        }

        public void onClick(View view) {
            CheckBox checkBox = (CheckBox) view;
            String filterTag = checkBox.getText().toString();

            Log.d("FilterTagClickListener", "Filter - " + filterTag);

            if (checkBox.isChecked()) {
                if (!(FilterDialogFragment.this.selectedFilters.get(tagTypeKey)).contains(filterTag)) {
                    (FilterDialogFragment.this.selectedFilters.get(tagTypeKey)).add(filterTag);
                }
            } else if (FilterDialogFragment.this.selectedFilters.get(tagTypeKey).contains(filterTag)) {
                (FilterDialogFragment.this.selectedFilters.get(tagTypeKey)).remove(filterTag);
            }
            FilterDialogFragment.this.updateFilterSelection();
        }
    }

}
