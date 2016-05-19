package shehan.com.migrainetrigger.controller;

import android.util.Log;

import java.util.ArrayList;

import shehan.com.migrainetrigger.data.dao.DBLocationDAO;
import shehan.com.migrainetrigger.data.model.Location;

/**
 * Created by Shehan on 4/13/2016.
 */
public class LocationController {

    public static void addNewLocation(Location location) {

    }

    public static Location getLocationById(int id) {
        return DBLocationDAO.getLocation(id);
    }

    public static ArrayList<Location> getAllLocations() {
        Log.d("LocationController", " getAllLocations ");
        return DBLocationDAO.getAllLocations();
    }

    public static void deleteLocation() {

    }

    public static void updateLocation(Location location) {

    }

    public static void reorderPriority(Location location) {

    }

    public static long addLocation(Location location) {
        return DBLocationDAO.addLocation(location);
    }

    public static long addLocations(ArrayList<Location> lst) {
        for (Location itm : lst) {
            long result = addLocation(itm);
            if (result < 1) {
                return 0;
            }
        }
        return lst.size();
    }

    public static long deleteLocation(int id) {
        return DBLocationDAO.deleteLocation(id);
    }
}
