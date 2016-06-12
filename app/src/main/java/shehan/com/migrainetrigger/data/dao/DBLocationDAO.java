package shehan.com.migrainetrigger.data.dao;

import android.content.ContentValues;
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
     * add Location
     *
     * @param location location
     * @return affected no of rows
     */
    public static long addLocation(Location location) {
        Log.d("DBLocationDAO", "DB - addLocation");
        try (SQLiteDatabase db = DatabaseHandler.getWritableDatabase()) {

            ContentValues values = new ContentValues();

            values.put(DatabaseDefinition.LOCATION_ID_KEY, location.getLocationId());

            values.put(DatabaseDefinition.LOCATION_NAME_KEY, location.getLocationName());

            long row_id = db.insert(DatabaseDefinition.LOCATION_TABLE, null, values);

            return row_id;
        } catch (SQLiteException e) {

            e.printStackTrace();
            return -1;
        }
    }

    /**
     * delete Location
     *
     * @param id id
     * @return affected no of rows
     */
    public static long deleteLocation(int id) {
        Log.d("DBLocationDAO", "deleteLocation");
        try (SQLiteDatabase db = DatabaseHandler.getWritableDatabase()) {
            if (id == 0) {
                Log.e("DBLocationDAO", "attempting to delete readonly nil value");
                return -1;
            }
            long row_id = db.delete(DatabaseDefinition.LOCATION_TABLE, DatabaseDefinition.LOCATION_ID_KEY + " = ?", new String[]{String.valueOf(id)});
            return row_id;
        } catch (SQLiteException e) {

            e.printStackTrace();
            return -1;
        }
    }

    /**
     * delete Location
     *
     * @param db SQLiteDatabase
     * @param id id
     * @return affected no of rows
     */
    public static long deleteLocation(SQLiteDatabase db, int id) {
        Log.d("DBLocationDAO", "deleteLocation");

        if (id == 0) {
            Log.e("DBLocationDAO", "attempting to delete readonly nil value");
            return -1;
        }

        long row_id = db.delete(DatabaseDefinition.LOCATION_TABLE, DatabaseDefinition.LOCATION_ID_KEY + " = ?", new String[]{String.valueOf(id)});
        return row_id;
    }

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
        if (locationArrayList.size() > 0) {
            locationArrayList.remove(0);
        }
        return locationArrayList;
    }

    /**
     * get Last Record Id
     *
     * @return last location record id
     */
    public static int getLastRecordId() {
        Log.d("DBLocationDAO", "getLastRecordId");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        int recordId = -1;
        try {
            String[] projection = {DatabaseDefinition.LOCATION_ID_KEY};
            db = DatabaseHandler.getReadableDatabase();
            cursor = db.query(
                    DatabaseDefinition.LOCATION_TABLE,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    DatabaseDefinition.LOCATION_ID_KEY + " DESC",
                    "1");

            if (cursor != null && cursor.moveToFirst() && cursor.getString(0) != null) {// If records are found process them
                recordId = Integer.valueOf(cursor.getString(0));
                Log.d("getLastRecordId ", "Value: " + String.valueOf(recordId));
            } else {
                Log.d("getLastRecordId ", "Empty");
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
        if (recordId < 0) {
            recordId = 0;
        }
        return recordId;
    }

    /**
     * get Location
     *
     * @param id id
     * @return Location
     */
    @Nullable
    public static Location getLocation(int id) {
        Log.d("DBLocationDAO", "getLocation");

        if (id == 0) {
            Log.e("DBLocationDAO", "Trying to get nil value");
            return null;
        }
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
                            " WHERE location.location_id != 0 AND " + rightTable + "." + filter + " BETWEEN ? AND ? " +
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

    /**
     * update Location Record
     *
     * @param location location
     * @return no of effected rows
     */
    public static long updateLocationRecord(Location location) {
        Log.d("DBLocationDAO", "DB - updateLocationRecord");

        if (location == null) {
            Log.e("DBLocationDAO", "null location");
            return -1;
        }
        if (location.getLocationId() == 0) {
            Log.e("DBLocationDAO", "Trying to update nil value");
            return -1;
        }

        try (SQLiteDatabase db = DatabaseHandler.getWritableDatabase()) {

            ContentValues values = new ContentValues();

            if (location.getLocationName().equals("")) {
                throw new SQLiteException("Empty update");
            } else {
                values.put(DatabaseDefinition.LOCATION_NAME_KEY, location.getLocationName());
            }


            long result = db.update(
                    DatabaseDefinition.LOCATION_TABLE,
                    values,
                    DatabaseDefinition.LOCATION_ID_KEY + " = ?",
                    new String[]{String.valueOf(location.getLocationId())}
            );

            return result;
        } catch (SQLiteException e) {

            e.printStackTrace();
            return -1;
        }
    }
}
