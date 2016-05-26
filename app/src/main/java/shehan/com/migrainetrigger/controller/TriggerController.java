package shehan.com.migrainetrigger.controller;

import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shehan.com.migrainetrigger.data.dao.DBTriggerDAO;
import shehan.com.migrainetrigger.data.model.Trigger;
import shehan.com.migrainetrigger.utility.AppUtil;
import shehan.com.migrainetrigger.utility.MigraineTriggerApplication;
import shehan.com.migrainetrigger.view.model.AnswerSectionViewData;

/**
 * Created by Shehan on 4/13/2016.
 */
public class TriggerController {

    public static long addTriggerRecord(int triggerId, int recordId) {
        Log.d("TriggerController", " addTriggerRecord ");
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

    public static List<AnswerSectionViewData> getAnswerSectionViewData() {
        ArrayList<Trigger> lst = getAllTriggers();
        List<AnswerSectionViewData> answerSectionViewDataLst = new ArrayList<>();
        for (int i = 0; i < lst.size(); i++) {
            Trigger trigger = lst.get(i);

            answerSectionViewDataLst.add(new AnswerSectionViewData(trigger.getTriggerId(), trigger.getTriggerName(), trigger.getPriority()));
        }
        return answerSectionViewDataLst;
    }

    public static ArrayList<Trigger> getAllTriggers() {
        Log.d("TriggerController", " getAllTriggers ");
        ArrayList<Trigger> lst = DBTriggerDAO.getAllTriggers();

        Collections.sort(lst);

        //enable suggestions
        boolean suggestions = PreferenceManager.getDefaultSharedPreferences(MigraineTriggerApplication.getAppContext()).getBoolean("pref_suggestions", false);
        if (lst.size() > 0 && suggestions) {
            return getReOrderedLst(lst);
        }

        return lst;
    }

    private static ArrayList<Trigger> getReOrderedLst(ArrayList<Trigger> lst) {
        Log.d("TriggerController", "getReOrderedLst ");
        ArrayList<String> topLst = AppUtil.getTopList("Trigger");

        if (topLst != null && topLst.size() > 0) {
            Collections.reverse(topLst);//reverse priority
            //check for 1
            lst = reorderLst(lst, topLst.get(0));

            if (topLst.size() > 1) {//check for 2
                lst = reorderLst(lst, topLst.get(1));
            }
            if (topLst.size() > 2) {//check for 3
                lst = reorderLst(lst, topLst.get(2));
            }
        }

        return lst;
    }

    private static ArrayList<Trigger> reorderLst(ArrayList<Trigger> lst, String match) {
        Log.d("TriggerController", "reorderLst ");
        int pos = -1;
        for (int itr = 0; itr < lst.size(); itr++) {
            if (lst.get(itr).getTriggerName().trim().equals(match.trim())) {//get position of element
                pos = itr;
                break;
            }
        }
        if (pos > -1 && pos < lst.size()) {
            Trigger tmp = lst.remove(pos);//remove and insert at beginning
            lst.add(0, tmp);
        } else {
            Log.e("TriggerController", "reorderLst - top lst item not found at index " + 0);
        }
        return lst;
    }

    public static int getLastRecordId() {
        return DBTriggerDAO.getLastRecordId();
    }

    public static Trigger getTriggerById(int id) {
        return DBTriggerDAO.getTrigger(id);
    }

    public static ArrayList<Trigger> getTriggersForRecord(int recordId) {
        return DBTriggerDAO.getTriggersForRecord(recordId);
    }

    public static long updateTriggerRecord(Trigger trigger) {
        return DBTriggerDAO.updateTriggerRecord(trigger);
    }
}
