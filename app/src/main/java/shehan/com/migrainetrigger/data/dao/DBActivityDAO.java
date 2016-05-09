package shehan.com.migrainetrigger.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import shehan.com.migrainetrigger.data.builders.ActivityBuilder;
import shehan.com.migrainetrigger.data.model.LifeActivity;
import shehan.com.migrainetrigger.utility.database.DatabaseDefinition;
import shehan.com.migrainetrigger.utility.database.DatabaseHandler;

/**
 * Created by Shehan on 4/13/2016.
 */
public final class DBActivityDAO {


    /**
     * Get all activities
     *
     * @return ArrayList<LifeActivity>
     */
    public static ArrayList<LifeActivity> getAllActivities() {
        Log.d("DBActivityDAO", " DB - getAllActivities ");
        ArrayList<LifeActivity> lifeActivityArrayList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            cursor = db.query(DatabaseDefinition.ACTIVITY_TABLE, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {// If records are found process them
                do {

                    LifeActivity lifeActivity = new ActivityBuilder()
                            .setActivityId(cursor.getInt(0))
                            .setActivityName(cursor.getString(1))
                            .setPriority(cursor.getInt(2))
                            .createActivity();
                    lifeActivityArrayList.add(lifeActivity);
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
        return lifeActivityArrayList;
    }

    /**
     * add Activity Record to table
     *
     * @param activityId activityId
     * @param recordId   recordId
     * @return edited raw id
     */
    public static long addActivityRecord(int activityId, int recordId) {
        Log.d("DBActivityDAO", "DB - addActivityRecord");

//        if (activityId <= 0 || recordId <= 0) {
//            Log.e("DAO-add", "invalid information");
//            return -1;
//        }

        try (SQLiteDatabase db = DatabaseHandler.getWritableDatabase()) {

            ContentValues values = new ContentValues();

            values.put(DatabaseDefinition.ACTIVITY_RECORD_ACTIVITY_ID_KEY, activityId);

            values.put(DatabaseDefinition.ACTIVITY_RECORD_RECORD_ID_KEY, recordId);

            long row_id = db.insert(DatabaseDefinition.ACTIVITY_RECORD_TABLE, null, values);

            return row_id;
        } catch (SQLiteException e) {

            e.printStackTrace();
            return -1;
        }
    }

    /**
     * @param db         SQLiteDatabase
     * @param activityId recordId
     * @param recordId   recordId
     * @return edited raw id
     * @throws SQLiteException
     */
    public static long addActivityRecord(SQLiteDatabase db, int activityId, int recordId) throws SQLiteException {
        Log.d("DBActivityDAO", "DB - addActivityRecord");

//        if (activityId <= 0 || recordId < 0) {
//            Log.e("DAO-add", "invalid information");
//            return -1;
//        }

        ContentValues values = new ContentValues();

        values.put(DatabaseDefinition.ACTIVITY_RECORD_ACTIVITY_ID_KEY, activityId);

        values.put(DatabaseDefinition.ACTIVITY_RECORD_RECORD_ID_KEY, recordId);

        long row_id = db.insert(DatabaseDefinition.ACTIVITY_RECORD_TABLE, null, values);

        return row_id;

    }

    /**
     * delete Activity Records
     *
     * @param db       SQLiteDatabase
     * @param recordId recordId
     * @return deleted no of rows
     */
    public static long deleteActivityRecords(SQLiteDatabase db, int recordId) {
        Log.d("DBActivityDAO", "DB - deleteActivityRecords");

        long row_id = db.delete(DatabaseDefinition.ACTIVITY_RECORD_TABLE, DatabaseDefinition.ACTIVITY_RECORD_RECORD_ID_KEY + " = ?", new String[]{String.valueOf(recordId)});
        return row_id;
    }

    /**
     * get Activity
     *
     * @param id id
     * @return LifeActivity
     */
    @Nullable
    public static LifeActivity getActivity(int id) {
        Log.d("DBActivityDAO", "getActivity");

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            cursor = db.query(DatabaseDefinition.ACTIVITY_TABLE, null, DatabaseDefinition.ACTIVITY_ID_KEY + " = ?", new String[]{String.valueOf(id)}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {// If records are found process them


                int activityId = cursor.getInt(0);

                ActivityBuilder activityBuilder = new ActivityBuilder().setActivityId(activityId);

                int index = cursor.getColumnIndexOrThrow(DatabaseDefinition.ACTIVITY_NAME_KEY);

                if (!cursor.isNull(index)) {
                    String name = cursor.getString(index);
                    activityBuilder = activityBuilder.setActivityName(name);
                }

                index = cursor.getColumnIndexOrThrow(DatabaseDefinition.ACTIVITY_PRIORITY_KEY);

                if (!cursor.isNull(index)) {
                    String priority = cursor.getString(index);
                    activityBuilder = activityBuilder.setPriority(Integer.parseInt(priority));
                }

                return activityBuilder.createActivity();
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
     * get Activities For Record
     *
     * @param recordId recordId
     * @return ArrayList<LifeActivity>
     */
    public static ArrayList<LifeActivity> getActivitiesForRecord(int recordId) {
        Log.d("DBActivityDAO", "getActivities");

        ArrayList<LifeActivity> lifeActivities = new ArrayList<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            String joinQuery =
                    "SELECT * FROM " + DatabaseDefinition.ACTIVITY_TABLE + " a INNER JOIN " + DatabaseDefinition.ACTIVITY_RECORD_TABLE +
                            " r USING(" + DatabaseDefinition.ACTIVITY_ID_KEY + ") WHERE r.record_id=?";

            cursor = db.rawQuery(joinQuery, new String[]{String.valueOf(recordId)});

            if (cursor != null && cursor.moveToFirst()) {// If records are found process them
                do {

                    int activityId = cursor.getInt(0);

                    ActivityBuilder activityBuilder = new ActivityBuilder().setActivityId(activityId);

                    int index = cursor.getColumnIndexOrThrow(DatabaseDefinition.ACTIVITY_NAME_KEY);

                    if (!cursor.isNull(index)) {
                        String name = cursor.getString(index);
                        activityBuilder = activityBuilder.setActivityName(name);
                    }

                    index = cursor.getColumnIndexOrThrow(DatabaseDefinition.ACTIVITY_PRIORITY_KEY);

                    if (!cursor.isNull(index)) {
                        String priority = cursor.getString(index);
                        activityBuilder = activityBuilder.setPriority(Integer.parseInt(priority));
                    }

                    lifeActivities.add(activityBuilder.createActivity());
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

        return lifeActivities;
    }


}
