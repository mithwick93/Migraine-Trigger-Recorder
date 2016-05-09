package shehan.com.migrainetrigger.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import shehan.com.migrainetrigger.data.builders.RecordBuilder;
import shehan.com.migrainetrigger.data.model.Record;
import shehan.com.migrainetrigger.utility.database.DatabaseDefinition;
import shehan.com.migrainetrigger.utility.database.DatabaseHandler;

import static shehan.com.migrainetrigger.utility.AppUtil.getStringDate;
import static shehan.com.migrainetrigger.utility.AppUtil.getTimeStampDate;

/**
 * Created by Shehan on 4/13/2016.
 */
public final class DBRecordDAO {

    /**
     * add Record To DB
     *
     * @param record record
     * @return raw id
     */
    public static long addRecordTo(Record record) {
        Log.d("DBRecordDAO", "addRecordTo");

        if (record == null) {
            Log.e("DAO-add", "null record");
            return -1;
        }

        try (SQLiteDatabase db = DatabaseHandler.getWritableDatabase()) {

            ContentValues values = new ContentValues();

            values.put(DatabaseDefinition.RECORD_ID_KEY, record.getRecordId());
            //Start time
            if (record.getStartTime() != null) {
                values.put(DatabaseDefinition.RECORD_START_TIME_KEY, getStringDate(record.getStartTime()));
            }
            //End time
            if (record.getEndTime() != null) {
                values.put(DatabaseDefinition.RECORD_END_TIME_KEY, getStringDate(record.getEndTime()));
            }

            //Intensity
            values.put(DatabaseDefinition.RECORD_INTENSITY_KEY, record.getIntensity());

            //Location
            if (record.getLocation() != null) {
                values.put(DatabaseDefinition.RECORD_LOCATION_ID_KEY, record.getLocation().getLocationId());
            } else {
                values.put(DatabaseDefinition.RECORD_LOCATION_ID_KEY, -1);
            }

            // Inserting Row
            long row_id = db.insert(DatabaseDefinition.RECORD_TABLE, null, values);

            return row_id;
        } catch (SQLiteException e) {

            e.printStackTrace();
            return -1;
        }
    }

    /**
     * add Record To DB
     * @param db     SQLiteDatabase
     * @param record record
     * @return raw id
     * @throws SQLiteException
     */
    public static long addRecordTo(SQLiteDatabase db, Record record) throws SQLiteException {
        Log.d("DBRecordDAO", "addRecordTo");

        if (record == null) {
            Log.e("DAO-add", "null record");
            return -1;
        }
        ContentValues values = new ContentValues();

        values.put(DatabaseDefinition.RECORD_ID_KEY, record.getRecordId());
        //Start time
        if (record.getStartTime() != null) {
            Log.d("DBMedicineDAO", "addRecordTo sTime : " + record.getStartTime().getTime());
            values.put(DatabaseDefinition.RECORD_START_TIME_KEY, getStringDate(record.getStartTime()));
        }
        //End time
        if (record.getEndTime() != null) {
            Log.d("DBMedicineDAO", "addRecordTo eTime : " + record.getEndTime().getTime());
            values.put(DatabaseDefinition.RECORD_END_TIME_KEY, getStringDate(record.getEndTime()));
        }

        //Intensity
        values.put(DatabaseDefinition.RECORD_INTENSITY_KEY, record.getIntensity());

        //Location
        if (record.getLocation() != null) {
            values.put(DatabaseDefinition.RECORD_LOCATION_ID_KEY, record.getLocation().getLocationId());
        } else {
            values.put(DatabaseDefinition.RECORD_LOCATION_ID_KEY, -1);
        }

        // Inserting Row
        long row_id = db.insert(DatabaseDefinition.RECORD_TABLE, null, values);

        return row_id;
    }

