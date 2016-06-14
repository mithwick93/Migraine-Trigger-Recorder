package shehan.com.migrainetrigger.controller;

import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shehan.com.migrainetrigger.data.dao.DBActivityDAO;
import shehan.com.migrainetrigger.data.model.LifeActivity;
import shehan.com.migrainetrigger.utility.AppUtil;
import shehan.com.migrainetrigger.utility.MigraineTriggerApplication;
import shehan.com.migrainetrigger.view.model.AnswerSectionViewData;

/**
 * Created by Shehan on 4/13/2016.
 */
public class LifeActivityController {

    public static long addActivity(LifeActivity lifeActivity) {
        return DBActivityDAO.addActivity(lifeActivity);
    }

    public static ArrayList<LifeActivity> getActivitiesForRecord(int recordId) {
        return DBActivityDAO.getActivitiesForRecord(recordId);
    }

    /**
     * Get all activities, apply sort
     *
     * @param applySuggestions enable suggestions
     * @return ArrayList<LifeActivity>
     */
    public static ArrayList<LifeActivity> getAllActivities(boolean applySuggestions) {
        Log.d("LifeActivityController", "getAllActivities ");

        ArrayList<LifeActivity> lst = DBActivityDAO.getAllActivities();

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
        ArrayList<LifeActivity> lst = getAllActivities(false);
        List<AnswerSectionViewData> answerSectionViewDataLst = new ArrayList<>();
        for (int i = 0; i < lst.size(); i++) {
            LifeActivity lifeActivity = lst.get(i);

            answerSectionViewDataLst.add(new AnswerSectionViewData(lifeActivity.getActivityId(), lifeActivity.getActivityName(), lifeActivity.getPriority()));
        }
        return answerSectionViewDataLst;
    }

    public static int getLastRecordId() {
        return DBActivityDAO.getLastRecordId();
    }

    private static ArrayList<LifeActivity> getReOrderedLst(ArrayList<LifeActivity> lst) {
        Log.d("LifeActivityController", "getReOrderedLst ");
        ArrayList<String> topLst = AppUtil.getTopList("LifeActivity");

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

    private static ArrayList<LifeActivity> reorderLst(ArrayList<LifeActivity> lst, String match) {
        Log.d("LifeActivityController", "reorderLst ");
        int pos = -1;
        for (int itr = 0; itr < lst.size(); itr++) {
            if (lst.get(itr).getActivityName().trim().equals(match.trim())) {//get position of element
                pos = itr;
                break;
            }
        }
        if (pos > -1 && pos < lst.size()) {
            LifeActivity tmp = lst.remove(pos);//remove and insert at beginning
            lst.add(0, tmp);
        } else {
            Log.e("LifeActivityController", "reorderLst - top lst item not found at index " + 0);
        }
        return lst;
    }

    public static long updateActivityRecord(LifeActivity lifeActivity) {
        return DBActivityDAO.updateActivityRecord(lifeActivity);
    }

}
