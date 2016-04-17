package shehan.com.migrainetrigger.data.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import shehan.com.migrainetrigger.data.model.Record;
import shehan.com.migrainetrigger.utility.database.DatabaseDefinition;
import shehan.com.migrainetrigger.utility.database.DatabaseHandler;

/**
 * Created by Shehan on 4/13/2016.
 */
public final class DBRecordDAO {

    public static long addRecordToDB(Record record) {
        Log.d("DAO-add", "Add record");

        if (record == null) {
            Log.e("DAO-add", "null record");
            return -1;
        }

        SQLiteDatabase db = DatabaseHandler.getWritableDatabase();
        try {


            ContentValues values = new ContentValues();

            //Start time
            if (record.getStartTime() != null) {
                values.put(DatabaseDefinition.RECORD_START_TIME_KEY, record.getStartTime().getTime());
            }
            //End time
            if (record.getEndTime() != null) {
                values.put(DatabaseDefinition.RECORD_END_TIME_KEY, record.getEndTime().getTime());
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

            Log.d("DAO-add-", "result : " + row_id);

            return row_id;
        } catch (SQLiteException e) {

            e.printStackTrace();
            return -1;
        } finally {
            db.close();
        }
    }


}
