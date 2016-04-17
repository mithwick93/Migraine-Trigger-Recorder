package shehan.com.migrainetrigger.data.builders;

import java.sql.Timestamp;
import java.util.ArrayList;

import shehan.com.migrainetrigger.data.model.Activity;
import shehan.com.migrainetrigger.data.model.BodyArea;
import shehan.com.migrainetrigger.data.model.Location;
import shehan.com.migrainetrigger.data.model.Medicine;
import shehan.com.migrainetrigger.data.model.Record;
import shehan.com.migrainetrigger.data.model.Relief;
import shehan.com.migrainetrigger.data.model.Symptom;
import shehan.com.migrainetrigger.data.model.Trigger;
import shehan.com.migrainetrigger.data.model.WeatherData;

public class RecordBuilder {
    private int recordId;
    private int intensity;
    private Timestamp startTime;
    private Timestamp endTime;
    private ArrayList<Activity> activities;
    private ArrayList<BodyArea> bodyAreas;
    private Location location;
    private ArrayList<Medicine> medicines;
    private ArrayList<Relief> reliefs;
    private ArrayList<Symptom> symptoms;
    private ArrayList<Trigger> triggers;
    private WeatherData weatherData;

    public RecordBuilder setRecordId(int recordId) {
        this.recordId = recordId;
        return this;
    }

    public RecordBuilder setIntensity(int intensity) {
        this.intensity = intensity;
        return this;
    }

    public RecordBuilder setStartTime(Timestamp startTime) {
        this.startTime = startTime;
        return this;
    }

    public RecordBuilder setEndTime(Timestamp endTime) {
        this.endTime = endTime;
        return this;
    }

    public RecordBuilder setActivities(ArrayList<Activity> activities) {
        this.activities = activities;
        return this;
    }

    public RecordBuilder setBodyAreas(ArrayList<BodyArea> bodyAreas) {
        this.bodyAreas = bodyAreas;
        return this;
    }

    public RecordBuilder setLocation(Location location) {
        this.location = location;
        return this;
    }

    public RecordBuilder setMedicines(ArrayList<Medicine> medicines) {
        this.medicines = medicines;
        return this;
    }

    public RecordBuilder setReliefs(ArrayList<Relief> reliefs) {
        this.reliefs = reliefs;
        return this;
    }

    public RecordBuilder setSymptoms(ArrayList<Symptom> symptoms) {
        this.symptoms = symptoms;
        return this;
    }

    public RecordBuilder setTriggers(ArrayList<Trigger> triggers) {
        this.triggers = triggers;
        return this;
    }

    public RecordBuilder setWeatherData(WeatherData weatherData) {
        this.weatherData = weatherData;
        return this;
    }

    public Record createRecord() {
        return new Record(recordId, intensity, startTime, endTime, activities, bodyAreas, location, medicines, reliefs, symptoms, triggers, weatherData);
    }
}