package shehan.com.migrainetrigger.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import shehan.com.migrainetrigger.data.builders.TriggerBuilder;
import shehan.com.migrainetrigger.data.model.Trigger;
import shehan.com.migrainetrigger.utility.database.DatabaseDefinition;
import shehan.com.migrainetrigger.utility.database.DatabaseHandler;

/**
 * Created by Shehan on 4/13/2016.
 */
public final class DBTriggerDAO {

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
}
