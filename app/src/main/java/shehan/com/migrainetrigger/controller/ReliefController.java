package shehan.com.migrainetrigger.controller;

import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shehan.com.migrainetrigger.data.dao.DBReliefDAO;
import shehan.com.migrainetrigger.data.model.Relief;
import shehan.com.migrainetrigger.utility.AppUtil;
import shehan.com.migrainetrigger.utility.MigraineTriggerApplication;
import shehan.com.migrainetrigger.view.model.AnswerSectionViewData;

/**
 * Created by Shehan on 4/13/2016.
 */
public class ReliefController {

    public static long addReliefRecord(int reliefId, int recordId, boolean effective) {
        Log.d("ReliefController", " addReliefRecord ");
        return DBReliefDAO.addReliefRecord(reliefId, recordId, effective);
    }

    public static long addReliefs(ArrayList<Relief> lst) {
        for (Relief itm : lst) {
            long result = addRelief(itm);
            if (result < 1) {
                return 0;
            }
        }
        return lst.size();
    }

    public static long addRelief(Relief relief) {
        return DBReliefDAO.addRelief(relief);
    }

    public static long deleteRelief(int id) {
        return DBReliefDAO.deleteRelief(id);
    }

    public static List<AnswerSectionViewData> getAnswerSectionViewData() {
        ArrayList<Relief> lst = getAllReliefs();
        List<AnswerSectionViewData> answerSectionViewDataLst = new ArrayList<>();
        for (int i = 0; i < lst.size(); i++) {
            Relief relief = lst.get(i);

            answerSectionViewDataLst.add(new AnswerSectionViewData(relief.getReliefId(), relief.getReliefName(), relief.getPriority()));
        }
        return answerSectionViewDataLst;
    }

    public static ArrayList<Relief> getAllReliefs() {
        Log.d("ReliefController", " getAllReliefs ");
        ArrayList<Relief> lst = DBReliefDAO.getAllReliefs();

        Collections.sort(lst);

        //enable suggestions
        boolean suggestions = PreferenceManager.getDefaultSharedPreferences(MigraineTriggerApplication.getAppContext()).getBoolean("pref_suggestions", false);
        if (lst.size() > 0 && suggestions) {
            return getReOrderedLst(lst);
        }

        return lst;
    }

    private static ArrayList<Relief> getReOrderedLst(ArrayList<Relief> lst) {
        Log.d("ReliefController", "getReOrderedLst ");
        ArrayList<String> topLst = AppUtil.getTopList("Relief");

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


    private static ArrayList<Relief> reorderLst(ArrayList<Relief> lst, String match) {
        Log.d("ReliefController", "reorderLst ");
        int pos = -1;
        for (int itr = 0; itr < lst.size(); itr++) {
            if (lst.get(itr).getReliefName().trim().equals(match.trim())) {//get position of element
                pos = itr;
                break;
            }
        }
        if (pos > -1 && pos < lst.size()) {
            Relief tmp = lst.remove(pos);//remove and insert at beginning
            lst.add(0, tmp);
        } else {
            Log.e("ReliefController", "reorderLst - top lst item not found at index " + 0);
        }
        return lst;
    }

    public static int getLastRecordId() {
        return DBReliefDAO.getLastRecordId();
    }

    public static Relief getReliefById(int id) {
        return DBReliefDAO.getRelief(id);
    }

    public static ArrayList<Relief> getReliefsForRecord(int recordId) {
        return DBReliefDAO.getReliefsForRecord(recordId);
    }

    public static long updateReliefRecord(Relief relief) {
        return DBReliefDAO.updateReliefRecord(relief);
    }
}
