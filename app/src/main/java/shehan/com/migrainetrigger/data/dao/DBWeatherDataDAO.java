package shehan.com.migrainetrigger.data.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import shehan.com.migrainetrigger.data.builders.WeatherDataBuilder;
import shehan.com.migrainetrigger.data.model.WeatherData;
import shehan.com.migrainetrigger.utility.database.DatabaseDefinition;
import shehan.com.migrainetrigger.utility.database.DatabaseHandler;

/**
 * Created by Shehan on 4/13/2016.
 */
public final class DBWeatherDataDAO {

    /**
     * add Weather Data
     *
     * @param db          SQLiteDatabase
     * @param recordId    recordId
     * @param weatherData weatherData
     * @return raw id
     * @throws SQLiteException
     */
    public static long addWeatherData(SQLiteDatabase db, int recordId, WeatherData weatherData) throws SQLiteException {
        Log.d("DBWeatherDataDAO", "DB - addTriggerRecord");

        if (weatherData == null) {
            Log.e("DAO-add", "invalid information");
            return -1;
        }

        ContentValues values = new ContentValues();

        values.put(DatabaseDefinition.WEATHER_DATA_TEMPERATURE_KEY, weatherData.getTemperature());

        values.put(DatabaseDefinition.WEATHER_DATA_HUMIDITY_KEY, weatherData.getHumidity());

        values.put(DatabaseDefinition.WEATHER_DATA_PRESSURE_KEY, weatherData.getPressure());

        values.put(DatabaseDefinition.WEATHER_DATA_RECORD_ID_KEY, recordId);

        if (weatherData.getWeatherDataId() > -1) {
            values.put(DatabaseDefinition.WEATHER_DATA_ID_KEY, weatherData.getWeatherDataId());
        }

        long row_id = db.insert(DatabaseDefinition.WEATHER_DATA_TABLE, null, values);

        return row_id;

    }


    /**
     * delete Weather Record
     *
     * @param db       SQLiteDatabase
     * @param recordId recordId
     * @return deleted no of rows
     */
    public static long deleteWeatherRecord(SQLiteDatabase db, int recordId) {
        Log.d("DBWeatherDataDAO", "DB - deleteWeatherRecord");

        long row_id = db.delete(DatabaseDefinition.WEATHER_DATA_TABLE, DatabaseDefinition.WEATHER_DATA_RECORD_ID_KEY + " = ?", new String[]{String.valueOf(recordId)});
        return row_id;
    }

    /**
     * get Last Weather Record Id
     *
     * @return last record id
     */
    public static int getLastRecordId() {
        Log.d("DBWeatherDataDAO", "getLastRecordId");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        int recordId = -1;
        try {
            String[] projection = {DatabaseDefinition.WEATHER_DATA_ID_KEY};
            db = DatabaseHandler.getReadableDatabase();
            cursor = db.query(
                    DatabaseDefinition.WEATHER_DATA_TABLE,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    DatabaseDefinition.WEATHER_DATA_ID_KEY + " DESC",
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
     * get Weather Data By RecordId
     *
     * @param recordId recordId
     * @return WeatherData
     */
    public static WeatherData getWeatherDataByRecordId(int recordId) {
        Log.d("DBWeatherDataDAO", "getWeatherDataById");

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = DatabaseHandler.getReadableDatabase();

            cursor = db.query(DatabaseDefinition.WEATHER_DATA_TABLE, null, DatabaseDefinition.WEATHER_DATA_RECORD_ID_KEY + " = ?", new String[]{String.valueOf(recordId)}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {// If records are found process them


                int weatherId = cursor.getInt(0);

                WeatherDataBuilder weatherDataBuilder = new WeatherDataBuilder().setWeatherDataId(weatherId);

                int index = cursor.getColumnIndexOrThrow(DatabaseDefinition.WEATHER_DATA_HUMIDITY_KEY);

                if (!cursor.isNull(index)) {
                    String humidity = cursor.getString(index);
                    weatherDataBuilder = weatherDataBuilder.setHumidity(Double.valueOf(humidity));
                }

                index = cursor.getColumnIndexOrThrow(DatabaseDefinition.WEATHER_DATA_PRESSURE_KEY);

                if (!cursor.isNull(index)) {
                    String pressure = cursor.getString(index);
                    weatherDataBuilder = weatherDataBuilder.setPressure(Double.valueOf(pressure));
                }

                index = cursor.getColumnIndexOrThrow(DatabaseDefinition.WEATHER_DATA_TEMPERATURE_KEY);

                if (!cursor.isNull(index)) {
                    String temperature = cursor.getString(index);
                    weatherDataBuilder = weatherDataBuilder.setTemperature(Double.valueOf(temperature));
                }

                weatherDataBuilder.setRecordId(recordId);

                return weatherDataBuilder.createWeatherData();
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
