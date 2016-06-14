package shehan.com.migrainetrigger.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.sql.Timestamp;
import java.util.ArrayList;

import shehan.com.migrainetrigger.data.builders.RecordBuilder;
import shehan.com.migrainetrigger.data.model.Record;
import shehan.com.migrainetrigger.utility.AppUtil;
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
     * @param db     SQLiteDatabase
     * @param record record
     * @return raw id
     * @throws SQLiteException
     */
    public static long addRecord(SQLiteDatabase db, Record record) throws SQLiteException {
        Log.d("DBRecordDAO", "addRecord");

        if (record == null) {
            Log.e("DAO-add", "null record");
            return -1;
        }
        ContentValues values = new ContentValues();

        values.put(DatabaseDefinition.RECORD_ID_KEY, record.getRecordId());
        //Start time
        if (record.getStartTime() != null) {
            Log.d("DBRecordDAO", "addRecord sTime : " + record.getStartTime().getTime());
            values.put(DatabaseDefinition.RECORD_START_TIME_KEY, getStringDate(record.getStartTime()));
        }
        //End time
        if (record.getEndTime() != null) {
            Log.d("DBRecordDAO", "addRecord eTime : " + record.getEndTime().getTime());
            values.put(DatabaseDefinition.RECORD_END_TIME_KEY, getStringDate(record.getEndTime()));
        }

        //Intensity
        values.put(DatabaseDefinition.RECORD_INTENSITY_KEY, record.getIntensity());

        //Location
        if (record.getLocation() != null) {
            values.put(DatabaseDefinition.RECORD_LOCATION_ID_KEY, record.getLocation().getLocationId());
        } else {
            //A sign nil to location
            values.put(DatabaseDefinition.RECORD_LOCATION_ID_KEY, 0);
        }

        // Inserting Row

        return db.insert(DatabaseDefinition.RECORD_TABLE, null, values);
    }

    /**
     * Delete record
     *
     * @param db       SQLiteDatabase
     * @param recordId recordId
     * @return whether record deleted
     */
    public static long deleteRecord(SQLiteDatabase db, int recordId) {
        Log.d("DBRecordDAO", "deleteRecord");

        return (long) db.delete(DatabaseDefinition.RECORD_TABLE, DatabaseDefinition.RECORD_ID_KEY + " = ?", new String[]{String.valueOf(recordId)});
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
                        Log.d("DBRecordDAO", "getAllRecords sTime : " + start);
                        recordBuilder = recordBuilder.setStartTime(getTimeStampDate(start));
                    }

                    index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RECORD_END_TIME_KEY);

                    if (!cursor.isNull(index)) {
                        String end = cursor.getString(index);
                        Log.d("DBRecordDAO", "getAllRecords eTime : " + end);
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

            cursor = db.query(DatabaseDefinition.RECORD_TABLE, null, null, null, null, null, DatabaseDefinition.RECORD_START_TIME_KEY + " DESC");
            if (cursor != null && cursor.moveToFirst()) {// If records are found process them
                do {

                    int recordId = cursor.getInt(0);

                    RecordBuilder recordBuilder = new RecordBuilder().setRecordId(recordId);

                    int index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RECORD_START_TIME_KEY);

                    if (!cursor.isNull(index)) {
                        String start = cursor.getString(index);
                        Log.d("DBRecordDAO", "getAllRecords sTime : " + start);
                        recordBuilder = recordBuilder.setStartTime(getTimeStampDate(start));
                    }

                    index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RECORD_END_TIME_KEY);

                    if (!cursor.isNull(index)) {
                        String end = cursor.getString(index);
                        Log.d("DBRecordDAO", "getAllRecords eTime : " + end);
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
     * @return Arraylist of string array
     */
    public static ArrayList<String[]> getAllRecordsOrderByDateRAW() {
        Log.d("DBRecordDAO", "getAllRecordsOrderByDateRAW");

        ArrayList<String[]> records = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            cursor = db.query(DatabaseDefinition.RECORD_TABLE, null, null, null, null, null, DatabaseDefinition.RECORD_START_TIME_KEY + " DESC");
            if (cursor != null && cursor.moveToFirst()) {// If records are found process them
                do {

                    String recordId = cursor.getString(0);
                    String startTime = "-";
                    String endTime = "-";
                    String intensity = "-";

                    int index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RECORD_START_TIME_KEY);

                    if (!cursor.isNull(index)) {
                        startTime = cursor.getString(index);
                    }

                    index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RECORD_END_TIME_KEY);

                    if (!cursor.isNull(index)) {
                        endTime = cursor.getString(index);
                    }

                    index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RECORD_INTENSITY_KEY);

                    if (!cursor.isNull(index)) {
                        intensity = cursor.getString(index);
                        intensity = intensity.trim().equals("0") ? "-" : intensity;
                    }

                    records.add(new String[]{recordId, startTime, endTime, intensity});

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

    public static ArrayList<Record> getAverageReportRecords(Timestamp from, Timestamp to) {
        Log.d("DBRecordDAO", "getAverageReportRecords");

        ArrayList<Record> records = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            String[] selectionArgs = {getStringDate(from), getStringDate(to)};

            cursor = db.query(
                    DatabaseDefinition.RECORD_TABLE,//Table
                    null,//Columns
                    DatabaseDefinition.RECORD_START_TIME_KEY + " BETWEEN ? AND ?",//Selection
                    selectionArgs,//Selection conditions
                    null,//Group by
                    null,//Having
                    null,//Order by
                    null //limit
            );
            if (cursor != null && cursor.moveToFirst()) {// If records are found process them
                do {

                    int recordId = cursor.getInt(0);

                    RecordBuilder recordBuilder = new RecordBuilder().setRecordId(recordId);

                    int index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RECORD_START_TIME_KEY);

                    if (!cursor.isNull(index)) {
                        String start = cursor.getString(index);
                        Log.d("DBRecordDAO", "getAverageReportRecords sTime : " + start);
                        recordBuilder = recordBuilder.setStartTime(getTimeStampDate(start));
                    }

                    index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RECORD_END_TIME_KEY);

                    if (!cursor.isNull(index)) {
                        String end = cursor.getString(index);
                        Log.d("DBRecordDAO", "getAverageReportRecords eTime : " + end);
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
     * get First Record
     *
     * @return First record
     */
    public static Record getFirstRecord() {
        Log.d("DBRecordDAO", "getFirstRecord");
        Record record = null;

        //Get first record from db after sorting in ascending order of start date

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            String[] columns = {DatabaseDefinition.RECORD_ID_KEY, DatabaseDefinition.RECORD_START_TIME_KEY, DatabaseDefinition.RECORD_END_TIME_KEY};

            cursor = db.query(
                    DatabaseDefinition.RECORD_TABLE,//Table
                    columns,//Columns
                    null,//Selection
                    null,//Selection conditions
                    null,//Group by
                    null,//Having
                    "datetime(" + DatabaseDefinition.RECORD_START_TIME_KEY + ")",//Order by
                    "1" //limit
            );

            if (cursor != null && cursor.moveToFirst()) {// If records are found process them
                int recordId = cursor.getInt(0);

                RecordBuilder recordBuilder = new RecordBuilder().setRecordId(recordId);

                int index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RECORD_START_TIME_KEY);

                if (!cursor.isNull(index)) {
                    String start = cursor.getString(index);
                    Log.d("DBRecordDAO", "getFirstRecord sTime : " + start);
                    recordBuilder = recordBuilder.setStartTime(getTimeStampDate(start));
                }

                index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RECORD_END_TIME_KEY);

                if (!cursor.isNull(index)) {
                    String end = cursor.getString(index);
                    Log.d("DBRecordDAO", "getFirstRecord eTime : " + end);
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
     * get Last Record
     *
     * @return Record
     */
    public static Record getLastRecord() {
        Log.d("DBRecordDAO", "getLastRecord");
        Record record = null;

        //Get last record from db after sorting in descending order of start date

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            String[] columns = {DatabaseDefinition.RECORD_ID_KEY, DatabaseDefinition.RECORD_START_TIME_KEY, DatabaseDefinition.RECORD_END_TIME_KEY};

            cursor = db.query(
                    DatabaseDefinition.RECORD_TABLE,//Table
                    columns,//Columns
                    null,//Selection
                    null,//Selection conditions
                    null,//Group by
                    null,//Having
                    "datetime(" + DatabaseDefinition.RECORD_START_TIME_KEY + ")" + " DESC",//Order by
                    "1" //limit
            );

            if (cursor != null && cursor.moveToFirst()) {// If records are found process them
                int recordId = cursor.getInt(0);

                RecordBuilder recordBuilder = new RecordBuilder().setRecordId(recordId);

                int index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RECORD_START_TIME_KEY);

                if (!cursor.isNull(index)) {
                    String start = cursor.getString(index);
                    Log.d("DBRecordDAO", "getLastRecord sTime : " + start);
                    recordBuilder = recordBuilder.setStartTime(getTimeStampDate(start));
                }

                index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RECORD_END_TIME_KEY);

                if (!cursor.isNull(index)) {
                    String end = cursor.getString(index);
                    Log.d("DBRecordDAO", "getLastRecord eTime : " + end);
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

            if (cursor != null && cursor.moveToFirst() && cursor.getString(0) != null) {// If records are found process them
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

    public static int getTotalRecords(Timestamp from, Timestamp to) {
        Log.d("DBRecordDAO", "getTotalRecords");
        int total = -1;

        Log.d("DBRecordDAO", "from " + AppUtil.getStringDate(from));
        Log.d("DBRecordDAO", "to " + AppUtil.getStringDate(to));
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            String[] columns = {"COUNT(" + DatabaseDefinition.RECORD_ID_KEY + ")"};
            String[] selectionArgs = {getStringDate(from), getStringDate(to)};


            cursor = db.query(
                    DatabaseDefinition.RECORD_TABLE,//Table
                    columns,//Columns
                    DatabaseDefinition.RECORD_START_TIME_KEY + " BETWEEN ? AND ?",//Selection
                    selectionArgs,//Selection conditions
                    null,//Group by
                    null,//Having
                    null,//Order by
                    null //limit
            );

            if (cursor != null && cursor.moveToFirst()) {// If records are found process them
                total = cursor.getInt(0);
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
        return total;
    }

    /**
     * update Record
     *
     * @param db     SQLiteDatabase
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
            Log.d("DBRecordDAO", "updateRecord sTime : " + record.getStartTime().getTime());
            values.put(DatabaseDefinition.RECORD_START_TIME_KEY, getStringDate(record.getStartTime()));
        }
        //End time
        if (record.getEndTime() != null) {
            Log.d("DBRecordDAO", "updateRecord eTime : " + record.getEndTime().getTime());
            values.put(DatabaseDefinition.RECORD_END_TIME_KEY, getStringDate(record.getEndTime()));
        }

        //Intensity
        values.put(DatabaseDefinition.RECORD_INTENSITY_KEY, record.getIntensity());

        //Location
        if (record.getLocation() != null) {
            values.put(DatabaseDefinition.RECORD_LOCATION_ID_KEY, record.getLocation().getLocationId());
        } else {
            values.put(DatabaseDefinition.RECORD_LOCATION_ID_KEY, 0);
        }

        // Inserting Row

        return (long) db.update(DatabaseDefinition.RECORD_TABLE, values, DatabaseDefinition.RECORD_ID_KEY + " = ?", new String[]{String.valueOf(record.getRecordId())});
    }
}
