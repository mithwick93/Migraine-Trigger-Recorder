package shehan.com.migrainetrigger.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.sql.Timestamp;
import java.util.ArrayList;

import shehan.com.migrainetrigger.data.builders.TriggerBuilder;
import shehan.com.migrainetrigger.data.model.Trigger;
import shehan.com.migrainetrigger.utility.database.DatabaseDefinition;
import shehan.com.migrainetrigger.utility.database.DatabaseHandler;

import static shehan.com.migrainetrigger.utility.AppUtil.getStringDate;

/**
 * Created by Shehan on 4/13/2016.
 */
public final class DBTriggerDAO {

    /**
     * add Trigger
     *
     * @param trigger trigger
     * @return affected no of rows
     */
    public static long addTrigger(Trigger trigger) {
        Log.d("DBTriggerDAO", "DB - addTrigger");
        try (SQLiteDatabase db = DatabaseHandler.getWritableDatabase()) {

            ContentValues values = new ContentValues();

            values.put(DatabaseDefinition.TRIGGER_ID_KEY, trigger.getTriggerId());

            values.put(DatabaseDefinition.TRIGGER_NAME_KEY, trigger.getTriggerName());

            values.put(DatabaseDefinition.TRIGGER_PRIORITY_KEY, trigger.getPriority());

            long row_id = db.insert(DatabaseDefinition.TRIGGER_TABLE, null, values);

            return row_id;
        } catch (SQLiteException e) {

            e.printStackTrace();
            return -1;
        }
    }

    /**
     * add Trigger Record
     *
     * @param triggerId triggerId
     * @param recordId  recordId
     * @return raw id
     */
    public static long addTriggerRecord(int triggerId, int recordId) {
        Log.d("DBTriggerDAO", "DB - addTriggerRecord");

//        if (triggerId <= 0 || recordId <= 0) {
//            Log.e("DAO-add", "invalid information");
//            return -1;
//        }

        try (SQLiteDatabase db = DatabaseHandler.getWritableDatabase()) {

            ContentValues values = new ContentValues();

            values.put(DatabaseDefinition.TRIGGER_RECORD_TRIGGER_ID_KEY, triggerId);

            values.put(DatabaseDefinition.TRIGGER_RECORD_RECORD_ID_KEY, recordId);

            long row_id = db.insert(DatabaseDefinition.TRIGGER_RECORD_TABLE, null, values);

            return row_id;
        } catch (SQLiteException e) {

            e.printStackTrace();
            return -1;
        }
    }

    /**
     * add Trigger Record
     *
     * @param db        SQLiteDatabase
     * @param triggerId triggerId
     * @param recordId  recordId
     * @return raw id
     * @throws SQLiteException
     */
    public static long addTriggerRecord(SQLiteDatabase db, int triggerId, int recordId) throws SQLiteException {
        Log.d("DBTriggerDAO", "DB - addTriggerRecord");
//
//        if (triggerId <= 0 || recordId < 0) {
//            Log.e("DAO-add", "invalid information");
//            return -1;
//        }

        ContentValues values = new ContentValues();

        values.put(DatabaseDefinition.TRIGGER_RECORD_TRIGGER_ID_KEY, triggerId);

        values.put(DatabaseDefinition.TRIGGER_RECORD_RECORD_ID_KEY, recordId);

        long row_id = db.insert(DatabaseDefinition.TRIGGER_RECORD_TABLE, null, values);

        return row_id;

    }

    /**
     * delete Trigger
     *
     * @param id id
     * @return affected no of rows
     */
    public static long deleteTrigger(int id) {
        Log.d("DBTriggerDAO", "deleteTrigger");
        try (SQLiteDatabase db = DatabaseHandler.getWritableDatabase()) {

            long row_id = db.delete(DatabaseDefinition.TRIGGER_TABLE, DatabaseDefinition.TRIGGER_ID_KEY + " = ?", new String[]{String.valueOf(id)});
            return row_id;
        } catch (SQLiteException e) {

            e.printStackTrace();
            return -1;
        }
    }

    /**
     * delete Symptom Records
     *
     * @param db       SQLiteDatabase
     * @param recordId recordId
     * @return deleted no of rows
     */
    public static long deleteTriggerRecords(SQLiteDatabase db, int recordId) {
        Log.d("DBTriggerDAO", "DB - deleteTriggerRecords");

        long row_id = db.delete(DatabaseDefinition.TRIGGER_RECORD_TABLE, DatabaseDefinition.TRIGGER_RECORD_RECORD_ID_KEY + " = ?", new String[]{String.valueOf(recordId)});
        return row_id;
    }

