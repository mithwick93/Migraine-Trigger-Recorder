package shehan.com.migrainetrigger.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

import shehan.com.migrainetrigger.data.builders.ReliefBuilder;
import shehan.com.migrainetrigger.data.model.Relief;
import shehan.com.migrainetrigger.utility.database.DatabaseDefinition;
import shehan.com.migrainetrigger.utility.database.DatabaseHandler;

/**
 * Created by Shehan on 4/13/2016.
 */
public final class DBReliefDAO {
    public static ArrayList<Relief> getAllReliefs() {
        Log.d("DBReliefDAO", " DB - getAllReliefs ");
        ArrayList<Relief> reliefArrayList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            cursor = db.query(DatabaseDefinition.RELIEF_TABLE, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {// If records are found process them
                do {

                    Relief relief = new ReliefBuilder()
                            .setReliefId(cursor.getInt(0))
                            .setReliefName(cursor.getString(1))
                            .setPriority(cursor.getInt(2))
                            .createRelief();
                    reliefArrayList.add(relief);
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
        return reliefArrayList;
    }

    public static long addReliefRecord(int reliefId, int recordId, boolean effective) {
        Log.d("DBReliefDAO", "DB - addReliefRecord");

        if (reliefId <= 0 || recordId <= 0) {
            Log.e("DAO-add", "invalid information");
            return -1;
        }

        SQLiteDatabase db = DatabaseHandler.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();

            values.put(DatabaseDefinition.RELIEF_RECORD_RELIEF_ID_KEY, reliefId);

            values.put(DatabaseDefinition.RELIEF_RECORD_RECORD_ID_KEY, recordId);

            values.put(DatabaseDefinition.RELIEF_RECORD_EFFECTIVE_KEY, effective ? "t" : "f");

            long row_id = db.insert(DatabaseDefinition.RELIEF_RECORD_TABLE, null, values);

            Log.d("DAO-add", "result : " + row_id);

            return row_id;
        } catch (SQLiteException e) {

            e.printStackTrace();
            return -1;
        } finally {
            db.close();
        }
    }

    public static long addReliefRecord(SQLiteDatabase db, int reliefId, int recordId, boolean effective) throws SQLiteException {
        Log.d("DBReliefDAO", "DB - addReliefRecord");

        if (reliefId <= 0 || recordId <= 0) {
            Log.e("DAO-add", "invalid information");
            return -1;
        }

        ContentValues values = new ContentValues();

        values.put(DatabaseDefinition.RELIEF_RECORD_RELIEF_ID_KEY, reliefId);

        values.put(DatabaseDefinition.RELIEF_RECORD_RECORD_ID_KEY, recordId);

        values.put(DatabaseDefinition.RELIEF_RECORD_EFFECTIVE_KEY, effective ? "t" : "f");

        long row_id = db.insert(DatabaseDefinition.RELIEF_RECORD_TABLE, null, values);

        Log.d("DAO-add", "result : " + row_id);

        return row_id;

    }

    public static int[] getReliefsForRecord(int recordId) {


        return null;
    }

    public static Relief getRelief(int id) {
        Log.d("DBReliefDAO", "getRelief");

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            cursor = db.query(DatabaseDefinition.RELIEF_TABLE, null, DatabaseDefinition.RELIEF_ID_KEY + " = ?", new String[]{String.valueOf(id)}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {// If records are found process them


                int reliefId = cursor.getInt(0);

                ReliefBuilder reliefBuilder = new ReliefBuilder().setReliefId(reliefId);

                int index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RELIEF_NAME_KEY);

                if (!cursor.isNull(index)) {
                    String name = cursor.getString(index);
                    reliefBuilder = reliefBuilder.setReliefName(name);
                }

                index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RELIEF_PRIORITY_KEY);

                if (!cursor.isNull(index)) {
                    String priority = cursor.getString(index);
                    reliefBuilder = reliefBuilder.setPriority(Integer.parseInt(priority));
                }

                return reliefBuilder.createRelief();
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
