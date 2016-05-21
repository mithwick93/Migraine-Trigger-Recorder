package shehan.com.migrainetrigger.data.builders;

import shehan.com.migrainetrigger.data.model.WeatherData;

public class WeatherDataBuilder {
    private double humidity;
    private double pressure;
    private int recordId;
    private double temperature;
    private int weatherDataId;

    public WeatherData createWeatherData() {
        return new WeatherData(weatherDataId, humidity, pressure, temperature, recordId);
    }

    public WeatherDataBuilder setHumidity(double humidity) {
        this.humidity = humidity;
        return this;
    }

    public WeatherDataBuilder setPressure(double pressure) {
        this.pressure = pressure;
        return this;
    }

    public WeatherDataBuilder setRecordId(int recordId) {
        this.recordId = recordId;
        return this;
    }

    public WeatherDataBuilder setTemperature(double temperature) {
        this.temperature = temperature;
        return this;
    }

    public WeatherDataBuilder setWeatherDataId(int weatherDataId) {
        this.weatherDataId = weatherDataId;
        return this;
    }
}