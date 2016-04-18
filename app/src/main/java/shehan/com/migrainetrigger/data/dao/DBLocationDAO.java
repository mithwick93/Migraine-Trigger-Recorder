package shehan.com.migrainetrigger.data.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

import shehan.com.migrainetrigger.data.builders.LocationBuilder;
import shehan.com.migrainetrigger.data.model.Location;
import shehan.com.migrainetrigger.utility.database.DatabaseDefinition;
import shehan.com.migrainetrigger.utility.database.DatabaseHandler;

/**
 * Created by Shehan on 4/13/2016.
 */
public final class DBLocationDAO {
    public static ArrayList<Location> getAllLocations() {
        Log.d("DBLocationDAO", " DB - getAllLocations ");
        ArrayList<Location> locationArrayList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            cursor = db.query(DatabaseDefinition.LOCATION_TABLE, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {// If records are found process them
                do {

                    Location location = new LocationBuilder()
                            .setLocationId(cursor.getInt(0))
                            .setLocationName(cursor.getString(1))
                            .createLocation();
                    locationArrayList.add(location);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return locationArrayList;
    }

    public static Location getLocation(int id) {

        return null;
    }
}
