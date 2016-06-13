package shehan.com.migrainetrigger.controller;

import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shehan.com.migrainetrigger.data.dao.DBBodyAreaDAO;
import shehan.com.migrainetrigger.data.model.BodyArea;
import shehan.com.migrainetrigger.utility.AppUtil;
import shehan.com.migrainetrigger.utility.MigraineTriggerApplication;
import shehan.com.migrainetrigger.view.model.AnswerSectionViewData;

/**
 * Created by Shehan on 4/13/2016.
 */
public class BodyAreaController {

    public static long addBodyArea(BodyArea bodyArea) {
        return DBBodyAreaDAO.addBodyArea(bodyArea);
    }

    public static long addBodyAreaRecord(int bodyAreaId, int recordId) {
        Log.d("BodyAreaController", " addBodyAreaRecord ");
        return DBBodyAreaDAO.addBodyAreaRecord(bodyAreaId, recordId);
    }

    public static long addBodyAreas(ArrayList<BodyArea> lst) {
        for (BodyArea itm : lst) {
            long result = addBodyArea(itm);
            if (result < 1) {
                return 0;
            }
        }
        return lst.size();
    }

    public static long deleteBodyArea(int id) {
        return DBBodyAreaDAO.deleteBodyArea(id);
    }

    public static ArrayList<BodyArea> getAllBodyAreas(boolean applySuggestions) {
        Log.d("BodyAreaController", " getAllBodyAreas ");
        ArrayList<BodyArea> lst = DBBodyAreaDAO.getAllBodyAreas();

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
        ArrayList<BodyArea> lst = getAllBodyAreas(false);
        List<AnswerSectionViewData> answerSectionViewDataLst = new ArrayList<>();
        for (int i = 0; i < lst.size(); i++) {
            BodyArea bodyArea = lst.get(i);

            answerSectionViewDataLst.add(new AnswerSectionViewData(bodyArea.getBodyAreaId(), bodyArea.getBodyAreaName()));
        }
        return answerSectionViewDataLst;
    }

    public static BodyArea getBodyAreaById(int id) {
        return DBBodyAreaDAO.getBodyArea(id);
    }

    public static ArrayList<BodyArea> getBodyAreasForRecord(int recordId) {
        return DBBodyAreaDAO.getBodyAreasForRecord(recordId);
    }

    public static int getLastRecordId() {
        return DBBodyAreaDAO.getLastRecordId();
    }

    private static ArrayList<BodyArea> getReOrderedLst(ArrayList<BodyArea> lst) {
        Log.d("BodyAreaController", "getReOrderedLst ");
        ArrayList<String> topLst = AppUtil.getTopList("BodyArea");

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

    private static ArrayList<BodyArea> reorderLst(ArrayList<BodyArea> lst, String match) {
        Log.d("BodyAreaController", "reorderLst ");
        int pos = -1;
        for (int itr = 0; itr < lst.size(); itr++) {
            if (lst.get(itr).getBodyAreaName().trim().equals(match.trim())) {//get position of element
                pos = itr;
                break;
            }
        }
        if (pos > -1 && pos < lst.size()) {
            BodyArea tmp = lst.remove(pos);//remove and insert at beginning
            lst.add(0, tmp);
        } else {
            Log.e("BodyAreaController", "reorderLst - top lst item not found at index " + 0);
        }
        return lst;
    }

    public static long updateBodyAreaRecord(BodyArea bodyArea) {
        return DBBodyAreaDAO.updateBodyAreaRecord(bodyArea);
    }
}
