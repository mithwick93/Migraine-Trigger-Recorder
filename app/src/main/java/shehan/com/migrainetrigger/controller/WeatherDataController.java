package shehan.com.migrainetrigger.controller;

import shehan.com.migrainetrigger.data.dao.DBWeatherDataDAO;
import shehan.com.migrainetrigger.data.model.WeatherData;

/**
 * Created by Shehan on 4/13/2016.
 */
public class WeatherDataController {

    public static WeatherData getWeatherDataByRecordId(int recordId) {

        return DBWeatherDataDAO.getWeatherDataByRecordId(recordId);
    }

}
