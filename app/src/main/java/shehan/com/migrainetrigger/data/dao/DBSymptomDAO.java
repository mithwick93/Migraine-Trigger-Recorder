package shehan.com.migrainetrigger.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.Nullable;
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

    /**
     * get All Symptoms
     *
     * @return ArrayList<Symptom>
     */
    public static ArrayList<Symptom> getAllSymptoms() {
        Log.d("DBSymptomDAO", " DB - getAllSymptoms ");
        ArrayList<Symptom> symptomArrayList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            cursor = db.query(DatabaseDefinition.SYMPTOM_TABLE, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {// If records are found process them
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

    /**
     * add Symptom Record
     *
     * @param symptomId symptomId
     * @return raw id
     */
    public static long addSymptomRecord(int symptomId, int recordId) {
        Log.d("DBSymptomDAO", "DB - addSymptomRecord");

//        if (symptomId <= 0 || recordId <= 0) {
//            Log.e("DAO-add", "invalid information");
//            return -1;
//        }

        try (SQLiteDatabase db = DatabaseHandler.getWritableDatabase()) {

            ContentValues values = new ContentValues();

            values.put(DatabaseDefinition.SYMPTOM_RECORD_SYMPTOM_ID_KEY, symptomId);

            values.put(DatabaseDefinition.SYMPTOM_RECORD_RECORD_ID_KEY, recordId);

            long row_id = db.insert(DatabaseDefinition.SYMPTOM_RECORD_TABLE, null, values);

            Log.d("DAO-add", "result : " + row_id);

            return row_id;
        } catch (SQLiteException e) {

            e.printStackTrace();
            return -1;
        }
    }

    /**
     * add Symptom Record
     *
     * @param db        SQLiteDatabase
     * @param symptomId symptomId
     * @param recordId  recordId
     * @return raw id
     * @throws SQLiteException
     */
    public static long addSymptomRecord(SQLiteDatabase db, int symptomId, int recordId) throws SQLiteException {
        Log.d("DBSymptomDAO", "DB - addSymptomRecord");
//
//        if (symptomId <= 0 || recordId <= 0) {
//            Log.e("DAO-add", "invalid information");
//            return -1;
//        }

        ContentValues values = new ContentValues();

        values.put(DatabaseDefinition.SYMPTOM_RECORD_SYMPTOM_ID_KEY, symptomId);

        values.put(DatabaseDefinition.SYMPTOM_RECORD_RECORD_ID_KEY, recordId);

        long row_id = db.insert(DatabaseDefinition.SYMPTOM_RECORD_TABLE, null, values);

        Log.d("DAO-add", "result : " + row_id);

        return row_id;

    }

    /**
     * get Symptom
     *
     * @param id id
     * @return Symptom
     */
    @Nullable
    public static Symptom getSymptom(int id) {
        Log.d("DBSymptomDAO", "getSymptom");

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            cursor = db.query(DatabaseDefinition.SYMPTOM_TABLE, null, DatabaseDefinition.SYMPTOM_ID_KEY + " = ?", new String[]{String.valueOf(id)}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {// If records are found process them


                int symptomId = cursor.getInt(0);

                SymptomBuilder symptomBuilder = new SymptomBuilder().setSymptomId(symptomId);

                int index = cursor.getColumnIndexOrThrow(DatabaseDefinition.SYMPTOM_NAME_KEY);

                if (!cursor.isNull(index)) {
                    String name = cursor.getString(index);
                    symptomBuilder = symptomBuilder.setSymptomName(name);
                }

                index = cursor.getColumnIndexOrThrow(DatabaseDefinition.SYMPTOM_PRIORITY_KEY);

                if (!cursor.isNull(index)) {
                    String priority = cursor.getString(index);
                    symptomBuilder = symptomBuilder.setPriority(Integer.parseInt(priority));
                }

                return symptomBuilder.createSymptom();
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
     * get Symptoms For Record
     *
     * @param recordId recordId
     * @return ArrayList<Symptom>
     */
    public static ArrayList<Symptom> getSymptomsForRecord(int recordId) {
        Log.d("DBSymptomDAO", "getSymptomsForRecord");

        ArrayList<Symptom> symptoms = new ArrayList<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            String joinQuery =
                    "SELECT * FROM " + DatabaseDefinition.SYMPTOM_TABLE + " a INNER JOIN " + DatabaseDefinition.SYMPTOM_RECORD_TABLE +
                            " r USING(" + DatabaseDefinition.SYMPTOM_ID_KEY + ") WHERE r.record_id=?";

            cursor = db.rawQuery(joinQuery, new String[]{String.valueOf(recordId)});

            if (cursor != null && cursor.moveToFirst()) {// If records are found process them
                do {

                    int symptomId = cursor.getInt(0);

                    SymptomBuilder symptomBuilder = new SymptomBuilder().setSymptomId(symptomId);

                    int index = cursor.getColumnIndexOrThrow(DatabaseDefinition.SYMPTOM_NAME_KEY);

                    if (!cursor.isNull(index)) {
                        String name = cursor.getString(index);
                        symptomBuilder = symptomBuilder.setSymptomName(name);
                    }

                    index = cursor.getColumnIndexOrThrow(DatabaseDefinition.SYMPTOM_PRIORITY_KEY);

                    if (!cursor.isNull(index)) {
                        String priority = cursor.getString(index);
                        symptomBuilder = symptomBuilder.setPriority(Integer.parseInt(priority));
                    }

                    symptoms.add(symptomBuilder.createSymptom());
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

        return symptoms;
    }
}
