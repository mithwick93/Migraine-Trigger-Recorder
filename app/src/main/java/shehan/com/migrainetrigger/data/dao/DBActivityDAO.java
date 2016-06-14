package shehan.com.migrainetrigger.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.sql.Timestamp;
import java.util.ArrayList;

import shehan.com.migrainetrigger.data.builders.ActivityBuilder;
import shehan.com.migrainetrigger.data.model.LifeActivity;
import shehan.com.migrainetrigger.utility.database.DatabaseDefinition;
import shehan.com.migrainetrigger.utility.database.DatabaseHandler;

import static shehan.com.migrainetrigger.utility.AppUtil.getStringDate;

/**
 * Created by Shehan on 4/13/2016.
 */
public final class DBActivityDAO {

    /**
     * add Activity
     *
     * @param lifeActivity lifeActivity
     * @return affected no of rows
     */
    public static long addActivity(LifeActivity lifeActivity) {
        Log.d("DBActivityDAO", "DB - addActivity");
        try (SQLiteDatabase db = DatabaseHandler.getWritableDatabase()) {

            ContentValues values = new ContentValues();

            values.put(DatabaseDefinition.ACTIVITY_ID_KEY, lifeActivity.getActivityId());

            values.put(DatabaseDefinition.ACTIVITY_NAME_KEY, lifeActivity.getActivityName());

            values.put(DatabaseDefinition.ACTIVITY_PRIORITY_KEY, lifeActivity.getPriority());

            return db.insert(DatabaseDefinition.ACTIVITY_TABLE, null, values);
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

        ContentValues values = new ContentValues();

        values.put(DatabaseDefinition.ACTIVITY_RECORD_ACTIVITY_ID_KEY, activityId);

        values.put(DatabaseDefinition.ACTIVITY_RECORD_RECORD_ID_KEY, recordId);

        return db.insert(DatabaseDefinition.ACTIVITY_RECORD_TABLE, null, values);

    }


    /**
     * delete Activity
     *
     * @param db SQLiteDatabase
     * @param id id
     * @return affected no of rows
     */
    public static long deleteActivity(SQLiteDatabase db, int id) {
        Log.d("DBActivityDAO", "deleteActivity");

        return (long) db.delete(DatabaseDefinition.ACTIVITY_TABLE, DatabaseDefinition.ACTIVITY_ID_KEY + " = ?", new String[]{String.valueOf(id)});
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

        return (long) db.delete(DatabaseDefinition.ACTIVITY_RECORD_TABLE, DatabaseDefinition.ACTIVITY_RECORD_RECORD_ID_KEY + " = ?", new String[]{String.valueOf(recordId)});
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
     * get Last Record Id
     *
     * @return last activity record id
     */
    public static int getLastRecordId() {
        Log.d("DBActivityDAO", "getLastRecordId");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        int recordId = -1;
        try {
            String[] projection = {DatabaseDefinition.ACTIVITY_ID_KEY};
            db = DatabaseHandler.getReadableDatabase();
            cursor = db.query(
                    DatabaseDefinition.ACTIVITY_TABLE,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    DatabaseDefinition.ACTIVITY_ID_KEY + " DESC",
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
        return recordId;
    }

    /**
     * get Top Activities
     *
     * @param from  from date
     * @param to    to date
     * @param limit limit
     * @return ArrayList<String>
     */
    public static ArrayList<String> getTopActivities(Timestamp from, Timestamp to, int limit) {
        Log.d("DBActivityDAO", "getTopActivities");

        ArrayList<String> top = new ArrayList<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            String topic = DatabaseDefinition.ACTIVITY_NAME_KEY;//Dynamic
            String topicId = DatabaseDefinition.ACTIVITY_ID_KEY;//Dynamic

            String rightTable = DatabaseDefinition.RECORD_TABLE;
            String leftTable = DatabaseDefinition.ACTIVITY_TABLE;//Dynamic
            String middleTable = DatabaseDefinition.ACTIVITY_RECORD_TABLE;//Dynamic

            String rightId = DatabaseDefinition.RECORD_ID_KEY;

            String filter = DatabaseDefinition.RECORD_START_TIME_KEY;

            String joinQuery =
                    "SELECT " + topic + ", COUNT(" + topicId + ") as topCount " +
                            "FROM " + leftTable + " NATURAL JOIN " + middleTable + ", " + rightTable +
                            " WHERE " + middleTable + "." + rightId + " = " + rightTable + "." + rightId +
                            " AND " + rightTable + "." + filter + " BETWEEN ? AND ? " +
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
     * update Activity Record
     *
     * @param lifeActivity lifeActivity
     * @return no of effected rows
     */
    public static long updateActivityRecord(LifeActivity lifeActivity) {
        Log.d("DBActivityDAO", "DB - updateActivityRecord");
        try (SQLiteDatabase db = DatabaseHandler.getWritableDatabase()) {

            ContentValues values = new ContentValues();

            if (lifeActivity.getActivityName().equals("") && lifeActivity.getPriority() < 0) {
                throw new SQLiteException("Empty update");
            }

            if (!lifeActivity.getActivityName().equals("")) {
                values.put(DatabaseDefinition.ACTIVITY_NAME_KEY, lifeActivity.getActivityName());
            }
            if (lifeActivity.getPriority() >= 0) {
                values.put(DatabaseDefinition.ACTIVITY_PRIORITY_KEY, lifeActivity.getPriority());
            }


            return (long) db.update(
                    DatabaseDefinition.ACTIVITY_TABLE,
                    values,
                    DatabaseDefinition.ACTIVITY_ID_KEY + " = ?",
                    new String[]{String.valueOf(lifeActivity.getActivityId())}
            );
        } catch (SQLiteException e) {

            e.printStackTrace();
            return -1;
        }
    }
}
