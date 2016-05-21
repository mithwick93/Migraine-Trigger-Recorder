package shehan.com.migrainetrigger.view.fragment.answer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.controller.BodyAreaController;
import shehan.com.migrainetrigger.controller.LifeActivityController;
import shehan.com.migrainetrigger.controller.LocationController;
import shehan.com.migrainetrigger.controller.MedicineController;
import shehan.com.migrainetrigger.controller.ReliefController;
import shehan.com.migrainetrigger.controller.SymptomController;
import shehan.com.migrainetrigger.controller.TriggerController;
import shehan.com.migrainetrigger.data.model.BodyArea;
import shehan.com.migrainetrigger.data.model.LifeActivity;
import shehan.com.migrainetrigger.data.model.Location;
import shehan.com.migrainetrigger.data.model.Medicine;
import shehan.com.migrainetrigger.data.model.Relief;
import shehan.com.migrainetrigger.data.model.Symptom;
import shehan.com.migrainetrigger.data.model.Trigger;
import shehan.com.migrainetrigger.utility.AppUtil;
import shehan.com.migrainetrigger.view.model.AnswerSectionViewData;


public class AnswerSectionFragment extends Fragment {

    private static final String ARG_ANSWER_SECTION = "answerSection";

    private String answerSection;

    private boolean reOrder;

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
        View view = inflater.inflate(R.layout.fragment_answer_section, container, false);
        AppUtil.showToast(getContext(), answerSection);
        setHasOptionsMenu(true);
        init(view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //Show add new answer on toolbar
        inflater.inflate(R.menu.manage_answer_section_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //override this in sub classes
        int id = item.getItemId();
        if (id == R.id.action_add) {
            addAnswer();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addAnswer() {
        String content = answerSection.substring(0, answerSection.length() - 1).toLowerCase();
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

    private void init(View view) {

        //set weather to enable reorder
        reOrder = !(answerSection.equals("Locations") || answerSection.equals("Pain areas"));

        //on item click show dialog to rename
        //on long press enter multi selection mode and delete
        //on drag and drop reorder according to priority
        //On add new buton click, show dialog to get name, place at bottom , add to db
    }

    /**
     * Async task to add new Answer
     */
    private class AddNewAnswerTask extends AsyncTask<String, Void, Long> {

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
            return response;
        }

        @Override
        protected void onPostExecute(Long response) {
            Log.d("AddNewAnswerTask", " onPostExecute - show result");
            if (response > 0) {
                AppUtil.showToast(getContext(), "Answer added successfully");
            } else {
                AppUtil.showToast(getContext(), "Answer add failed");
            }

        }
    }

    /**
     * Async task to load answers
     */
    private class GetAnswerListTask extends AsyncTask<String, Void, AnswerSectionViewData[]> {

        private View mView;

        GetAnswerListTask(View mView) {
            this.mView = mView;
        }


        @Override
        protected AnswerSectionViewData[] doInBackground(String... params) {
            Log.d("GetAnswerListTask", " doInBackground - query records");
            //Load from async task the answers
            AnswerSectionViewData[] answerSectionViewData = null;
            switch (answerSection) {
                case "Triggers":
                    answerSectionViewData = TriggerController.getAnswerSectionViewData();
                    break;
                case "Symptoms":
                    answerSectionViewData = SymptomController.getAnswerSectionViewData();
                    break;
                case "Activities":
                    answerSectionViewData = LifeActivityController.getAnswerSectionViewData();
                    break;
                case "Locations":
                    answerSectionViewData = LocationController.getAnswerSectionViewData();
                    break;
                case "Pain areas":
                    answerSectionViewData = BodyAreaController.getAnswerSectionViewData();
                    break;
                case "Medicines":
                    answerSectionViewData = MedicineController.getAnswerSectionViewData();
                    break;
                case "Reliefs":
                    answerSectionViewData = ReliefController.getAnswerSectionViewData();
                    break;
            }
            return answerSectionViewData;
        }

        @Override
        protected void onPostExecute(AnswerSectionViewData answerSectionViewData[]) {
            Log.d("GetAnswerListTask", " onPostExecute - update ui");

            //show recycler view with answers
            RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.answers_list_recycler_view);

            // 2. set layoutManger
            recyclerView.setLayoutManager(new LinearLayoutManager(AnswerSectionFragment.this.getActivity()));
//            // 3. create an adapter
//            RecordViewAdapter recordViewAdapter = new RecordViewAdapter(ViewRecordListFragment.this, answerSectionViewData);
//
//            // 4. set adapter
//            recyclerView.setAdapter(recordViewAdapter);

            // 5. set item animator to DefaultAnimator
            recyclerView.setItemAnimator(new DefaultItemAnimator());


        }
    }


}