    /**
     * update Record
     * @param db SQLiteDatabase
     * @param record record
     * @return whether the record update
     * @throws SQLiteException
     */
    public static long updateRecord(SQLiteDatabase db, Record record) throws SQLiteException {
        Log.d("DBRecordDAO", "updateRecord");

        if (record == null) {
            Log.e("DAO-update", "null record");
            return -1;
        }

        ContentValues values = new ContentValues();

        //Start time
        if (record.getStartTime() != null) {
            Log.d("DBMedicineDAO", "updateRecord sTime : " + record.getStartTime().getTime());
            values.put(DatabaseDefinition.RECORD_START_TIME_KEY, getStringDate(record.getStartTime()));
        }
        //End time
        if (record.getEndTime() != null) {
            Log.d("DBMedicineDAO", "updateRecord eTime : " + record.getEndTime().getTime());
            values.put(DatabaseDefinition.RECORD_END_TIME_KEY, getStringDate(record.getEndTime()));
        }

        //Intensity
        values.put(DatabaseDefinition.RECORD_INTENSITY_KEY, record.getIntensity());

        //Location
        if (record.getLocation() != null) {
            values.put(DatabaseDefinition.RECORD_LOCATION_ID_KEY, record.getLocation().getLocationId());
        } else {
            values.put(DatabaseDefinition.RECORD_LOCATION_ID_KEY, -1);
        }

        // Inserting Row
        long row_id = db.update(DatabaseDefinition.RECORD_TABLE, values, DatabaseDefinition.RECORD_ID_KEY + " = ?", new String[]{String.valueOf(record.getRecordId())});

        return row_id;
    }

    /**
     * get Last Record Id
     *
     * @return last record id
     */
    public static int getLastRecordId() {
        Log.d("DBRecordDAO", "getLastRecordId");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        int recordId = -1;
        try {
            String[] projection = {DatabaseDefinition.RECORD_ID_KEY};
            db = DatabaseHandler.getReadableDatabase();
            cursor = db.query(
                    DatabaseDefinition.RECORD_TABLE,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    DatabaseDefinition.RECORD_ID_KEY + " DESC",
                    "1");

            if (cursor != null && cursor.moveToFirst()) {// If records are found process them
                recordId = Integer.valueOf(cursor.getString(0));
                Log.d("getLastRecordId ", "Value: " + String.valueOf(recordId));
            } else {
                Log.d("getLastRecordId ", "Empty: ");
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
     * get Last Record
     *
     * @return Record
     */
    public static Record getLastRecord() {
        Log.d("DBRecordDAO", "getLastRecord");
        Record record = null;

        //Get last record from db after sorting in decending order of start date

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            String MY_QUERY = "SELECT * FROM table_a a INNER JOIN table_b b ON a.id=b.other_id WHERE b.property_id=?";

            String[] columns = {DatabaseDefinition.RECORD_ID_KEY, DatabaseDefinition.RECORD_START_TIME_KEY, DatabaseDefinition.RECORD_END_TIME_KEY};

            cursor = db.query(
                    DatabaseDefinition.RECORD_TABLE,//Table
                    columns,//Columns
                    null,//Selection
                    null,//Selection conditions
                    null,//Group by
                    null,//Having
                    DatabaseDefinition.RECORD_START_TIME_KEY + " DESC",//Order by
                    "1" //limit
            );

            if (cursor != null && cursor.moveToFirst()) {// If records are found process them
                int recordId = cursor.getInt(0);

                RecordBuilder recordBuilder = new RecordBuilder().setRecordId(recordId);

                int index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RECORD_START_TIME_KEY);

                if (!cursor.isNull(index)) {
                    String start = cursor.getString(index);
                    Log.d("DBMedicineDAO", "getAllRecords sTime : " + start);
                    recordBuilder = recordBuilder.setStartTime(getTimeStampDate(start));
                }

                index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RECORD_END_TIME_KEY);

                if (!cursor.isNull(index)) {
                    String end = cursor.getString(index);
                    Log.d("DBMedicineDAO", "getAllRecords eTime : " + end);
                    recordBuilder = recordBuilder.setEndTime(getTimeStampDate(end));
                }

                record = recordBuilder.createRecord();

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

        return record;
    }

    /**
     * get All Records
     *
     * @return ArrayList<Record>
     */
    public static ArrayList<Record> getAllRecords() {
        Log.d("DBRecordDAO", "getAllRecords");

        ArrayList<Record> records = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            cursor = db.query(DatabaseDefinition.RECORD_TABLE, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {// If records are found process them
                do {

                    int recordId = cursor.getInt(0);

                    RecordBuilder recordBuilder = new RecordBuilder().setRecordId(recordId);

                    int index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RECORD_START_TIME_KEY);

                    if (!cursor.isNull(index)) {
                        String start = cursor.getString(index);
                        Log.d("DBMedicineDAO", "getAllRecords sTime : " + start);
                        recordBuilder = recordBuilder.setStartTime(getTimeStampDate(start));
                    }

                    index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RECORD_END_TIME_KEY);

                    if (!cursor.isNull(index)) {
                        String end = cursor.getString(index);
                        Log.d("DBMedicineDAO", "getAllRecords eTime : " + end);
                        recordBuilder = recordBuilder.setEndTime(getTimeStampDate(end));
                    }

                    index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RECORD_INTENSITY_KEY);

                    if (!cursor.isNull(index)) {
                        Integer intensity = cursor.getInt(index);
                        recordBuilder = recordBuilder.setIntensity(intensity);
                    }

                    index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RECORD_LOCATION_ID_KEY);

                    if (!cursor.isNull(index)) {
                        Integer locationId = cursor.getInt(index);
                        recordBuilder = recordBuilder.setLocationId(locationId);
                    }
                    records.add(recordBuilder.createRecord());
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


        return records;
    }

    /**
     * get All Records , in descending order
     *
     * @return ArrayList<Record>
     */
    public static ArrayList<Record> getAllRecordsOrderByDate() {
        Log.d("DBRecordDAO", "getAllRecordsOrderByDate");

        ArrayList<Record> records = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            cursor = db.query(DatabaseDefinition.RECORD_TABLE, null, null, null, null, null, DatabaseDefinition.RECORD_ID_KEY + " DESC");
            if (cursor != null && cursor.moveToFirst()) {// If records are found process them
                do {

                    int recordId = cursor.getInt(0);

                    RecordBuilder recordBuilder = new RecordBuilder().setRecordId(recordId);

                    int index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RECORD_START_TIME_KEY);

                    if (!cursor.isNull(index)) {
                        String start = cursor.getString(index);
                        Log.d("DBMedicineDAO", "getAllRecords sTime : " + start);
                        recordBuilder = recordBuilder.setStartTime(getTimeStampDate(start));
                    }

                    index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RECORD_END_TIME_KEY);

                    if (!cursor.isNull(index)) {
                        String end = cursor.getString(index);
                        Log.d("DBMedicineDAO", "getAllRecords eTime : " + end);
                        recordBuilder = recordBuilder.setEndTime(getTimeStampDate(end));
                    }

                    index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RECORD_INTENSITY_KEY);

                    if (!cursor.isNull(index)) {
                        Integer intensity = cursor.getInt(index);
                        recordBuilder = recordBuilder.setIntensity(intensity);
                    }

                    index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RECORD_LOCATION_ID_KEY);

