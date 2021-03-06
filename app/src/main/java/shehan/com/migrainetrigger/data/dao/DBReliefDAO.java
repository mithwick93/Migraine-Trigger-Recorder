package shehan.com.migrainetrigger.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.sql.Timestamp;
import java.util.ArrayList;

import shehan.com.migrainetrigger.data.builders.ReliefBuilder;
import shehan.com.migrainetrigger.data.model.Relief;
import shehan.com.migrainetrigger.utility.database.DatabaseDefinition;
import shehan.com.migrainetrigger.utility.database.DatabaseHandler;

import static shehan.com.migrainetrigger.utility.AppUtil.getStringDate;

/**
 * Created by Shehan on 4/13/2016.
 */
public final class DBReliefDAO {

    /**
     * add Relief
     *
     * @param relief relief
     * @return affected no of rows
     */
    public static long addRelief(Relief relief) {
        Log.d("DBReliefDAO", "DB - addRelief");
        try (SQLiteDatabase db = DatabaseHandler.getWritableDatabase()) {

            ContentValues values = new ContentValues();

            values.put(DatabaseDefinition.RELIEF_ID_KEY, relief.getReliefId());

            values.put(DatabaseDefinition.RELIEF_NAME_KEY, relief.getReliefName());

            values.put(DatabaseDefinition.RELIEF_PRIORITY_KEY, relief.getPriority());

            return db.insert(DatabaseDefinition.RELIEF_TABLE, null, values);
        } catch (SQLiteException e) {

            e.printStackTrace();
            return -1;
        }
    }

    /**
     * add Relief Record
     *
     * @param db        SQLiteDatabase
     * @param reliefId  reliefId
     * @param recordId  recordId
     * @param effective effective
     * @return raw id
     * @throws SQLiteException
     */
    public static long addReliefRecord(SQLiteDatabase db, int reliefId, int recordId, boolean effective) throws SQLiteException {
        Log.d("DBReliefDAO", "DB - addReliefRecord");

        ContentValues values = new ContentValues();

        values.put(DatabaseDefinition.RELIEF_RECORD_RELIEF_ID_KEY, reliefId);

        values.put(DatabaseDefinition.RELIEF_RECORD_RECORD_ID_KEY, recordId);

        values.put(DatabaseDefinition.RELIEF_RECORD_EFFECTIVE_KEY, effective ? "t" : "f");

        return db.insert(DatabaseDefinition.RELIEF_RECORD_TABLE, null, values);

    }

    /**
     * delete Relief
     *
     * @param db SQLiteDatabase
     * @param id id
     * @return affected no of rows
     */
    public static long deleteRelief(SQLiteDatabase db, int id) {
        Log.d("DBReliefDAO", "deleteMedicine");

        return (long) db.delete(DatabaseDefinition.RELIEF_TABLE, DatabaseDefinition.RELIEF_ID_KEY + " = ?", new String[]{String.valueOf(id)});
    }

    /**
     * delete Relief Records
     *
     * @param db       SQLiteDatabase
     * @param recordId recordId
     * @return deleted no of rows
     */
    public static long deleteReliefRecords(SQLiteDatabase db, int recordId) {
        Log.d("DBReliefDAO", "DB - deleteReliefRecords");

        return (long) db.delete(DatabaseDefinition.RELIEF_RECORD_TABLE, DatabaseDefinition.RELIEF_RECORD_RECORD_ID_KEY + " = ?", new String[]{String.valueOf(recordId)});
    }

