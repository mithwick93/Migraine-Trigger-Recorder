package shehan.com.migrainetrigger.controller;

import android.util.Log;

import java.util.ArrayList;

import shehan.com.migrainetrigger.data.dao.DBLocationDAO;
import shehan.com.migrainetrigger.data.model.Location;
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

    public static void addNewLocation(Location location) {

    }

    public static long deleteLocation(int id) {
        return DBLocationDAO.deleteLocation(id);
    }

    public static AnswerSectionViewData[] getAnswerSectionViewData() {
        ArrayList<Location> lst = getAllLocations();
        AnswerSectionViewData[] answerSectionViewData = new AnswerSectionViewData[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Location location = lst.get(i);

            answerSectionViewData[i] = new AnswerSectionViewData(location.getLocationId(), location.getLocationName());
        }
        return answerSectionViewData;
    }

    public static ArrayList<Location> getAllLocations() {
        Log.d("LocationController", " getAllLocations ");
        return DBLocationDAO.getAllLocations();
    }

    public static int getLastRecordId() {
        return DBLocationDAO.getLastRecordId();
    }

    public static Location getLocationById(int id) {
        return DBLocationDAO.getLocation(id);
    }

    public static void reorderPriority(Location location) {

    }

    public static long updateLocationRecord(Location location) {
        return DBLocationDAO.updateLocationRecord(location);

    }
}
