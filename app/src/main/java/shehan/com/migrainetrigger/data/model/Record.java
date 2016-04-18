package shehan.com.migrainetrigger.data.model;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by Shehan on 4/13/2016.
 */
public class Record {
    private int recordId;
    private int intensity;
    private Timestamp startTime;
    private Timestamp endTime;
    private int locationId;

    //Holders
    private ArrayList<LifeActivity> activities;
    private ArrayList<BodyArea> bodyAreas;
    private Location location;
    private ArrayList<Medicine> medicines;
    private ArrayList<Relief> reliefs;
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

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ArrayList<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(ArrayList<Medicine> medicines) {
        this.medicines = medicines;
    }

    public ArrayList<Relief> getReliefs() {
        return reliefs;
    }

    public void setReliefs(ArrayList<Relief> reliefs) {
        this.reliefs = reliefs;
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
