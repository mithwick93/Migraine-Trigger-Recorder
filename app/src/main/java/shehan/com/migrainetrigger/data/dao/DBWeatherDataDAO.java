package shehan.com.migrainetrigger.data.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import shehan.com.migrainetrigger.data.model.WeatherData;
import shehan.com.migrainetrigger.utility.database.DatabaseDefinition;

/**
 * Created by Shehan on 4/13/2016.
 */
public final class DBWeatherDataDAO {

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


        long row_id = db.insert(DatabaseDefinition.WEATHER_DATA_TABLE, null, values);

        Log.d("DAO-add", "result : " + row_id);

        return row_id;

    }
}
