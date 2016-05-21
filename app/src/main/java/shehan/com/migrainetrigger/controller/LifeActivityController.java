package shehan.com.migrainetrigger.controller;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

import shehan.com.migrainetrigger.data.dao.DBActivityDAO;
import shehan.com.migrainetrigger.data.model.LifeActivity;
import shehan.com.migrainetrigger.view.model.AnswerSectionViewData;

/**
 * Created by Shehan on 4/13/2016.
 */
public class LifeActivityController {

    public static long addActivities(ArrayList<LifeActivity> lst) {
        for (LifeActivity itm : lst) {
            long result = addActivity(itm);
            if (result < 1) {
                return 0;
            }
        }
        return lst.size();
    }

    public static long addActivity(LifeActivity lifeActivity) {
        return DBActivityDAO.addActivity(lifeActivity);
    }

    public static long addActivityRecord(int activityId, int recordId) {
        Log.d("getAll", "addActivityRecord ");
        return DBActivityDAO.addActivityRecord(activityId, recordId);
    }

    public static void addNewActivity(LifeActivity activity) {

    }

    public static long deleteActivity(int id) {
        return DBActivityDAO.deleteActivity(id);
    }

    public static ArrayList<LifeActivity> getActivitiesForRecord(int recordId) {
        return DBActivityDAO.getActivitiesForRecord(recordId);
    }

    public static LifeActivity getActivityById(int id) {
        return null;
    }

    public static AnswerSectionViewData[] getAnswerSectionViewData() {
        ArrayList<LifeActivity> lst = getAllActivities();
        AnswerSectionViewData[] answerSectionViewData = new AnswerSectionViewData[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            LifeActivity lifeActivity = lst.get(i);

            answerSectionViewData[i] = new AnswerSectionViewData(lifeActivity.getActivityId(), lifeActivity.getActivityName(), lifeActivity.getPriority());
        }
        return answerSectionViewData;
    }

    public static ArrayList<LifeActivity> getAllActivities() {
        Log.d("getAll", "getAllActivities ");

        ArrayList<LifeActivity> lifeActivityArrayList = DBActivityDAO.getAllActivities();
        Collections.sort(lifeActivityArrayList);
        return lifeActivityArrayList;
    }

    public static int getLastRecordId() {
        return DBActivityDAO.getLastRecordId();
    }

    public static void reorderPriority(LifeActivity activity) {

    }

    public static long updateActivityRecord(LifeActivity lifeActivity) {
        return DBActivityDAO.updateActivityRecord(lifeActivity);
    }
}
