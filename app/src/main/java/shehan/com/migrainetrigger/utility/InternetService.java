package shehan.com.migrainetrigger.utility;


import java.sql.Timestamp;

/**
 * Created by Shehan on 4/13/2016.
 */
public interface InternetService {

    /**
     * Retrieve weather data
     * Recommended to run in separate thread. Require internet access
     *
     * @param wLatitude  latitude of location
     * @param wLongitude longitude of location
     * @param wTimestamp Time of required weather
     */
    void getWeatherData(double wLatitude, double wLongitude, Timestamp wTimestamp);
}
