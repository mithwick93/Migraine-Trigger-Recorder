package shehan.com.migrainetrigger.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.sql.Timestamp;
import java.util.ArrayList;

import shehan.com.migrainetrigger.data.builders.BodyAreaBuilder;
import shehan.com.migrainetrigger.data.model.BodyArea;
import shehan.com.migrainetrigger.utility.database.DatabaseDefinition;
import shehan.com.migrainetrigger.utility.database.DatabaseHandler;

import static shehan.com.migrainetrigger.utility.AppUtil.getStringDate;

/**
 * Created by Shehan on 4/13/2016.
 */
public class DBBodyAreaDAO {

    /**
     * Get all body areas
     *
     * @return ArrayList<BodyArea> of body areas
     */
    public static ArrayList<BodyArea> getAllBodyAreas() {
        Log.d("DBBodyAreaDAO", " DB - getAllBodyAreas ");
        ArrayList<BodyArea> bodyAreaArrayList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            cursor = db.query(DatabaseDefinition.BODY_AREA_TABLE, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {// If records are found process them
                do {

                    BodyArea bodyArea = new BodyAreaBuilder()
                            .setBodyAreaId(cursor.getInt(0))
                            .setBodyAreaName(cursor.getString(1))
                            .createBodyArea();
                    bodyAreaArrayList.add(bodyArea);
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
        return bodyAreaArrayList;
    }

    /**
     * Add body area record
     *
     * @param bodyAreaId body area id
     * @param recordId   record id
     * @return row id
     */
    public static long addBodyAreaRecord(int bodyAreaId, int recordId) {
        Log.d("DBBodyAreaDAO", "DB - addBodyAreaRecord");

//        if (bodyAreaId <= 0 || recordId < 0) {
//            Log.e("DAO-add", "invalid information");
//            return -1;
//        }

        try (SQLiteDatabase db = DatabaseHandler.getWritableDatabase()) {

            ContentValues values = new ContentValues();

            values.put(DatabaseDefinition.BODY_AREA_RECORD_AREA_ID_KEY, bodyAreaId);

            values.put(DatabaseDefinition.BODY_AREA_RECORD_RECORD_ID_KEY, recordId);

            long row_id = db.insert(DatabaseDefinition.BODY_AREA_RECORD_TABLE, null, values); //Add new account to database

            return row_id;
        } catch (SQLiteException e) {

            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Add body area record in a transaction
     *
     * @param db         sql database
     * @param bodyAreaId body area id
     * @param recordId   record id
     * @return row id
     * @throws SQLiteException
     */
    public static long addBodyAreaRecord(SQLiteDatabase db, int bodyAreaId, int recordId) throws SQLiteException {
        Log.d("DBBodyAreaDAO", "DB - addBodyAreaRecord");

//        if (bodyAreaId <= 0 || recordId <= 0) {
//            Log.e("DAO-add", "invalid information");
//            return -1;
//        }

        ContentValues values = new ContentValues();

        values.put(DatabaseDefinition.BODY_AREA_RECORD_AREA_ID_KEY, bodyAreaId);

        values.put(DatabaseDefinition.BODY_AREA_RECORD_RECORD_ID_KEY, recordId);

        long row_id = db.insert(DatabaseDefinition.BODY_AREA_RECORD_TABLE, null, values);

        return row_id;

    }

    /**
     * delete Body Area Records
     *
     * @param db       SQLiteDatabase
     * @param recordId recordId
     * @return deleted no of rows
     */
    public static long deleteBodyAreaRecords(SQLiteDatabase db, int recordId) {
        Log.d("DBBodyAreaDAO", "DB - deleteBodyAreaRecords");

        long row_id = db.delete(DatabaseDefinition.BODY_AREA_RECORD_TABLE, DatabaseDefinition.BODY_AREA_RECORD_RECORD_ID_KEY + " = ?", new String[]{String.valueOf(recordId)});
        return row_id;
    }

    /**
     * get Body Area
     *
     * @param id id
     * @return BodyArea
     */
    @Nullable
    public static BodyArea getBodyArea(int id) {
        Log.d("DBBodyAreaDAO", "getBodyArea");

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            cursor = db.query(DatabaseDefinition.BODY_AREA_TABLE, null, DatabaseDefinition.BODY_AREA_ID_KEY + " = ?", new String[]{String.valueOf(id)}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {// If records are found process them


                int bodyAreaId = cursor.getInt(0);

                BodyAreaBuilder bodyAreaBuilder = new BodyAreaBuilder().setBodyAreaId(bodyAreaId);

                int index = cursor.getColumnIndexOrThrow(DatabaseDefinition.BODY_AREA_NAME_KEY);

                if (!cursor.isNull(index)) {
                    String name = cursor.getString(index);
                    bodyAreaBuilder = bodyAreaBuilder.setBodyAreaName(name);
                }


                return bodyAreaBuilder.createBodyArea();
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
     * get Body Areas For Record
     *
     * @param recordId recordId
     * @return ArrayList<BodyArea>
     */
    public static ArrayList<BodyArea> getBodyAreasForRecord(int recordId) {
        Log.d("DBBodyAreaDAO", "getBodyAreasForRecord");

        ArrayList<BodyArea> bodyAreas = new ArrayList<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            String joinQuery =
                    "SELECT * FROM " + DatabaseDefinition.BODY_AREA_TABLE + " a INNER JOIN " + DatabaseDefinition.BODY_AREA_RECORD_TABLE +
                            " r USING(" + DatabaseDefinition.BODY_AREA_ID_KEY + ") WHERE r.record_id=?";

            cursor = db.rawQuery(joinQuery, new String[]{String.valueOf(recordId)});

            if (cursor != null && cursor.moveToFirst()) {// If records are found process them
                do {

                    int bodyAreaId = cursor.getInt(0);

                    BodyAreaBuilder bodyAreaBuilder = new BodyAreaBuilder().setBodyAreaId(bodyAreaId);

                    int index = cursor.getColumnIndexOrThrow(DatabaseDefinition.BODY_AREA_NAME_KEY);

                    if (!cursor.isNull(index)) {
                        String name = cursor.getString(index);
                        bodyAreaBuilder = bodyAreaBuilder.setBodyAreaName(name);
                    }


                    bodyAreas.add(bodyAreaBuilder.createBodyArea());
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

        return bodyAreas;
    }

    /**
     * get Top Body Areas
     *
     * @param from  from date
     * @param to    to date
     * @param limit limit
     * @return ArrayList<String>
     */
    public static ArrayList<String> getTopBodyAreas(Timestamp from, Timestamp to, int limit) {
        Log.d("DBBodyAreaDAO", "getTopBodyAreas");

        ArrayList<String> top = new ArrayList<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            String topic = DatabaseDefinition.BODY_AREA_NAME_KEY;//Dynamic
            String topicId = DatabaseDefinition.BODY_AREA_ID_KEY;//Dynamic

            String rightTable = DatabaseDefinition.RECORD_TABLE;
            String leftTable = DatabaseDefinition.BODY_AREA_TABLE;//Dynamic
            String middleTable = DatabaseDefinition.BODY_AREA_RECORD_TABLE;//Dynamic

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
}
