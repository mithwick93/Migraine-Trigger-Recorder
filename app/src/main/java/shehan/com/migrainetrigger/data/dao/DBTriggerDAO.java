package shehan.com.migrainetrigger.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
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

    public static ArrayList<Trigger> getAllTriggers() {
        Log.d("getAll", " DB - getAllSymptoms ");
        ArrayList<Trigger> triggerArrayList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            cursor = db.query(DatabaseDefinition.TRIGGER_TABLE, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {// If records are found process them
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

    public static long addTriggerRecord(int triggerId, int recordId) {
        Log.d("DAO-add", "DB - addTriggerRecord");

        if (triggerId <= 0 || recordId <= 0) {
            Log.e("DAO-add", "invalid information");
            return -1;
        }

        SQLiteDatabase db = DatabaseHandler.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();

            values.put(DatabaseDefinition.TRIGGER_RECORD_TRIGGER_ID_KEY, triggerId);

            values.put(DatabaseDefinition.TRIGGER_RECORD_RECORD_ID_KEY, recordId);

            long row_id = db.insert(DatabaseDefinition.TRIGGER_RECORD_TABLE, null, values);

            Log.d("DAO-add-", "result : " + row_id);

            return row_id;
        } catch (SQLiteException e) {

            e.printStackTrace();
            return -1;
        } finally {
            db.close();
        }
    }

    public static long addTriggerRecord(SQLiteDatabase db, int triggerId, int recordId) throws SQLiteException {
        Log.d("DAO-add", "DB - addTriggerRecord");

        if (triggerId <= 0 || recordId <= 0) {
            Log.e("DAO-add", "invalid information");
            return -1;
        }

        ContentValues values = new ContentValues();

        values.put(DatabaseDefinition.TRIGGER_RECORD_TRIGGER_ID_KEY, triggerId);

        values.put(DatabaseDefinition.TRIGGER_RECORD_RECORD_ID_KEY, recordId);

        long row_id = db.insert(DatabaseDefinition.TRIGGER_RECORD_TABLE, null, values);

        Log.d("DAO-add-", "result : " + row_id);

        return row_id;

    }

    public static int[] getTriggersForRecord(int recordId) {


        return null;
    }

    public static Trigger getTrigger(int id) {

        return null;
    }
}
