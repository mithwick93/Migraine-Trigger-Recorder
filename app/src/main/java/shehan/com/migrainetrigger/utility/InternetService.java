package shehan.com.migrainetrigger.utility;


import android.util.Log;

import com.johnhiott.darkskyandroidlib.RequestBuilder;
import com.johnhiott.darkskyandroidlib.models.Request;
import com.johnhiott.darkskyandroidlib.models.WeatherResponse;

import java.sql.Timestamp;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import shehan.com.migrainetrigger.data.builders.WeatherDataBuilder;
import shehan.com.migrainetrigger.data.model.WeatherData;

import static shehan.com.migrainetrigger.utility.appUtil.getStringWeatherDate;

/**
 * Created by Shehan on 4/13/2016.
 */
public class InternetService {

    private static WeatherData weatherData = null;

    /**
     * Retrieve weather data
     * Recommended to run in separate thread. Require internet access
     *
     * @param latitude  latitude of location
     * @param longitude longitude of location
     * @param timestamp Time of required weather
     * @return Weather data object. Can be null
     */
    public static WeatherData getWeatherData(double latitude, double longitude, Timestamp timestamp) {
        Log.d("InternetService", "getWeatherData");


        RequestBuilder weather = new RequestBuilder();
        Request request = new Request();
        request.setLat(String.valueOf(latitude));
        request.setLng(String.valueOf(longitude));
        request.setTime(getStringWeatherDate(timestamp));
        request.setUnits(Request.Units.SI);
        request.setLanguage(Request.Language.PIG_LATIN);
        request.addExcludeBlock(Request.Block.CURRENTLY);
        request.removeExcludeBlock(Request.Block.CURRENTLY);

        weather.getWeather(request, new Callback<WeatherResponse>()

                {
                    @Override
                    public void success(WeatherResponse weatherResponse, Response response) {

                        weatherData = new WeatherDataBuilder()
                                .setHumidity(Double.valueOf(weatherResponse.getCurrently().getHumidity()))
                                .setPressure(Double.valueOf(weatherResponse.getCurrently().getPressure()))
                                .setTemperature(weatherResponse.getCurrently().getTemperature())
                                .createWeatherData();

                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        Log.d("InternetService", "Error while calling: " + retrofitError.getUrl());
                        Log.d("InternetService", retrofitError.toString());
                    }
                }
        );

        return weatherData;
    }
}
