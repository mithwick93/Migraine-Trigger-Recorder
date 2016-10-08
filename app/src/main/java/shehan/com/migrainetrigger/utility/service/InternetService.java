package shehan.com.migrainetrigger.utility.service;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONObject;

import java.sql.Timestamp;

import cz.msebera.android.httpclient.Header;
import shehan.com.migrainetrigger.BuildConfig;
import shehan.com.migrainetrigger.utility.AppUtil;

/**
 * Internet service to obtain weather data
 */
public class InternetService {
    private InternetServiceListener internetServiceListener;

    public InternetService(InternetServiceListener internetServiceListener) {
        this.internetServiceListener = internetServiceListener;
    }

    /**
     * Get response from dark sky api response for a weather data request
     *
     * @param latitude  latitude of location
     * @param longitude longitude of location
     * @param timestamp unix timestamp of time in history
     */
    public void getWeatherData(double latitude, double longitude, Timestamp timestamp) {
        SyncHttpClient client = new SyncHttpClient();

        client.get(getUrl(latitude, longitude, timestamp), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("InternetService", "statusCode success " + statusCode);
                internetServiceListener.onInternetResponseReceived(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Log.e("InternetService", "statusCode fail " + statusCode);
                internetServiceListener.onInternetResponseReceived(null);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject response) {
                Log.e("InternetService", "statusCode fail " + statusCode);
                internetServiceListener.onInternetResponseReceived(null);
            }
        });
    }

    /**
     * @param latitude  latitude of location
     * @param longitude longitude of location
     * @param timestamp unix timestamp of time in history
     * @return requestUrl
     */
    private String getUrl(double latitude, double longitude, Timestamp timestamp) {
        String latitudeString = String.valueOf(latitude);
        String longitudeString = String.valueOf(longitude);
        String timeString = AppUtil.getStringWeatherDate(timestamp);
        String units = "si";

        String darkSkyApiKey = BuildConfig.FORECAST_API_KEY;
        String darkskyBaseUrl = "https://api.darksky.net/forecast/";

        String requestUrl = darkskyBaseUrl + darkSkyApiKey + "/" + latitudeString + "," + longitudeString + "," + timeString + "?units=" + units;

        // Log.i("InternetService", "requestUrl " + requestUrl);

        return requestUrl;
    }

    /**
     * interface to communicate upon receiving dark sky api response
     */
    public interface InternetServiceListener {
        void onInternetResponseReceived(JSONObject response);
    }
}