package shehan.com.migrainetrigger.data.model;

/**
 * Created by Shehan on 4/13/2016.
 */
public class WeatherData {

    private double humidity;
    private double pressure;
    private int recordId;
    private double temperature;
    private int weatherDataId;

    public WeatherData(int weatherDataId, double humidity, double pressure, double temperature, int recordId) {
        this.weatherDataId = weatherDataId;
        this.humidity = humidity;
        this.pressure = pressure;
        this.temperature = temperature;
        this.recordId = recordId;
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

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getWeatherDataId() {
        return weatherDataId;
    }

    public void setWeatherDataId(int weatherDataId) {
        this.weatherDataId = weatherDataId;
    }
}
