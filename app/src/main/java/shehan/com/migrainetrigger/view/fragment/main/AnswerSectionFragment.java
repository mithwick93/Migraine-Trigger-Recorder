package shehan.com.migrainetrigger.view.fragment.main;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.controller.BodyAreaController;
import shehan.com.migrainetrigger.controller.LifeActivityController;
import shehan.com.migrainetrigger.controller.LocationController;
import shehan.com.migrainetrigger.controller.MedicineController;
import shehan.com.migrainetrigger.controller.ReliefController;
import shehan.com.migrainetrigger.controller.SymptomController;
import shehan.com.migrainetrigger.controller.TriggerController;
import shehan.com.migrainetrigger.data.dao.DBTransactionHandler;
import shehan.com.migrainetrigger.data.model.BodyArea;
import shehan.com.migrainetrigger.data.model.LifeActivity;
import shehan.com.migrainetrigger.data.model.Location;
import shehan.com.migrainetrigger.data.model.Medicine;
import shehan.com.migrainetrigger.data.model.Relief;
import shehan.com.migrainetrigger.data.model.Symptom;
import shehan.com.migrainetrigger.data.model.Trigger;
import shehan.com.migrainetrigger.utility.AppUtil;
import shehan.com.migrainetrigger.utility.recyclerviewhelper.OnStartDragListener;
import shehan.com.migrainetrigger.utility.recyclerviewhelper.SimpleItemTouchHelperCallback;
import shehan.com.migrainetrigger.view.adapter.NonPriorityAnswerSectionAdapter;
import shehan.com.migrainetrigger.view.adapter.PriorityAnswerSectionAdapter;
import shehan.com.migrainetrigger.view.model.AnswerSectionViewData;


