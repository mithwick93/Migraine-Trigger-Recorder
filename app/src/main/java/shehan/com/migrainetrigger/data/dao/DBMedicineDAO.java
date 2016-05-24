package shehan.com.migrainetrigger.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.sql.Timestamp;
import java.util.ArrayList;

import shehan.com.migrainetrigger.data.builders.MedicineBuilder;
import shehan.com.migrainetrigger.data.model.Medicine;
import shehan.com.migrainetrigger.utility.database.DatabaseDefinition;
import shehan.com.migrainetrigger.utility.database.DatabaseHandler;

import static shehan.com.migrainetrigger.utility.AppUtil.getStringDate;

/**
 * Created by Shehan on 4/13/2016.
 */
public final class DBMedicineDAO {

    /**
     * add Medicine
     *
     * @param medicine medicine
     * @return affected no of rows
     */
    public static long addMedicine(Medicine medicine) {
        Log.d("DBMedicineDAO", "DB - addMedicine");
        try (SQLiteDatabase db = DatabaseHandler.getWritableDatabase()) {

            ContentValues values = new ContentValues();

            values.put(DatabaseDefinition.MEDICINE_ID_KEY, medicine.getMedicineId());

            values.put(DatabaseDefinition.MEDICINE_NAME_KEY, medicine.getMedicineName());

            values.put(DatabaseDefinition.MEDICINE_PRIORITY_KEY, medicine.getPriority());

            long row_id = db.insert(DatabaseDefinition.MEDICINE_TABLE, null, values);

            return row_id;
        } catch (SQLiteException e) {

            e.printStackTrace();
            return -1;
        }
    }

    /**
     * add Medicine Record
     *
     * @param medicineId medicineId
     * @param recordId   recordId
     * @param effective  effective
     * @return row id
     */
    public static long addMedicineRecord(int medicineId, int recordId, boolean effective) {
        Log.d("DBMedicineDAO", "DB - addMedicineRecord");

        try (SQLiteDatabase db = DatabaseHandler.getWritableDatabase()) {

            ContentValues values = new ContentValues();

            values.put(DatabaseDefinition.MEDICINE_RECORD_MEDICINE_ID_KEY, medicineId);

            values.put(DatabaseDefinition.MEDICINE_RECORD_RECORD_ID_KEY, recordId);

            values.put(DatabaseDefinition.MEDICINE_RECORD_EFFECTIVE_KEY, effective ? "t" : "f");

            long row_id = db.insert(DatabaseDefinition.MEDICINE_RECORD_TABLE, null, values);

            return row_id;
        } catch (SQLiteException e) {

            e.printStackTrace();
            return -1;
        }
    }

    /**
     * add Medicine Record
     *
     * @param db         SQLiteDatabase
     * @param medicineId medicineId
     * @param recordId   recordId
     * @param effective  effective
     * @return raw id
     * @throws SQLiteException
     */
    public static long addMedicineRecord(SQLiteDatabase db, int medicineId, int recordId, boolean effective) throws SQLiteException {
        Log.d("DBMedicineDAO", "DB - addMedicineRecord");

        ContentValues values = new ContentValues();

        values.put(DatabaseDefinition.MEDICINE_RECORD_MEDICINE_ID_KEY, medicineId);

        values.put(DatabaseDefinition.MEDICINE_RECORD_RECORD_ID_KEY, recordId);

        values.put(DatabaseDefinition.MEDICINE_RECORD_EFFECTIVE_KEY, effective ? "t" : "f");

        long row_id = db.insert(DatabaseDefinition.MEDICINE_RECORD_TABLE, null, values);

        return row_id;

    }

    /**
     * delete Medicine
     *
     * @param id id
     * @return affected no of rows
     */
    public static long deleteMedicine(int id) {
        Log.d("DBMedicineDAO", "deleteMedicine");
        try (SQLiteDatabase db = DatabaseHandler.getWritableDatabase()) {

            long row_id = db.delete(DatabaseDefinition.MEDICINE_TABLE, DatabaseDefinition.MEDICINE_ID_KEY + " = ?", new String[]{String.valueOf(id)});
            return row_id;
        } catch (SQLiteException e) {

            e.printStackTrace();
            return -1;
        }
    }