    /**
     * get All Reliefs
     *
     * @return ArrayList<Relief>
     */
    public static ArrayList<Relief> getAllReliefs() {
        Log.d("DBReliefDAO", " DB - getAllReliefs ");
        ArrayList<Relief> reliefArrayList = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            cursor = db.query(DatabaseDefinition.RELIEF_TABLE, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {// If records are found process them
                do {

                    Relief relief = new ReliefBuilder()
                            .setReliefId(cursor.getInt(0))
                            .setReliefName(cursor.getString(1))
                            .setPriority(cursor.getInt(2))
                            .createRelief();
                    reliefArrayList.add(relief);
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
        return reliefArrayList;
    }

    /**
     * get Last Record Id
     *
     * @return last relief record id
     */
    public static int getLastRecordId() {
        Log.d("DBReliefDAO", "getLastRecordId");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        int recordId = -1;
        try {
            String[] projection = {DatabaseDefinition.RELIEF_ID_KEY};
            db = DatabaseHandler.getReadableDatabase();
            cursor = db.query(
                    DatabaseDefinition.RELIEF_TABLE,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    DatabaseDefinition.RELIEF_ID_KEY + " DESC",
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
     * get Reliefs For Record
     *
     * @param recordId recordId
     * @return ArrayList<Relief>
     */
    public static ArrayList<Relief> getReliefsForRecord(int recordId) {
        Log.d("DBReliefDAO", "getReliefsForRecord");

        ArrayList<Relief> reliefs = new ArrayList<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            String joinQuery =
                    "SELECT * FROM " + DatabaseDefinition.RELIEF_TABLE + " a INNER JOIN " + DatabaseDefinition.RELIEF_RECORD_TABLE +
                            " r USING(" + DatabaseDefinition.RELIEF_ID_KEY + ") WHERE r.record_id=?";

            cursor = db.rawQuery(joinQuery, new String[]{String.valueOf(recordId)});

            if (cursor != null && cursor.moveToFirst()) {// If records are found process them
                do {

                    int reliefId = cursor.getInt(0);

                    ReliefBuilder reliefBuilder = new ReliefBuilder().setReliefId(reliefId);

                    int index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RELIEF_NAME_KEY);

                    if (!cursor.isNull(index)) {
                        String name = cursor.getString(index);
                        reliefBuilder = reliefBuilder.setReliefName(name);
                    }

                    index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RELIEF_PRIORITY_KEY);

                    if (!cursor.isNull(index)) {
                        String priority = cursor.getString(index);
                        reliefBuilder = reliefBuilder.setPriority(Integer.parseInt(priority));
                    }

                    index = cursor.getColumnIndexOrThrow(DatabaseDefinition.RELIEF_RECORD_EFFECTIVE_KEY);

                    if (!cursor.isNull(index)) {
                        String effective = cursor.getString(index);
                        reliefBuilder = reliefBuilder.setEffective(effective.equals("t"));
                    }

                    reliefs.add(reliefBuilder.createRelief());
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

        return reliefs;
    }

    /**
     * get Top Effective Reliefs
     *
     * @param from  from date
     * @param to    to date
     * @param limit limit
     * @return ArrayList<String>
     */
    public static ArrayList<String> getTopEffectiveReliefs(Timestamp from, Timestamp to, int limit) {
        Log.d("DBReliefDAO", "getTopEffectiveReliefs");

        ArrayList<String> top = new ArrayList<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            String topic = DatabaseDefinition.RELIEF_NAME_KEY;//Dynamic
            String topicId = DatabaseDefinition.RELIEF_ID_KEY;//Dynamic

            String rightTable = DatabaseDefinition.RECORD_TABLE;
            String leftTable = DatabaseDefinition.RELIEF_TABLE;//Dynamic
            String middleTable = DatabaseDefinition.RELIEF_RECORD_TABLE;//Dynamic

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
     * get Top Reliefs
     *
     * @param from  from date
     * @param to    to date
     * @param limit limit
     * @return ArrayList<String>
     */
    public static ArrayList<String> getTopReliefs(Timestamp from, Timestamp to, int limit) {
        Log.d("DBReliefDAO", "getTopReliefs");

        ArrayList<String> top = new ArrayList<>();

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            String topic = DatabaseDefinition.RELIEF_NAME_KEY;//Dynamic
            String topicId = DatabaseDefinition.RELIEF_ID_KEY;//Dynamic

            String rightTable = DatabaseDefinition.RECORD_TABLE;
            String leftTable = DatabaseDefinition.RELIEF_TABLE;//Dynamic
            String middleTable = DatabaseDefinition.RELIEF_RECORD_TABLE;//Dynamic

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
     * update Relief Record
     *
     * @param relief relief
     * @return no of effected rows
     */
    public static long updateReliefRecord(Relief relief) {
        Log.d("DBReliefDAO", "DB - updateReliefRecord");
        try (SQLiteDatabase db = DatabaseHandler.getWritableDatabase()) {

            ContentValues values = new ContentValues();

            if (relief.getReliefName().equals("") && relief.getPriority() < 0) {
                throw new SQLiteException("Empty update");
            }

            if (!relief.getReliefName().equals("")) {
                values.put(DatabaseDefinition.RELIEF_NAME_KEY, relief.getReliefName());
            }
            if (relief.getPriority() >= 0) {
                values.put(DatabaseDefinition.RELIEF_PRIORITY_KEY, relief.getPriority());
            }


            return (long) db.update(
                    DatabaseDefinition.RELIEF_TABLE,
                    values,
                    DatabaseDefinition.RELIEF_ID_KEY + " = ?",
                    new String[]{String.valueOf(relief.getReliefId())}
            );
        } catch (SQLiteException e) {

            e.printStackTrace();
            return -1;
        }
    }
}
