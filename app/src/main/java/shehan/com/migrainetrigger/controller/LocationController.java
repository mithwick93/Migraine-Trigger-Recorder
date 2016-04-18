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
        return null;
    }

    /**
     *
     * @return
     */
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
}
