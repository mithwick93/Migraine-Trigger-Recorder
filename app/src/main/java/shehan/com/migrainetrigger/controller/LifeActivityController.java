package shehan.com.migrainetrigger.controller;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

import shehan.com.migrainetrigger.data.dao.DBActivityDAO;
import shehan.com.migrainetrigger.data.model.LifeActivity;

/**
 * Created by Shehan on 4/13/2016.
 */
public class LifeActivityController {

    public static void addNewActivity(LifeActivity activity) {

    }

    public static LifeActivity getActivityById(int id) {
        return null;
    }

    /**
     * @return
     */
    public static ArrayList<LifeActivity> getAllActivities() {
        Log.d("getAll", "getAllActivities ");

        ArrayList<LifeActivity> lifeActivityArrayList = DBActivityDAO.getAllActivities();
        Collections.sort(lifeActivityArrayList);
        return lifeActivityArrayList;
    }

    public static void deleteActivity() {

    }

    public static void updateActivity(LifeActivity activity) {

    }

    public static void reorderPriority(LifeActivity activity) {

    }

    /**
     * @param activityId
     * @param recordId
     * @return
     */
    public static long addActivityRecord(int activityId, int recordId) {
        Log.d("getAll", "addActivityRecord ");
        return DBActivityDAO.addActivityRecord(activityId, recordId);
    }

    public static ArrayList<LifeActivity> getActivitiesForRecord(int recordId) {
        return DBActivityDAO.getActivitiesForRecord(recordId);
    }

}