    /**
     * delete Medicine
     *
     * @param db SQLiteDatabase
     * @param id id
     * @return affected no of rows
     */
    public static long deleteMedicine(SQLiteDatabase db, int id) {
        Log.d("DBMedicineDAO", "deleteMedicine");

        long row_id = db.delete(DatabaseDefinition.MEDICINE_TABLE, DatabaseDefinition.MEDICINE_ID_KEY + " = ?", new String[]{String.valueOf(id)});
        return row_id;
    }

    /**
     * delete Medicine Records
     *
     * @param db       SQLiteDatabase
     * @param recordId recordId
     * @return deleted no of rows
     */
    public static long deleteMedicineRecords(SQLiteDatabase db, int recordId) {
        Log.d("DBMedicineDAO", "DB - deleteMedicineRecords");

        long row_id = db.delete(DatabaseDefinition.MEDICINE_RECORD_TABLE, DatabaseDefinition.MEDICINE_RECORD_RECORD_ID_KEY + " = ?", new String[]{String.valueOf(recordId)});
        return row_id;
    }

    /**
     * get All Medicines
     *
     * @return ArrayList<Medicine>
     */
    public static ArrayList<Medicine> getAllMedicines() {
        Log.d("DBMedicineDAO", " DB - getAllMedicines ");
        ArrayList<Medicine> medicineArrayList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            cursor = db.query(DatabaseDefinition.MEDICINE_TABLE, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {// If records are found process them
                do {

                    Medicine medicine = new MedicineBuilder()
                            .setMedicineId(cursor.getInt(0))
                            .setMedicineName(cursor.getString(1))
                            .setPriority(cursor.getInt(2))
                            .createMedicine();
                    medicineArrayList.add(medicine);
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
        return medicineArrayList;
    }

    /**
     * get Last Record Id
     *
     * @return last medicine record id
     */
    public static int getLastRecordId() {
        Log.d("DBMedicineDAO", "getLastRecordId");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        int recordId = -1;
        try {
            String[] projection = {DatabaseDefinition.MEDICINE_ID_KEY};
            db = DatabaseHandler.getReadableDatabase();
            cursor = db.query(
                    DatabaseDefinition.MEDICINE_TABLE,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    DatabaseDefinition.MEDICINE_ID_KEY + " DESC",
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
     * get Medicine
     *
     * @param id id
     * @return Medicine
     */
    @Nullable
    public static Medicine getMedicine(int id) {
        Log.d("DBMedicineDAO", "getMedicine");

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            cursor = db.query(DatabaseDefinition.MEDICINE_TABLE, null, DatabaseDefinition.MEDICINE_ID_KEY + " = ?", new String[]{String.valueOf(id)}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {// If records are found process them


                int medicineId = cursor.getInt(0);

                MedicineBuilder medicineBuilder = new MedicineBuilder().setMedicineId(medicineId);

                int index = cursor.getColumnIndexOrThrow(DatabaseDefinition.MEDICINE_NAME_KEY);

                if (!cursor.isNull(index)) {
                    String name = cursor.getString(index);
                    medicineBuilder = medicineBuilder.setMedicineName(name);
                }

                index = cursor.getColumnIndexOrThrow(DatabaseDefinition.MEDICINE_PRIORITY_KEY);

                if (!cursor.isNull(index)) {
                    String priority = cursor.getString(index);
                    medicineBuilder = medicineBuilder.setPriority(Integer.parseInt(priority));
                }

                return medicineBuilder.createMedicine();
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
     * get Medicines For Record
     *
     * @param recordId recordId
     * @return ArrayList<Medicine>
     */
    public static ArrayList<Medicine> getMedicinesForRecord(int recordId) {
        Log.d("DBMedicineDAO", "getMedicinesForRecord");

        ArrayList<Medicine> medicines = new ArrayList<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            String joinQuery =
                    "SELECT * FROM " + DatabaseDefinition.MEDICINE_TABLE + " a INNER JOIN " + DatabaseDefinition.MEDICINE_RECORD_TABLE +
                            " r USING(" + DatabaseDefinition.MEDICINE_ID_KEY + ") WHERE r.record_id=?";

            cursor = db.rawQuery(joinQuery, new String[]{String.valueOf(recordId)});

            if (cursor != null && cursor.moveToFirst()) {// If records are found process them
                do {

                    int medicineId = cursor.getInt(0);

                    MedicineBuilder medicineBuilder = new MedicineBuilder().setMedicineId(medicineId);

                    int index = cursor.getColumnIndexOrThrow(DatabaseDefinition.MEDICINE_NAME_KEY);

                    if (!cursor.isNull(index)) {
                        String name = cursor.getString(index);
                        medicineBuilder = medicineBuilder.setMedicineName(name);
                    }

                    index = cursor.getColumnIndexOrThrow(DatabaseDefinition.MEDICINE_PRIORITY_KEY);

                    if (!cursor.isNull(index)) {
                        String priority = cursor.getString(index);
                        medicineBuilder = medicineBuilder.setPriority(Integer.parseInt(priority));
                    }

                    index = cursor.getColumnIndexOrThrow(DatabaseDefinition.MEDICINE_RECORD_EFFECTIVE_KEY);

                    if (!cursor.isNull(index)) {
                        String effective = cursor.getString(index);
                        medicineBuilder = medicineBuilder.setEffective(effective.equals("t"));
                    }

                    medicines.add(medicineBuilder.createMedicine());
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

        return medicines;
    }

    /**
     * get Top Effective Medicines
     *
     * @param from  from date
     * @param to    to date
     * @param limit limit
     * @return ArrayList<String>
     */
    public static ArrayList<String> getTopEffectiveMedicines(Timestamp from, Timestamp to, int limit) {
        Log.d("DBMedicineDAO", "getTopEffectiveMedicines");

        ArrayList<String> top = new ArrayList<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            String topic = DatabaseDefinition.MEDICINE_NAME_KEY;//Dynamic
            String topicId = DatabaseDefinition.MEDICINE_ID_KEY;//Dynamic

            String rightTable = DatabaseDefinition.RECORD_TABLE;
            String leftTable = DatabaseDefinition.MEDICINE_TABLE;//Dynamic
            String middleTable = DatabaseDefinition.MEDICINE_RECORD_TABLE;//Dynamic

            String rightId = DatabaseDefinition.RECORD_ID_KEY;

            String filter = DatabaseDefinition.RECORD_START_TIME_KEY;

            String joinQuery =
                    "SELECT " + topic + ", COUNT(" + topicId + ") as topCount " +
                            "FROM " + leftTable + " NATURAL JOIN " + middleTable + ", " + rightTable +
                            " WHERE " + middleTable + "." + rightId + " = " + rightTable + "." + rightId +
                            " AND " + middleTable + ".effective='t' AND " + rightTable + "." + filter + " BETWEEN ? AND ? " +
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
     * get Top Medicines
     *
     * @param from  from date
     * @param to    to date
     * @param limit limit
     * @return ArrayList<String>
     */
    public static ArrayList<String> getTopMedicines(Timestamp from, Timestamp to, int limit) {
        Log.d("DBMedicineDAO", "getTopMedicines");

        ArrayList<String> top = new ArrayList<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            String topic = DatabaseDefinition.MEDICINE_NAME_KEY;//Dynamic
            String topicId = DatabaseDefinition.MEDICINE_ID_KEY;//Dynamic

            String rightTable = DatabaseDefinition.RECORD_TABLE;
            String leftTable = DatabaseDefinition.MEDICINE_TABLE;//Dynamic
            String middleTable = DatabaseDefinition.MEDICINE_RECORD_TABLE;//Dynamic

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
     * update Medicine Record
     *
     * @param medicine medicine
     * @return no of effected rows
     */
    public static long updateMedicineRecord(Medicine medicine) {
        Log.d("DBMedicineDAO", "DB - updateMedicineRecord");
        try (SQLiteDatabase db = DatabaseHandler.getWritableDatabase()) {

            ContentValues values = new ContentValues();

            if (medicine.getMedicineName().equals("") && medicine.getPriority() < 0) {
                throw new SQLiteException("Empty update");
            }

            if (!medicine.getMedicineName().equals("")) {
                values.put(DatabaseDefinition.MEDICINE_NAME_KEY, medicine.getMedicineName());
            }
            if (medicine.getPriority() >= 0) {
                values.put(DatabaseDefinition.MEDICINE_PRIORITY_KEY, medicine.getPriority());
            }


            long result = db.update(
                    DatabaseDefinition.MEDICINE_TABLE,
                    values,
                    DatabaseDefinition.MEDICINE_ID_KEY + " = ?",
                    new String[]{String.valueOf(medicine.getMedicineId())}
            );

            return result;
        } catch (SQLiteException e) {

            e.printStackTrace();
            return -1;
        }
    }


}
