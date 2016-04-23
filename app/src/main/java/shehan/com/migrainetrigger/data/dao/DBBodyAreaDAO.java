package shehan.com.migrainetrigger.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

import shehan.com.migrainetrigger.data.builders.BodyAreaBuilder;
import shehan.com.migrainetrigger.data.model.BodyArea;
import shehan.com.migrainetrigger.utility.database.DatabaseDefinition;
import shehan.com.migrainetrigger.utility.database.DatabaseHandler;

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

        if (bodyAreaId <= 0 || recordId <= 0) {
            Log.e("DAO-add", "invalid information");
            return -1;
        }

        SQLiteDatabase db = DatabaseHandler.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();

            values.put(DatabaseDefinition.BODY_AREA_RECORD_AREA_ID_KEY, bodyAreaId);

            values.put(DatabaseDefinition.BODY_AREA_RECORD_RECORD_ID_KEY, recordId);

            long row_id = db.insert(DatabaseDefinition.BODY_AREA_RECORD_TABLE, null, values); //Add new account to database

            Log.d("DAO-add", "result : " + row_id);

            return row_id;
        } catch (SQLiteException e) {

            e.printStackTrace();
            return -1;
        } finally {
            db.close();
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

        if (bodyAreaId <= 0 || recordId <= 0) {
            Log.e("DAO-add", "invalid information");
            return -1;
        }

        ContentValues values = new ContentValues();

        values.put(DatabaseDefinition.BODY_AREA_RECORD_AREA_ID_KEY, bodyAreaId);

        values.put(DatabaseDefinition.BODY_AREA_RECORD_RECORD_ID_KEY, recordId);

        long row_id = db.insert(DatabaseDefinition.BODY_AREA_RECORD_TABLE, null, values);

        Log.d("DAO-add", "result : " + row_id);

        return row_id;

    }

    public static int[] getBodyAreasForRecord(int id) {


        return null;
    }

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
}
