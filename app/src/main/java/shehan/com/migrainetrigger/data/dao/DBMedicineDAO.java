package shehan.com.migrainetrigger.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import shehan.com.migrainetrigger.data.builders.MedicineBuilder;
import shehan.com.migrainetrigger.data.model.Medicine;
import shehan.com.migrainetrigger.utility.database.DatabaseDefinition;
import shehan.com.migrainetrigger.utility.database.DatabaseHandler;

/**
 * Created by Shehan on 4/13/2016.
 */
public final class DBMedicineDAO {

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

    public static long addMedicineRecord(int medicineId, int recordId, boolean effective) {
        Log.d("DBMedicineDAO", "DB - addMedicineRecord");

//        if (medicineId <= 0 || recordId <= 0) {
//            Log.e("DAO-add", "invalid information");
//            return -1;
//        }

        SQLiteDatabase db = DatabaseHandler.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();

            values.put(DatabaseDefinition.MEDICINE_RECORD_MEDICINE_ID_KEY, medicineId);

            values.put(DatabaseDefinition.MEDICINE_RECORD_RECORD_ID_KEY, recordId);

            values.put(DatabaseDefinition.MEDICINE_RECORD_EFFECTIVE_KEY, effective ? "t" : "f");

            long row_id = db.insert(DatabaseDefinition.MEDICINE_RECORD_TABLE, null, values);

            Log.d("DAO-add", "result : " + row_id);

            return row_id;
        } catch (SQLiteException e) {

            e.printStackTrace();
            return -1;
        } finally {
            db.close();
        }
    }

    public static long addMedicineRecord(SQLiteDatabase db, int medicineId, int recordId, boolean effective) throws SQLiteException {
        Log.d("DBMedicineDAO", "DB - addMedicineRecord");

//        if (medicineId <= 0 || recordId <= 0) {
//            Log.e("DAO-add", "invalid information");
//            return -1;
//        }

        ContentValues values = new ContentValues();

        values.put(DatabaseDefinition.MEDICINE_RECORD_MEDICINE_ID_KEY, medicineId);

        values.put(DatabaseDefinition.MEDICINE_RECORD_RECORD_ID_KEY, recordId);

        values.put(DatabaseDefinition.MEDICINE_RECORD_EFFECTIVE_KEY, effective ? "t" : "f");

        long row_id = db.insert(DatabaseDefinition.MEDICINE_RECORD_TABLE, null, values);

        Log.d("DAO-add", "result : " + row_id);

        return row_id;

    }

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
}