    /**
     * get All Triggers
     *
     * @return ArrayList<Trigger>
     */
    public static ArrayList<Trigger> getAllTriggers() {
        Log.d("DBTriggerDAO", " DB - getAllSymptoms ");
        ArrayList<Trigger> triggerArrayList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            cursor = db.query(DatabaseDefinition.TRIGGER_TABLE, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {// If records are found process them
                do {

                    Trigger relief = new TriggerBuilder()
                            .setTriggerId(cursor.getInt(0))
                            .setTriggerName(cursor.getString(1))
                            .setPriority(cursor.getInt(2))
                            .createTrigger();
                    triggerArrayList.add(relief);
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
        return triggerArrayList;
    }

    /**
     * get Last Record Id
     *
     * @return last trigger record id
     */
    public static int getLastRecordId() {
        Log.d("DBTriggerDAO", "getLastRecordId");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        int recordId = -1;
        try {
            String[] projection = {DatabaseDefinition.TRIGGER_ID_KEY};
            db = DatabaseHandler.getReadableDatabase();
            cursor = db.query(
                    DatabaseDefinition.TRIGGER_TABLE,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    DatabaseDefinition.TRIGGER_ID_KEY + " DESC",
                    "1");

            if (cursor != null && cursor.moveToFirst()) {// If records are found process them
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
     * get Top Triggers
     *
     * @param from  from date
     * @param to    to date
     * @param limit limit
     * @return ArrayList<String>
     */
    public static ArrayList<String> getTopTriggers(Timestamp from, Timestamp to, int limit) {
        Log.d("DBTriggerDAO", "getTopTriggers");

        ArrayList<String> top = new ArrayList<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            String topic = DatabaseDefinition.TRIGGER_NAME_KEY;//Dynamic
            String topicId = DatabaseDefinition.TRIGGER_ID_KEY;//Dynamic

            String rightTable = DatabaseDefinition.RECORD_TABLE;
            String leftTable = DatabaseDefinition.TRIGGER_TABLE;//Dynamic
            String middleTable = DatabaseDefinition.TRIGGER_RECORD_TABLE;//Dynamic

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
     * get Trigger
     *
     * @param id id
     * @return Trigger
     */
    @Nullable
    public static Trigger getTrigger(int id) {
        Log.d("DBTriggerDAO", "getTrigger");

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            cursor = db.query(DatabaseDefinition.TRIGGER_TABLE, null, DatabaseDefinition.TRIGGER_ID_KEY + " = ?", new String[]{String.valueOf(id)}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {// If records are found process them


                int triggerId = cursor.getInt(0);

                TriggerBuilder triggerBuilder = new TriggerBuilder().setTriggerId(triggerId);

                int index = cursor.getColumnIndexOrThrow(DatabaseDefinition.TRIGGER_NAME_KEY);

                if (!cursor.isNull(index)) {
                    String name = cursor.getString(index);
                    triggerBuilder = triggerBuilder.setTriggerName(name);
                }

                index = cursor.getColumnIndexOrThrow(DatabaseDefinition.TRIGGER_PRIORITY_KEY);

                if (!cursor.isNull(index)) {
                    String priority = cursor.getString(index);
                    triggerBuilder = triggerBuilder.setPriority(Integer.parseInt(priority));
                }

                return triggerBuilder.createTrigger();
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
     * get Triggers For Record
     *
     * @param recordId recordId
     * @return ArrayList<Trigger>
     */
    public static ArrayList<Trigger> getTriggersForRecord(int recordId) {
        Log.d("DBTriggerDAO", "getTriggersForRecord");

        ArrayList<Trigger> triggers = new ArrayList<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            String joinQuery =
                    "SELECT * FROM " + DatabaseDefinition.TRIGGER_TABLE + " a INNER JOIN " + DatabaseDefinition.TRIGGER_RECORD_TABLE +
                            " r USING(" + DatabaseDefinition.TRIGGER_ID_KEY + ") WHERE r.record_id=?";

            cursor = db.rawQuery(joinQuery, new String[]{String.valueOf(recordId)});

            if (cursor != null && cursor.moveToFirst()) {// If records are found process them
                do {

                    int triggerId = cursor.getInt(0);

                    TriggerBuilder triggerBuilder = new TriggerBuilder().setTriggerId(triggerId);

                    int index = cursor.getColumnIndexOrThrow(DatabaseDefinition.TRIGGER_NAME_KEY);

                    if (!cursor.isNull(index)) {
                        String name = cursor.getString(index);
                        triggerBuilder = triggerBuilder.setTriggerName(name);
                    }

                    index = cursor.getColumnIndexOrThrow(DatabaseDefinition.TRIGGER_PRIORITY_KEY);

                    if (!cursor.isNull(index)) {
                        String priority = cursor.getString(index);
                        triggerBuilder = triggerBuilder.setPriority(Integer.parseInt(priority));
                    }

                    triggers.add(triggerBuilder.createTrigger());
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

        return triggers;
    }

    /**
     * update Trigger Record
     *
     * @param trigger trigger
     * @return no of effected rows
     */
    public static long updateTriggerRecord(Trigger trigger) {
        Log.d("DBTriggerDAO", "DB - updateTriggerRecord");
        try (SQLiteDatabase db = DatabaseHandler.getWritableDatabase()) {

            ContentValues values = new ContentValues();

            if (trigger.getTriggerName().equals("") && trigger.getPriority() < 0) {
                throw new SQLiteException("Empty update");
            }

            if (!trigger.getTriggerName().equals("")) {
                values.put(DatabaseDefinition.TRIGGER_NAME_KEY, trigger.getTriggerName());
            }
            if (trigger.getPriority() >= 0) {
                values.put(DatabaseDefinition.TRIGGER_PRIORITY_KEY, trigger.getPriority());
            }


            long result = db.update(
                    DatabaseDefinition.TRIGGER_TABLE,
                    values,
                    DatabaseDefinition.TRIGGER_ID_KEY + " = ?",
                    new String[]{String.valueOf(trigger.getTriggerId())}
            );

            return result;
        } catch (SQLiteException e) {

            e.printStackTrace();
            return -1;
        }
    }
}
