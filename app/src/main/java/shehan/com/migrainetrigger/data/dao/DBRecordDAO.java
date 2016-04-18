package shehan.com.migrainetrigger.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

import shehan.com.migrainetrigger.data.builders.RecordBuilder;
import shehan.com.migrainetrigger.data.model.Record;
import shehan.com.migrainetrigger.utility.database.DatabaseDefinition;
import shehan.com.migrainetrigger.utility.database.DatabaseHandler;

import static shehan.com.migrainetrigger.view.utility.viewUtilities.getStringDate;
import static shehan.com.migrainetrigger.view.utility.viewUtilities.getTimeStampDate;

/**
 * Created by Shehan on 4/13/2016.
 */
public final class DBRecordDAO {

    public static long addRecordToDB(Record record) {
        Log.d("DBRecordDAO", "addRecordToDB");

        if (record == null) {
            Log.e("DAO-add", "null record");
            return -1;
        }

        SQLiteDatabase db = DatabaseHandler.getWritableDatabase();
        try {

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

            Log.d("DAO-add", "result : " + row_id);

            return row_id;
        } catch (SQLiteException e) {

            e.printStackTrace();
            return -1;
        } finally {
            db.close();
        }
    }

    public static long addRecordToDB(SQLiteDatabase db, Record record) throws SQLiteException {
        Log.d("DBMedicineDAO", "Add record");

        if (record == null) {
            Log.e("DAO-add", "null record");
            return -1;
        }
        ContentValues values = new ContentValues();

        values.put(DatabaseDefinition.RECORD_ID_KEY, record.getRecordId());
        //Start time
        if (record.getStartTime() != null) {
            Log.d("DBMedicineDAO", "addRecordToDB sTime : " + record.getStartTime().getTime());
            values.put(DatabaseDefinition.RECORD_START_TIME_KEY, getStringDate(record.getStartTime()));
        }
        //End time
        if (record.getEndTime() != null) {
            Log.d("DBMedicineDAO", "addRecordToDB eTime : " + record.getEndTime().getTime());
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

        Log.d("DAO-add", "result : " + row_id);
        return row_id;
    }

    public static int getLastRecordId() {
        Log.d("DBRecordDAO", "Last record");
        SQLiteDatabase db = DatabaseHandler.getReadableDatabase();
        int recordId = -1;
        try {
            String[] projection = {DatabaseDefinition.RECORD_ID_KEY};
            Cursor cursor = db.query(
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
            db.close();
        }
        return recordId;
    }

    /**
     * @return
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
     * @param id
     * @return
     */
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
