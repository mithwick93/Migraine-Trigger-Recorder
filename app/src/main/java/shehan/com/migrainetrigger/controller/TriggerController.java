package shehan.com.migrainetrigger.controller;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

import shehan.com.migrainetrigger.data.dao.DBTriggerDAO;
import shehan.com.migrainetrigger.data.model.Trigger;
import shehan.com.migrainetrigger.view.model.AnswerSectionViewData;

/**
 * Created by Shehan on 4/13/2016.
 */
public class TriggerController {

    public static void addNewTrigger(Trigger trigger) {

    }

    public static long addTriggerRecord(int triggerId, int recordId) {
        Log.d("addTriggerRecord", " addTriggerRecord ");
        return DBTriggerDAO.addTriggerRecord(triggerId, recordId);
    }

    public static long addTriggers(ArrayList<Trigger> lst) {
        for (Trigger itm : lst) {
            long result = addTrigger(itm);
            if (result < 1) {
                return 0;
            }
        }
        return lst.size();
    }

    public static long addTrigger(Trigger trigger) {
        return DBTriggerDAO.addTrigger(trigger);
    }

    public static long deleteTrigger(int id) {
        return DBTriggerDAO.deleteTrigger(id);
    }

    public static AnswerSectionViewData[] getAnswerSectionViewData() {
        ArrayList<Trigger> lst = getAllTriggers();
        AnswerSectionViewData[] answerSectionViewData = new AnswerSectionViewData[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Trigger trigger = lst.get(i);

            answerSectionViewData[i] = new AnswerSectionViewData(trigger.getTriggerId(), trigger.getTriggerName(), trigger.getPriority());
        }
        return answerSectionViewData;
    }

    public static ArrayList<Trigger> getAllTriggers() {
        Log.d("addTriggerRecord", " getAllTriggers ");
        ArrayList<Trigger> triggerArrayList = DBTriggerDAO.getAllTriggers();
        Collections.sort(triggerArrayList);
        return triggerArrayList;
    }

    public static int getLastRecordId() {
        return DBTriggerDAO.getLastRecordId();
    }

    public static Trigger getTriggerById(int id) {
        return null;
    }

    public static ArrayList<Trigger> getTriggersForRecord(int recordId) {
        return DBTriggerDAO.getTriggersForRecord(recordId);
    }

    public static void reorderPriority(Trigger trigger) {

    }

    public static long updateTriggerRecord(Trigger trigger) {
        return DBTriggerDAO.updateTriggerRecord(trigger);
    }
}