                    if (!cursor.isNull(index)) {
                        Integer locationId = cursor.getInt(index);
                        recordBuilder = recordBuilder.setLocationId(locationId);
                    }
                    records.add(recordBuilder.createRecord());
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


        return records;
    }

    /**
     * get Record
     *
     * @param id id
     * @return Record
     */
    @Nullable
    public static Record getRecord(int id) {
        Log.d("DBRecordDAO", "getRecord");

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            cursor = db.query(DatabaseDefinition.RECORD_TABLE, null, DatabaseDefinition.RECORD_ID_KEY + " = ?", new String[]{String.valueOf(id)}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {// If records are found process them


                int recordId = cursor.getInt(0);

                RecordBuilder recordBuilder = new RecordBuilder().setRecordId(recordId);

                int index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RECORD_START_TIME_KEY);

                if (!cursor.isNull(index)) {
                    String start = cursor.getString(index);
                    recordBuilder = recordBuilder.setStartTime(getTimeStampDate(start));
                }

                index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RECORD_END_TIME_KEY);

                if (!cursor.isNull(index)) {
                    String end = cursor.getString(index);
                    recordBuilder = recordBuilder.setEndTime(getTimeStampDate(end));
                }

                index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RECORD_INTENSITY_KEY);

                if (!cursor.isNull(index)) {
                    Integer intensity = cursor.getInt(index);
                    recordBuilder = recordBuilder.setIntensity(intensity);
                }

                index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RECORD_LOCATION_ID_KEY);

                if (!cursor.isNull(index)) {
                    Integer locationId = cursor.getInt(index);
                    recordBuilder = recordBuilder.setLocationId(locationId);
                }


                return recordBuilder.createRecord();

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
