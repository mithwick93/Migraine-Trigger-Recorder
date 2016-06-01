package shehan.com.migrainetrigger.controller;

import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shehan.com.migrainetrigger.data.dao.DBLocationDAO;
import shehan.com.migrainetrigger.data.model.Location;
import shehan.com.migrainetrigger.utility.AppUtil;
import shehan.com.migrainetrigger.utility.MigraineTriggerApplication;
import shehan.com.migrainetrigger.view.model.AnswerSectionViewData;

/**
 * Created by Shehan on 4/13/2016.
 */
public class LocationController {

    public static long addLocations(ArrayList<Location> lst) {
        for (Location itm : lst) {
            long result = addLocation(itm);
            if (result < 1) {
                return 0;
            }
        }
        return lst.size();
    }

    public static long addLocation(Location location) {
        return DBLocationDAO.addLocation(location);
    }

    public static long deleteLocation(int id) {
        return DBLocationDAO.deleteLocation(id);
    }

    public static List<AnswerSectionViewData> getAnswerSectionViewData() {
        ArrayList<Location> lst = getAllLocations();
        List<AnswerSectionViewData> answerSectionViewDataLst = new ArrayList<>();
        for (int i = 0; i < lst.size(); i++) {
            Location location = lst.get(i);

            answerSectionViewDataLst.add(new AnswerSectionViewData(location.getLocationId(), location.getLocationName()));
        }
        return answerSectionViewDataLst;
    }

    public static ArrayList<Location> getAllLocations() {
        Log.d("LocationController", " getAllLocations ");
        ArrayList<Location> lst = DBLocationDAO.getAllLocations();

        //enable suggestions
        boolean suggestions = PreferenceManager.getDefaultSharedPreferences(MigraineTriggerApplication.getAppContext()).getBoolean("pref_suggestions", false);
        if (lst.size() > 0 && suggestions) {
            return getReOrderedLst(lst);
        }
        return lst;
    }


    private static ArrayList<Location> getReOrderedLst(ArrayList<Location> lst) {
        Log.d("LocationController", "getReOrderedLst ");
        ArrayList<String> topLst = AppUtil.getTopList("Location");

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

    private static ArrayList<Location> reorderLst(ArrayList<Location> lst, String match) {
        Log.d("LocationController", "reorderLst ");
        int pos = -1;
        for (int itr = 0; itr < lst.size(); itr++) {
            if (lst.get(itr).getLocationName().trim().equals(match.trim())) {//get position of element
                pos = itr;
                break;
            }
        }
        if (pos > -1 && pos < lst.size()) {
            Location tmp = lst.remove(pos);//remove and insert at beginning
            lst.add(0, tmp);
        } else {
            Log.e("LocationController", "reorderLst - top lst item not found at index " + 0);
        }
        return lst;
    }

    public static int getLastRecordId() {
        return DBLocationDAO.getLastRecordId();
    }

    public static Location getLocationById(int id) {
        return DBLocationDAO.getLocation(id);
    }

    public static long updateLocationRecord(Location location) {
        return DBLocationDAO.updateLocationRecord(location);

    }
}
