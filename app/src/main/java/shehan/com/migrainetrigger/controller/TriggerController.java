package shehan.com.migrainetrigger.controller;

import android.util.Log;

import java.util.ArrayList;

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
        Log.d("getAll", " getAllTriggers ");
        return DBTriggerDAO.getAllTriggers();
    }

    public static void deleteTrigger() {

    }

    public static void updateTrigger(Trigger trigger) {

    }

    public static void reorderPriority(Trigger trigger) {

    }

    public static long addTriggerRecord(int triggerId, int recordId) {
        return DBTriggerDAO.addTriggerRecord(triggerId, recordId);
    }
}
