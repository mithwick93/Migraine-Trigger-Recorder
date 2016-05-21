package shehan.com.migrainetrigger.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.sql.Timestamp;
import java.util.ArrayList;

import shehan.com.migrainetrigger.data.builders.SymptomBuilder;
import shehan.com.migrainetrigger.data.model.Symptom;
import shehan.com.migrainetrigger.utility.database.DatabaseDefinition;
import shehan.com.migrainetrigger.utility.database.DatabaseHandler;

import static shehan.com.migrainetrigger.utility.AppUtil.getStringDate;

/**
 * Created by Shehan on 4/13/2016.
 */
public final class DBSymptomDAO {

    /**
     * add Symptom
     *
     * @param symptom symptom
     * @return affected no of rows
     */
    public static long addSymptom(Symptom symptom) {
        Log.d("DBSymptomDAO", "DB - addSymptom");
        try (SQLiteDatabase db = DatabaseHandler.getWritableDatabase()) {

            ContentValues values = new ContentValues();

            values.put(DatabaseDefinition.SYMPTOM_ID_KEY, symptom.getSymptomId());

            values.put(DatabaseDefinition.SYMPTOM_NAME_KEY, symptom.getSymptomName());

            values.put(DatabaseDefinition.SYMPTOM_PRIORITY_KEY, symptom.getPriority());

            long row_id = db.insert(DatabaseDefinition.SYMPTOM_TABLE, null, values);

            return row_id;
        } catch (SQLiteException e) {

            e.printStackTrace();
            return -1;
        }
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

        return row_id;

    }

    /**
     * delete Symptom
     *
     * @param id id
     * @return affected no of rows
     */
    public static long deleteSymptom(int id) {
        Log.d("DBSymptomDAO", "deleteSymptom");
        try (SQLiteDatabase db = DatabaseHandler.getWritableDatabase()) {

            long row_id = db.delete(DatabaseDefinition.SYMPTOM_TABLE, DatabaseDefinition.SYMPTOM_ID_KEY + " = ?", new String[]{String.valueOf(id)});
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
    public static long deleteSymptomRecords(SQLiteDatabase db, int recordId) {
        Log.d("DBSymptomDAO", "DB - deleteSymptomRecords");

        long row_id = db.delete(DatabaseDefinition.SYMPTOM_RECORD_TABLE, DatabaseDefinition.SYMPTOM_RECORD_RECORD_ID_KEY + " = ?", new String[]{String.valueOf(recordId)});
        return row_id;
    }

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
     * get Last Record Id
     *
     * @return last symptom record id
     */
    public static int getLastRecordId() {
        Log.d("DBSymptomDAO", "getLastRecordId");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        int recordId = -1;
        try {
            String[] projection = {DatabaseDefinition.SYMPTOM_ID_KEY};
            db = DatabaseHandler.getReadableDatabase();
            cursor = db.query(
                    DatabaseDefinition.SYMPTOM_TABLE,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    DatabaseDefinition.SYMPTOM_ID_KEY + " DESC",
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

    /**
     * get Top Symptoms
     *
     * @param from  from date
     * @param to    to date
     * @param limit limit
     * @return ArrayList<String>
     */
    public static ArrayList<String> getTopSymptoms(Timestamp from, Timestamp to, int limit) {
        Log.d("DBSymptomDAO", "getTopSymptoms");

        ArrayList<String> top = new ArrayList<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            String topic = DatabaseDefinition.SYMPTOM_NAME_KEY;//Dynamic
            String topicId = DatabaseDefinition.SYMPTOM_ID_KEY;//Dynamic

            String rightTable = DatabaseDefinition.RECORD_TABLE;
            String leftTable = DatabaseDefinition.SYMPTOM_TABLE;//Dynamic
            String middleTable = DatabaseDefinition.SYMPTOM_RECORD_TABLE;//Dynamic

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
     * update Symptom Record
     *
     * @param symptom symptom
     * @return no of effected rows
     */
    public static long updateSymptomRecord(Symptom symptom) {
        Log.d("DBSymptomDAO", "DB - updateReliefRecord");
        try (SQLiteDatabase db = DatabaseHandler.getWritableDatabase()) {

            ContentValues values = new ContentValues();

            if (symptom.getSymptomName().equals("") && symptom.getPriority() < 0) {
                throw new SQLiteException("Empty update");
            }

            if (!symptom.getSymptomName().equals("")) {
                values.put(DatabaseDefinition.SYMPTOM_NAME_KEY, symptom.getSymptomName());
            }
            if (symptom.getPriority() >= 0) {
                values.put(DatabaseDefinition.SYMPTOM_PRIORITY_KEY, symptom.getPriority());
            }


            long result = db.update(
                    DatabaseDefinition.SYMPTOM_TABLE,
                    values,
                    DatabaseDefinition.SYMPTOM_ID_KEY + " = ?",
                    new String[]{String.valueOf(symptom.getSymptomId())}
            );

            return result;
        } catch (SQLiteException e) {

            e.printStackTrace();
            return -1;
        }
    }
}
