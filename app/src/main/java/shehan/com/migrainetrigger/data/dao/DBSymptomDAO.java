package shehan.com.migrainetrigger.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

import shehan.com.migrainetrigger.data.builders.SymptomBuilder;
import shehan.com.migrainetrigger.data.model.Symptom;
import shehan.com.migrainetrigger.utility.database.DatabaseDefinition;
import shehan.com.migrainetrigger.utility.database.DatabaseHandler;

/**
 * Created by Shehan on 4/13/2016.
 */
public final class DBSymptomDAO {

    public static ArrayList<Symptom> getAllSymptoms() {
        Log.d("DBSymptomDAO", " DB - getAllSymptoms ");
        ArrayList<Symptom> symptomArrayList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            cursor = db.query(DatabaseDefinition.SYMPTOM_TABLE, null, null, null, null, null, null);
            if (cursor!=null && cursor.moveToFirst()) {// If records are found process them
                do {

                    Symptom relief = new SymptomBuilder()
                            .setSymptomId(cursor.getInt(0))
                            .setSymptomName(cursor.getString(1))
                            .setPriority(cursor.getInt(2))
                            .createSymptom();
                    symptomArrayList.add(relief);
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
        return symptomArrayList;
    }

    public static long addSymptomRecord(int symptomId, int recordId) {
        Log.d("DBSymptomDAO", "DB - addSymptomRecord");

        if (symptomId <= 0 || recordId <= 0) {
            Log.e("DAO-add", "invalid information");
            return -1;
        }

        SQLiteDatabase db = DatabaseHandler.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();

            values.put(DatabaseDefinition.SYMPTOM_RECORD_SYMPTOM_ID_KEY, symptomId);

            values.put(DatabaseDefinition.SYMPTOM_RECORD_RECORD_ID_KEY, recordId);

            long row_id = db.insert(DatabaseDefinition.SYMPTOM_RECORD_TABLE, null, values);

            Log.d("DAO-add", "result : " + row_id);

            return row_id;
        } catch (SQLiteException e) {

            e.printStackTrace();
            return -1;
        } finally {
            db.close();
        }
    }

    public static long addSymptomRecord(SQLiteDatabase db, int symptomId, int recordId) throws SQLiteException {
        Log.d("DBSymptomDAO", "DB - addSymptomRecord");

        if (symptomId <= 0 || recordId <= 0) {
            Log.e("DAO-add", "invalid information");
            return -1;
        }

        ContentValues values = new ContentValues();

        values.put(DatabaseDefinition.SYMPTOM_RECORD_SYMPTOM_ID_KEY, symptomId);

        values.put(DatabaseDefinition.SYMPTOM_RECORD_RECORD_ID_KEY, recordId);

        long row_id = db.insert(DatabaseDefinition.SYMPTOM_RECORD_TABLE, null, values);

        Log.d("DAO-add", "result : " + row_id);

        return row_id;

    }

    public static int[] getSymptomsForRecord(int recordId) {


        return null;
    }

    public static Symptom getSymptom(int id) {

        return null;
    }
}
