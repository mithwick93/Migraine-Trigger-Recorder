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

    public static long addTrigger(Trigger trigger) {
        return DBTriggerDAO.addTrigger(trigger);
    }

    /**
     * Get all triggers, apply sort
     *
     * @param applySuggestions enable suggestions
     * @return ArrayList<Trigger>
     */
    public static ArrayList<Trigger> getAllTriggers(boolean applySuggestions) {
        Log.d("TriggerController", " getAllTriggers ");
        ArrayList<Trigger> lst = DBTriggerDAO.getAllTriggers();

        Collections.sort(lst);

        if (applySuggestions) {
            //enable suggestions
            boolean suggestions = PreferenceManager.getDefaultSharedPreferences(MigraineTriggerApplication.getAppContext()).getBoolean("pref_suggestions", false);
            if (lst.size() > 0 && suggestions) {
                return getReOrderedLst(lst);
            }
        }

        return lst;
    }

    public static List<AnswerSectionViewData> getAnswerSectionViewData() {
        ArrayList<Trigger> lst = getAllTriggers(false);
        List<AnswerSectionViewData> answerSectionViewDataLst = new ArrayList<>();
        for (int i = 0; i < lst.size(); i++) {
            Trigger trigger = lst.get(i);

            answerSectionViewDataLst.add(new AnswerSectionViewData(trigger.getTriggerId(), trigger.getTriggerName(), trigger.getPriority()));
        }
        return answerSectionViewDataLst;
    }

    public static int getLastRecordId() {
        return DBTriggerDAO.getLastRecordId();
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

    public static ArrayList<Trigger> getTriggersForRecord(int recordId) {
        return DBTriggerDAO.getTriggersForRecord(recordId);
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

    public static long updateTriggerRecord(Trigger trigger) {
        return DBTriggerDAO.updateTriggerRecord(trigger);
    }
}
