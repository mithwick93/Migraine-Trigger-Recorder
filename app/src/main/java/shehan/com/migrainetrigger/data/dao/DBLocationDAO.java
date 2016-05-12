package shehan.com.migrainetrigger.data.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.sql.Timestamp;
import java.util.ArrayList;

import shehan.com.migrainetrigger.data.builders.LocationBuilder;
import shehan.com.migrainetrigger.data.model.Location;
import shehan.com.migrainetrigger.utility.database.DatabaseDefinition;
import shehan.com.migrainetrigger.utility.database.DatabaseHandler;

import static shehan.com.migrainetrigger.utility.AppUtil.getStringDate;

/**
 * Created by Shehan on 4/13/2016.
 */
public final class DBLocationDAO {

    /**
     * get All Locations
     *
     * @return ArrayList<Location>
     */
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

    /**
     * ge tLocation
     *
     * @param id id
     * @return Location
     */
    @Nullable
    public static Location getLocation(int id) {
        Log.d("DBLocationDAO", "getLocation");

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            cursor = db.query(DatabaseDefinition.LOCATION_TABLE, null, DatabaseDefinition.LOCATION_ID_KEY + " = ?", new String[]{String.valueOf(id)}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {// If records are found process them


                int locationId = cursor.getInt(0);

                LocationBuilder locationBuilder = new LocationBuilder().setLocationId(locationId);

                int index = cursor.getColumnIndexOrThrow(DatabaseDefinition.LOCATION_NAME_KEY);

                if (!cursor.isNull(index)) {
                    String name = cursor.getString(index);
                    locationBuilder = locationBuilder.setLocationName(name);
                }


                return locationBuilder.createLocation();
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
        return null;
    }

    /**
     * get Top Locations
     *
     * @param from  from date
     * @param to    to date
     * @param limit limit
     * @return ArrayList<String>
     */
    public static ArrayList<String> getTopLocations(Timestamp from, Timestamp to, int limit) {
        Log.d("DBLocationDAO", "getTopLocations");

        ArrayList<String> top = new ArrayList<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            String topic = DatabaseDefinition.LOCATION_NAME_KEY;//Dynamic
            String topicId = DatabaseDefinition.LOCATION_ID_KEY;//Dynamic

            String rightTable = DatabaseDefinition.RECORD_TABLE;
            String leftTable = DatabaseDefinition.LOCATION_TABLE;//Dynamic

            String rightId = DatabaseDefinition.RECORD_ID_KEY;

            String filter = DatabaseDefinition.RECORD_START_TIME_KEY;

            String joinQuery =
                    "SELECT " + topic + ", COUNT(" + topicId + ") as topCount " +
                            "FROM " + leftTable + " NATURAL JOIN " + rightTable +
                            " WHERE " + rightTable + "." + filter + " BETWEEN ? AND ? " +
                            "GROUP BY " + topic + " ORDER BY topCount DESC LIMIT " + String.valueOf(limit);

            String[] selectionArgs = {getStringDate(from), getStringDate(to)};
            cursor = db.rawQuery(joinQuery, selectionArgs);

            if (cursor != null && cursor.moveToFirst()) {// If records are found process them
                do {
                    String title = cursor.getString(0);
                    top.add(title);
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

        return top;
    }
}
