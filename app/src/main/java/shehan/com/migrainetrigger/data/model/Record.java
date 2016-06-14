package shehan.com.migrainetrigger.data.model;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by Shehan on 4/13/2016.
 * Wrapper model to contain all data about a migraine record
 */
public class Record {
    //Holders
    private ArrayList<LifeActivity> activities;
    private ArrayList<BodyArea> bodyAreas;
    private Timestamp endTime;
    private int intensity;
    private Location location;
    private int locationId;
    private ArrayList<Medicine> medicines;
    private int recordId;
    private ArrayList<Relief> reliefs;
    private Timestamp startTime;
    private ArrayList<Symptom> symptoms;
    private ArrayList<Trigger> triggers;
    private WeatherData weatherData;

    public Record(int recordId, int intensity, Timestamp startTime, Timestamp endTime, int locationId, ArrayList<LifeActivity> activities, ArrayList<BodyArea> bodyAreas, Location location, ArrayList<Medicine> medicines, ArrayList<Relief> reliefs, ArrayList<Symptom> symptoms, ArrayList<Trigger> triggers, WeatherData weatherData) {
        this.recordId = recordId;
        this.intensity = intensity;
        this.startTime = startTime;
        this.endTime = endTime;
        this.locationId = locationId;
        this.activities = activities;
        this.bodyAreas = bodyAreas;
        this.location = location;
        this.medicines = medicines;
        this.reliefs = reliefs;
        this.symptoms = symptoms;
        this.triggers = triggers;
        this.weatherData = weatherData;
    }

    public ArrayList<LifeActivity> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<LifeActivity> activities) {
        this.activities = activities;
    }

    public ArrayList<BodyArea> getBodyAreas() {
        return bodyAreas;
    }

    public void setBodyAreas(ArrayList<BodyArea> bodyAreas) {
        this.bodyAreas = bodyAreas;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public ArrayList<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(ArrayList<Medicine> medicines) {
        this.medicines = medicines;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public ArrayList<Relief> getReliefs() {
        return reliefs;
    }

    public void setReliefs(ArrayList<Relief> reliefs) {
        this.reliefs = reliefs;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public ArrayList<Symptom> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(ArrayList<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    public ArrayList<Trigger> getTriggers() {
        return triggers;
    }

    public void setTriggers(ArrayList<Trigger> triggers) {
        this.triggers = triggers;
    }

    public WeatherData getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(WeatherData weatherData) {
        this.weatherData = weatherData;
    }
}
