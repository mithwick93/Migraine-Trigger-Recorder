package shehan.com.migrainetrigger.controller;

import android.util.Log;

import java.util.ArrayList;

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

    public static ArrayList<LifeActivity> getAllActivities() {
        Log.d("getAll", "getAllActivities ");
        return DBActivityDAO.getAllActivities();
    }

    public static void deleteActivity() {

    }

    public static void updateActivity(LifeActivity activity) {

    }

    public static void reorderPriority(LifeActivity activity) {

    }

    public static long addActivityRecord(int activityId, int recordId) {
        return DBActivityDAO.addActivityRecord(activityId, recordId);
    }

}
