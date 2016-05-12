package shehan.com.migrainetrigger.controller;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

import shehan.com.migrainetrigger.data.dao.DBTriggerDAO;
import shehan.com.migrainetrigger.data.model.Trigger;

/**
 * Created by Shehan on 4/13/2016.
 */
public class TriggerController {

    public static void addNewTrigger(Trigger trigger) {

    }

    public static Trigger getTriggerById(int id) {
        return null;
    }


    public static ArrayList<Trigger> getAllTriggers() {
        Log.d("addTriggerRecord", " getAllTriggers ");
        ArrayList<Trigger> triggerArrayList = DBTriggerDAO.getAllTriggers();
        Collections.sort(triggerArrayList);
        return triggerArrayList;
    }

    public static void deleteTrigger() {

    }

    public static void updateTrigger(Trigger trigger) {

    }

    public static void reorderPriority(Trigger trigger) {

    }

    public static long addTriggerRecord(int triggerId, int recordId) {
        Log.d("addTriggerRecord", " addTriggerRecord ");
        return DBTriggerDAO.addTriggerRecord(triggerId, recordId);
    }

    public static ArrayList<Trigger> getTriggersForRecord(int recordId) {
        return DBTriggerDAO.getTriggersForRecord(recordId);
    }
}