public class AnswerSectionFragment
        extends Fragment
        implements OnStartDragListener,
        NonPriorityAnswerSectionAdapter.NonPriorityRowClickListener,
        PriorityAnswerSectionAdapter.PriorityAnswersRowClickListener,
        PriorityAnswerSectionAdapter.DataOrderChangeListener,
        PriorityAnswerSectionAdapter.PriorityDataItemRemoveListener,
        NonPriorityAnswerSectionAdapter.NonPriorityDataItemRemoveListener {

    private static final String ARG_ANSWER_SECTION = "answerSection";
    private static volatile List<AnswerSectionViewData> removedNonPriorityItemList;
    private static volatile List<AnswerSectionViewData> removedPriorityItemList;
    private static volatile List<AnswerSectionViewData> reorderedList;
    private volatile String answerSection;
    private ItemTouchHelper mItemTouchHelper;
    private volatile Menu mMenu;
    private volatile View mView;
    private volatile boolean reOrder;


    public AnswerSectionFragment() {
        // Required empty public constructor
    }

    /**
     * Static method to get new instance of this fragment
     *
     * @param answerSection answer section
     * @return AnswerSectionFragment
     */
    public static AnswerSectionFragment newInstance(String answerSection) {

        AnswerSectionFragment fragment = new AnswerSectionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ANSWER_SECTION, answerSection);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            answerSection = getArguments().getString(ARG_ANSWER_SECTION);
        } else {
            Log.e("AnswerSectionFragment", "Invalid answer Section");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_answer_section, container, false);

        //AppUtil.showToast(getContext(), answerSection);
        setHasOptionsMenu(true);
        init();
        return mView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //Show add new answer on toolbar
        mMenu = menu;
        inflater.inflate(R.menu.manage_answer_section_menu, menu);
        if (mMenu != null) {
            mMenu.findItem(R.id.action_add).setVisible(true);
            mMenu.findItem(R.id.action_save).setVisible(false);
            mMenu.findItem(R.id.action_cancel).setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            handleAdd();
            return true;
        } else if (id == R.id.action_save) {
            handleSave();
            return true;
        } else if (id == R.id.action_cancel) {
            handleCancel();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void handleAdd() {
        //On add new button click, show dialog to get name, place at bottom , add to db
        String content = answerSection.substring(0, answerSection.length() - 1).toLowerCase(Locale.getDefault());
        if (answerSection.equals("Activities")) {
            content = "activity";
        }
        new MaterialDialog.Builder(this.getContext())
                .title("Add new answer")
                .content("Enter new " + content)
                .inputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
                .inputRange(1, 64)
                .positiveText("Submit")
                .negativeText("Cancel")
                .input("New answer", "", false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        new AddNewAnswerTask().execute(input.toString());
                    }
                }).show();
    }

    private void handleSave() {

        if (!reOrder) {//normal
            //Delete operation
            if (removedNonPriorityItemList != null) {
                initiateListDelete(removedNonPriorityItemList);
            } else {
                AppUtil.showToast(getContext(), "Nothing to save");
            }
        } else {
            if (reorderedList == null && removedPriorityItemList == null) {
                AppUtil.showToast(getContext(), "Nothing to save");
            } else {
                if (reorderedList != null && removedPriorityItemList != null) {//choose

                    new MaterialDialog.Builder(this.getContext())
                            .title("Multiple actions")
                            .content("Multiple actions selected. Please choose one")
                            .positiveText("Reorder")
                            .negativeText("Delete")
                            .neutralText("Cancel")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    new ReorderAnswerListTask(reorderedList, true).execute();
                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    new DeleteAnswerListTask(removedPriorityItemList).execute();
                                }
                            }).show();
                } else if (reorderedList != null && removedPriorityItemList == null) {//reorder
                    initiateListReorder(reorderedList);
                } else if (reorderedList == null && removedPriorityItemList != null) {//delete
                    initiateListDelete(removedPriorityItemList);
                }
            }
        }

    }

    private void handleCancel() {
        reorderedList = null;
        removedPriorityItemList = null;
        removedNonPriorityItemList = null;
        //Show add button only
        if (mMenu != null) {
            mMenu.findItem(R.id.action_add).setVisible(true);
            mMenu.findItem(R.id.action_save).setVisible(false);
            mMenu.findItem(R.id.action_cancel).setVisible(false);
        }
        //Load answers to list
        new GetAnswerListTask().execute();
        AppUtil.showToast(getContext(), "Task canceled");
    }

    private void initiateListDelete(final List<AnswerSectionViewData> lst) {
        String content = answerSection.substring(0, answerSection.length() - 1).toLowerCase(Locale.getDefault());
        if (answerSection.equals("Activities")) {
            content = "activity";
        }
        new MaterialDialog.Builder(this.getContext())
                .title("Delete answers")
                .content("Permanently delete answer(s) from " + content)

                .positiveText("Confirm")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        new DeleteAnswerListTask(lst).execute();
                    }
                }).show();
    }

    private void initiateListReorder(final List<AnswerSectionViewData> lst) {
        String content = answerSection.substring(0, answerSection.length() - 1).toLowerCase(Locale.getDefault());
        if (answerSection.equals("Activities")) {
            content = "activity";
        }
        new MaterialDialog.Builder(this.getContext())
                .title("Reorder answers")
                .content("Permanently reorder answer(s) from " + content)

                .positiveText("Confirm")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        new ReorderAnswerListTask(lst, true).execute();
                    }
                }).show();
    }

    private void init() {

        //set weather to enable reorder
        reOrder = !(answerSection.equals("Locations") || answerSection.equals("Pain areas"));
        reorderedList = null;
        removedPriorityItemList = null;
        removedNonPriorityItemList = null;

        //Load answers to list
        new GetAnswerListTask().execute();
    }

    @Override
    public void onDataReordered(List<AnswerSectionViewData> answerList) {
        Log.d("AnswerSectionFragment", "onDataReordered called");
        //on drag and drop reorder according to priority

        List<AnswerSectionViewData> tmpList = new ArrayList<>();

        //Create new list with reordered priority
        for (int i = 0; i < answerList.size(); i++) {
            AnswerSectionViewData tmpItem = answerList.get(i);
            tmpList.add(new AnswerSectionViewData(tmpItem.getId(), tmpItem.getName(), i));
        }
        reorderedList = tmpList;//Replace tracking list

        //Show save or cancel, hide add
        if (mMenu != null) {
            mMenu.findItem(R.id.action_add).setVisible(false);
            mMenu.findItem(R.id.action_save).setVisible(true);
            mMenu.findItem(R.id.action_cancel).setVisible(true);
        }

    }

    @Override
    public void onNonPriorityAnswerRowClicked(AnswerSectionViewData answerSectionViewData) {
        //on item click show dialog to rename for basic answer
        initiateUpdateAnswer(answerSectionViewData, false);
    }

    private void initiateUpdateAnswer(final AnswerSectionViewData answerSectionViewData, final boolean setPriority) {
        String content = answerSection.substring(0, answerSection.length() - 1).toLowerCase(Locale.getDefault());
        if (answerSection.equals("Activities")) {
            content = "activity";
        }
        String contentStr = "Edit " + content;
        if (reOrder) {
            contentStr = "Edit " + content + ". WARNING: unsaved ordering will be lost ";
        }
        new MaterialDialog.Builder(this.getContext())
                .title("Update answer")
                .content(contentStr)
                .inputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
                .inputRange(1, 64)
                .positiveText("Submit")
                .negativeText("Cancel")
                .input("Update answer", answerSectionViewData.getName(), false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        answerSectionViewData.setName(input.toString());
                        new UpdateAnswerTask(setPriority).execute(answerSectionViewData);
                    }
                }).show();
    }

    @Override
    public void onNonPriorityDataItemRemoved(AnswerSectionViewData answerItem) {
        Log.d("AnswerSectionFragment", "onNonPriorityDataItemRemoved called");
        //on item deleted
        if (removedNonPriorityItemList == null) {
            removedNonPriorityItemList = new ArrayList<>();
        }
        removedNonPriorityItemList.add(new AnswerSectionViewData(answerItem.getId(), answerItem.getName(), -1));
        //Show save or cancel, hide add
        if (mMenu != null) {
            mMenu.findItem(R.id.action_add).setVisible(false);
            mMenu.findItem(R.id.action_save).setVisible(true);
            mMenu.findItem(R.id.action_cancel).setVisible(true);
        }

    }

    @Override
    public void onPriorityAnswerRawClicked(AnswerSectionViewData answerSectionViewData) {
        //on item click show dialog to rename for advanced answer
        initiateUpdateAnswer(answerSectionViewData, false);
    }

    @Override
    public void onPriorityDataItemRemoved(AnswerSectionViewData answerItem) {
        Log.d("AnswerSectionFragment", "onPriorityDataItemRemoved called");
        //on item deleted
        if (removedPriorityItemList == null) {
            removedPriorityItemList = new ArrayList<>();
        }
        removedPriorityItemList.add(new AnswerSectionViewData(answerItem.getId(), answerItem.getName(), answerItem.getPriority()));
        //Show save or cancel, hide add
        if (mMenu != null) {
            mMenu.findItem(R.id.action_add).setVisible(false);
            mMenu.findItem(R.id.action_save).setVisible(true);
            mMenu.findItem(R.id.action_cancel).setVisible(true);
        }
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        if (mItemTouchHelper != null) {
            mItemTouchHelper.startDrag(viewHolder);
        }
    }

    /**
     * Async task to add new Answer
     */
    private class AddNewAnswerTask extends AsyncTask<String, Void, Long> {

        private ProgressDialog nDialog;

        @Override
        protected Long doInBackground(String... answer) {
            Log.d("AddNewAnswerTask", " doInBackground - save answers");

            String name = answer[0].trim();
            long response = -1;
            int id;
            switch (answerSection) {
                case "Triggers": {
                    int priority = -1;
                    id = TriggerController.getLastRecordId() + 1;
                    ArrayList<Trigger> tmpLst = TriggerController.getAllTriggers();
                    if (tmpLst.size() > 0) {
                        Trigger trigger = tmpLst.get(tmpLst.size() - 1);
                        if (trigger != null) {
                            priority = trigger.getPriority();
                        }
                    }
                    priority++;
                    response = TriggerController.addTrigger(new Trigger(id, name, priority));
                    break;
                }
                case "Symptoms": {
                    int priority = -1;
                    id = SymptomController.getLastRecordId() + 1;
                    ArrayList<Symptom> tmpLst = SymptomController.getAllSymptoms();
                    if (tmpLst.size() > 0) {
                        Symptom symptom = tmpLst.get(tmpLst.size() - 1);
                        if (symptom != null) {
                            priority = symptom.getPriority();
                        }
                    }
                    priority++;
                    response = SymptomController.addSymptom(new Symptom(id, name, priority));
                    break;
                }
                case "Activities": {
                    int priority = -1;
                    id = LifeActivityController.getLastRecordId() + 1;
                    ArrayList<LifeActivity> tmpLst = LifeActivityController.getAllActivities();
                    if (tmpLst.size() > 0) {
                        LifeActivity lifeActivity = tmpLst.get(tmpLst.size() - 1);
                        if (lifeActivity != null) {
                            priority = lifeActivity.getPriority();
                        }
                    }
                    priority++;
                    response = LifeActivityController.addActivity(new LifeActivity(id, name, priority));
                    break;
                }
                case "Locations": {
                    id = LocationController.getLastRecordId() + 1;
                    response = LocationController.addLocation(new Location(id, name));
                    break;
                }
                case "Pain areas": {
                    id = BodyAreaController.getLastRecordId() + 1;
                    response = BodyAreaController.addBodyArea(new BodyArea(id, name));
                    break;
                }
                case "Medicines": {
                    int priority = -1;
                    id = MedicineController.getLastRecordId() + 1;
                    ArrayList<Medicine> tmpLst = MedicineController.getAllMedicines();
                    if (tmpLst.size() > 0) {
                        Medicine medicine = tmpLst.get(tmpLst.size() - 1);
                        if (medicine != null) {
                            priority = medicine.getPriority();
                        }
                    }
                    priority++;
                    response = MedicineController.addMedicine(new Medicine(id, name, priority, false));
                    break;
                }
                case "Reliefs": {
                    int priority = -1;
                    id = ReliefController.getLastRecordId() + 1;
                    ArrayList<Relief> tmpLst = ReliefController.getAllReliefs();
                    if (tmpLst.size() > 0) {
                        Relief relief = tmpLst.get(tmpLst.size() - 1);
                        if (relief != null) {
                            priority = relief.getPriority();
                        }
                    }
                    priority++;
                    response = ReliefController.addRelief(new Relief(id, name, priority, false));
                    break;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(getActivity());
            nDialog.setMessage("Adding new Answer...");
            nDialog.setTitle("Processing");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(false);
            nDialog.show();
        }


        @Override
        protected void onPostExecute(Long response) {
            Log.d("AddNewAnswerTask", " onPostExecute - show result");

            if (response > 0) {
                AppUtil.showToast(getContext(), "Answer added successfully");

                //Reset
                reorderedList = null;
                removedPriorityItemList = null;
                removedNonPriorityItemList = null;
                //Show add button only
                if (mMenu != null) {
                    mMenu.findItem(R.id.action_add).setVisible(true);
                    mMenu.findItem(R.id.action_save).setVisible(false);
                    mMenu.findItem(R.id.action_cancel).setVisible(false);
                }
                new GetAnswerListTask().execute();
            } else {
                AppUtil.showToast(getContext(), "Answer add failed");
            }
            if (nDialog != null) {
                nDialog.dismiss();
            }
        }
    }

    /**
     * Async task to remove answers
     */
    private class DeleteAnswerListTask extends AsyncTask<Void, Void, Boolean> {
        private ProgressDialog nDialog;
        private List<AnswerSectionViewData> removeList;

        DeleteAnswerListTask(List<AnswerSectionViewData> removeList) {
            this.removeList = removeList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(getActivity());
            nDialog.setMessage("Deleting answers...");
            nDialog.setTitle("Processing");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(false);
            nDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... v) {
            Log.d("DeleteAnswerListTask", " doInBackground - delete");

            boolean response = DBTransactionHandler.deleteAnswers(answerSection, removeList);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(Boolean response) {
            Log.d("DeleteAnswerListTask", " onPostExecute - show result");

            if (response) {
                AppUtil.showToast(getContext(), "Answers deleted successfully");

                //Reset
                reorderedList = null;
                removedPriorityItemList = null;
                removedNonPriorityItemList = null;
                //Show add button only
                if (mMenu != null) {
                    mMenu.findItem(R.id.action_add).setVisible(true);
                    mMenu.findItem(R.id.action_save).setVisible(false);
                    mMenu.findItem(R.id.action_cancel).setVisible(false);
                }
                //Load answers to list
                new GetAnswerListTask().execute();
            } else {
                AppUtil.showToast(getContext(), "Answer delete failed");
            }
            if (nDialog != null) {
                nDialog.dismiss();
            }
        }
    }

    /**
     * Async task to load answers
     */
    private class GetAnswerListTask extends AsyncTask<String, Void, List<AnswerSectionViewData>> {
        private ProgressDialog nDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(getActivity());
            nDialog.setMessage("Refreshing answer list...");
            nDialog.setTitle("Processing");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(false);
            nDialog.show();
        }

        @Override
        protected List<AnswerSectionViewData> doInBackground(String... params) {
            Log.d("GetAnswerListTask", " doInBackground - query records");
            //Load from async task the answers
            List<AnswerSectionViewData> answerSectionViewDataLst = null;
            switch (answerSection) {
                case "Triggers":
                    answerSectionViewDataLst = TriggerController.getAnswerSectionViewData();
                    break;
                case "Symptoms":
                    answerSectionViewDataLst = SymptomController.getAnswerSectionViewData();
                    break;
                case "Activities":
                    answerSectionViewDataLst = LifeActivityController.getAnswerSectionViewData();
                    break;
                case "Locations":
                    answerSectionViewDataLst = LocationController.getAnswerSectionViewData();
                    break;
                case "Pain areas":
                    answerSectionViewDataLst = BodyAreaController.getAnswerSectionViewData();
                    break;
                case "Medicines":
                    answerSectionViewDataLst = MedicineController.getAnswerSectionViewData();
                    break;
                case "Reliefs":
                    answerSectionViewDataLst = ReliefController.getAnswerSectionViewData();
                    break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return answerSectionViewDataLst;
        }

        @Override
        protected void onPostExecute(List<AnswerSectionViewData> answerSectionViewDataLst) {
            Log.d("GetAnswerListTask", " onPostExecute - update ui");

            //show recycler view with answers
            RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.answers_list_recycler_view);

            recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
            // 2. set layoutManger
            recyclerView.setLayoutManager(new LinearLayoutManager(AnswerSectionFragment.this.getActivity()));

            //load answers
            if (!reOrder) {
                if (recyclerView.getAdapter() != null) {
                    NonPriorityAnswerSectionAdapter tmpAdapter = (NonPriorityAnswerSectionAdapter) recyclerView.getAdapter();
                    if (tmpAdapter != null) {
                        tmpAdapter.clearData();
                        tmpAdapter.setData(answerSectionViewDataLst);
                    }
                } else {
                    //basic answers
                    // 3. create an adapter
                    NonPriorityAnswerSectionAdapter nonPriorityAnswerSectionAdapter =
                            new NonPriorityAnswerSectionAdapter(
                                    AnswerSectionFragment.this,
                                    AnswerSectionFragment.this,
                                    answerSectionViewDataLst);

                    // 4. set adapter
                    recyclerView.setAdapter(nonPriorityAnswerSectionAdapter);

                    // 5. set item animator to DefaultAnimator
                    recyclerView.setItemAnimator(new DefaultItemAnimator());

                    //Enable swap
                    ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(nonPriorityAnswerSectionAdapter, false, true);
                    mItemTouchHelper = new ItemTouchHelper(callback);
                    mItemTouchHelper.attachToRecyclerView(recyclerView);
                }

                AppUtil.showToast(getContext(), "Swipe to remove answer");

            } else {
                if (recyclerView.getAdapter() != null) {
                    PriorityAnswerSectionAdapter tmpAdapter = (PriorityAnswerSectionAdapter) recyclerView.getAdapter();
                    if (tmpAdapter != null) {
                        tmpAdapter.clearData();
                        tmpAdapter.setData(answerSectionViewDataLst);
                    }
                } else {
                    //reorder answers
                    // 3. create an adapter
                    PriorityAnswerSectionAdapter priorityAnswerSectionAdapter =
                            new PriorityAnswerSectionAdapter(
                                    AnswerSectionFragment.this,
                                    AnswerSectionFragment.this,
                                    AnswerSectionFragment.this,
                                    AnswerSectionFragment.this,
                                    answerSectionViewDataLst);


                    // 4. set adapter
                    recyclerView.setAdapter(priorityAnswerSectionAdapter);
                    // 5. set item animator to DefaultAnimator
                    recyclerView.setItemAnimator(new DefaultItemAnimator());

                    //Enable drag and swipe
                    ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(priorityAnswerSectionAdapter, true, true);
                    mItemTouchHelper = new ItemTouchHelper(callback);
                    mItemTouchHelper.attachToRecyclerView(recyclerView);
                }

                AppUtil.showToast(getContext(), "Drag and drop answer to reorder, swipe to remove");
            }
            if (nDialog != null) {
                nDialog.dismiss();
            }
        }
    }

    /**
     * Async task to reorder answers
     */
    private class ReorderAnswerListTask extends AsyncTask<Void, Void, Long> {
        private ProgressDialog nDialog;
        private List<AnswerSectionViewData> reorderList;
        private boolean setPriority;

        ReorderAnswerListTask(List<AnswerSectionViewData> reorderList, boolean setPriority) {
            this.reorderList = reorderList;
            this.setPriority = setPriority;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(getActivity());
            nDialog.setMessage("Reordering answer list...");
            nDialog.setTitle("Processing");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(false);
            nDialog.show();
        }

        @Override
        protected Long doInBackground(Void... v) {
            Log.d("ReorderAnswerListTask", " doInBackground - reorder");
            long response = -1;

            boolean canContinue = true;
            for (AnswerSectionViewData reorderItem : reorderList) {

                if (!canContinue) {
                    break;
                }
                int id = reorderItem.getId();
                String name = reorderItem.getName().trim();
                int priority = -1;
                if (setPriority) {
                    priority = reorderItem.getPriority();
                }

                switch (answerSection) {
                    case "Triggers": {
                        response = TriggerController.updateTriggerRecord(new Trigger(id, name, priority));
                        if (response < 0) {
                            canContinue = false;
                        }
                        break;
                    }
                    case "Symptoms": {
                        response = SymptomController.updateSymptomRecord(new Symptom(id, name, priority));
                        if (response < 0) {
                            canContinue = false;
                        }
                        break;
                    }
                    case "Activities": {
                        response = LifeActivityController.updateActivityRecord(new LifeActivity(id, name, priority));
                        if (response < 0) {
                            canContinue = false;
                        }
                        break;
                    }
                    case "Medicines": {
                        response = MedicineController.updateMedicineRecord(new Medicine(id, name, priority, false));
                        if (response < 0) {
                            canContinue = false;
                        }
                        break;
                    }
                    case "Reliefs": {
                        response = ReliefController.updateReliefRecord(new Relief(id, name, priority, false));
                        if (response < 0) {
                            canContinue = false;
                        }
                        break;
                    }
                }

            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(Long response) {
            Log.d("ReorderAnswerListTask", " onPostExecute - show result");
            if (response > 0) {
                AppUtil.showToast(getContext(), "Answers reordered successfully");

                //Reset
                reorderedList = null;
                removedPriorityItemList = null;
                removedNonPriorityItemList = null;
                //Show add button only
                if (mMenu != null) {
                    mMenu.findItem(R.id.action_add).setVisible(true);
                    mMenu.findItem(R.id.action_save).setVisible(false);
                    mMenu.findItem(R.id.action_cancel).setVisible(false);
                }
                //Load answers to list
                new GetAnswerListTask().execute();
            } else {
                AppUtil.showToast(getContext(), "Answers reorder failed");
            }
            if (nDialog != null) {
                nDialog.dismiss();
            }
        }
    }

    /**
     * Async task to update Answer
     */
    private class UpdateAnswerTask extends AsyncTask<AnswerSectionViewData, Void, Long> {
        private ProgressDialog nDialog;
        private boolean setPriority;

        UpdateAnswerTask(boolean setPriority) {
            this.setPriority = setPriority;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog = new ProgressDialog(getActivity());
            nDialog.setMessage("Updating answer...");
            nDialog.setTitle("Processing");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(false);
            nDialog.show();
        }

        @Override
        protected Long doInBackground(AnswerSectionViewData... answerSectionViewData) {
            Log.d("UpdateAnswerTask", " doInBackground - update answer");

            int id = answerSectionViewData[0].getId();
            String name = answerSectionViewData[0].getName().trim();
            int priority = -1;
            if (setPriority) {
                priority = answerSectionViewData[0].getPriority();
            }


            long response = -1;
            switch (answerSection) {
                case "Triggers": {
                    response = TriggerController.updateTriggerRecord(new Trigger(id, name, priority));
                    break;
                }
                case "Symptoms": {
                    response = SymptomController.updateSymptomRecord(new Symptom(id, name, priority));
                    break;
                }
                case "Activities": {
                    response = LifeActivityController.updateActivityRecord(new LifeActivity(id, name, priority));
                    break;
                }
                case "Locations": {
                    response = LocationController.updateLocationRecord(new Location(id, name));
                    break;
                }
                case "Pain areas": {
                    response = BodyAreaController.updateBodyAreaRecord(new BodyArea(id, name));
                    break;
                }
                case "Medicines": {
                    response = MedicineController.updateMedicineRecord(new Medicine(id, name, priority, false));
                    break;
                }
                case "Reliefs": {
                    response = ReliefController.updateReliefRecord(new Relief(id, name, priority, false));
                    break;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(Long response) {
            Log.d("UpdateAnswerTask", " onPostExecute - show result");
            if (response > 0) {
                AppUtil.showToast(getContext(), "Answer updated successfully");
                //Reset
                reorderedList = null;
                removedPriorityItemList = null;
                removedNonPriorityItemList = null;
                //Show add button only
                if (mMenu != null) {
                    mMenu.findItem(R.id.action_add).setVisible(true);
                    mMenu.findItem(R.id.action_save).setVisible(false);
                    mMenu.findItem(R.id.action_cancel).setVisible(false);
                }
                new GetAnswerListTask().execute();
            } else {
                AppUtil.showToast(getContext(), "Answer update failed");
            }
            if (nDialog != null) {
                nDialog.dismiss();
            }
        }
    }

}