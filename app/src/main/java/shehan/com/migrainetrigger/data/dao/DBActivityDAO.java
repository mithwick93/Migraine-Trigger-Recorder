package shehan.com.migrainetrigger.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
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

    public static long addActivityRecord(int activityId, int recordId) {
        Log.d("DBActivityDAO", "DB - addActivityRecord");

        if (activityId <= 0 || recordId <= 0) {
            Log.e("DAO-add", "invalid information");
            return -1;
        }

        SQLiteDatabase db = DatabaseHandler.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();

            values.put(DatabaseDefinition.ACTIVITY_RECORD_ACTIVITY_ID_KEY, activityId);

            values.put(DatabaseDefinition.ACTIVITY_RECORD_RECORD_ID_KEY, recordId);

            long row_id = db.insert(DatabaseDefinition.ACTIVITY_RECORD_TABLE, null, values);

            Log.d("DAO-add", "result : " + row_id);

            return row_id;
        } catch (SQLiteException e) {

            e.printStackTrace();
            return -1;
        } finally {
            db.close();
        }
    }

    public static long addActivityRecord(SQLiteDatabase db, int activityId, int recordId) throws SQLiteException {
        Log.d("DBActivityDAO", "DB - addActivityRecord");

        if (activityId <= 0 || recordId <= 0) {
            Log.e("DAO-add", "invalid information");
            return -1;
        }

        ContentValues values = new ContentValues();

        values.put(DatabaseDefinition.ACTIVITY_RECORD_ACTIVITY_ID_KEY, activityId);

        values.put(DatabaseDefinition.ACTIVITY_RECORD_RECORD_ID_KEY, recordId);

        long row_id = db.insert(DatabaseDefinition.ACTIVITY_RECORD_TABLE, null, values);

        Log.d("DAO-add", "result : " + row_id);

        return row_id;

    }

    public static int[] getActivitiesForRecord(int id) {


        return null;
    }

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


}
