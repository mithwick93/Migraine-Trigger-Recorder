package shehan.com.migrainetrigger.data.model;

/**
 * Created by Shehan on 4/13/2016.
 */
public class WeatherData {

    private int weatherDataId;
    private double humidity;
    private double pressure;
    private double temperature;

    public WeatherData(int weatherDataId, double humidity, double pressure, double temperature) {
        this.weatherDataId = weatherDataId;
        this.humidity = humidity;
        this.pressure = pressure;
        this.temperature = temperature;
    }

    public int getWeatherDataId() {
        return weatherDataId;
    }

    public void setWeatherDataId(int weatherDataId) {
        this.weatherDataId = weatherDataId;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
