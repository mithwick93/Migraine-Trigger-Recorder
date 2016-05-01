package shehan.com.migrainetrigger.controller;

import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Log;

import java.sql.Timestamp;
import java.util.ArrayList;

import shehan.com.migrainetrigger.data.dao.DBRecordDAO;
import shehan.com.migrainetrigger.data.dao.DBTransactionHandler;
import shehan.com.migrainetrigger.data.model.Record;
import shehan.com.migrainetrigger.utility.AppUtil;

/**
 * Created by Shehan on 4/13/2016.
 */
public class RecordController {

    /**
     * @param record
     * @param recordLevel
     * @return
     */
    public static boolean addNewRecord(Record record, int recordLevel) {
        Log.d("RecordController", "addNewRecord");
        return DBTransactionHandler.addRecordTransaction(record, recordLevel);
    }

    /**
     * @return
     */
    public static int getLastId() {
        return DBRecordDAO.getLastRecordId();
    }

    /**
     * @param id
     * @return
     */
    public static Record getRecordById(int id) {

        return DBRecordDAO.getRecord(id);
    }

    /**
     * @param id
     * @return
     */
    @Nullable
    public static Record getRecordAll(int id) {
        Log.d("RecordController", "getRecordAll");
        Record record = DBRecordDAO.getRecord(id);
        if (record != null) {

            if (record.getLocationId() > -1) {//set location
                record.setLocation(LocationController.getLocationById(record.getLocationId()));
            }

            record.setWeatherData(WeatherDataController.getWeatherDataByRecordId(id));//set weather data

            record.setActivities(LifeActivityController.getActivitiesForRecord(id));//set activities

            record.setMedicines(MedicineController.getMedicinesForRecord(id));//Set medicine

            record.setReliefs(ReliefController.getReliefsForRecord(id));//Set reliefs

            record.setSymptoms(SymptomController.getSymptomsForRecord(id));//Set symptoms

            record.setTriggers(TriggerController.getTriggersForRecord(id));//Set triggers

            return record;
        }
        Log.e("RecordController", "getRecordAll -No such record");

        return null;
    }


    /**
     * @return
     */
    public static ArrayList<Record> getAllRecords() {

        return DBRecordDAO.getAllRecords();
    }


    public static String getStatus() {
        Log.d("RecordController", "getStatus");
        String status;

        Record lastRecord = DBRecordDAO.getLastRecord();
        if (lastRecord != null) {
            if (lastRecord.getEndTime() != null) {
                Timestamp endTime = lastRecord.getEndTime();
                Timestamp currentTime = new Timestamp(new java.util.Date().getTime());

                long difference = currentTime.getTime() - endTime.getTime();
                long differenceInSeconds = difference / DateUtils.SECOND_IN_MILLIS;
                String duration = AppUtil.getFriendlyDuration(differenceInSeconds);

                status = "Migraine free for : " + duration;
            } else {
                status = "Get well soon ";
            }
        } else {
            status = "No migraine records yet.";
        }

        return status;
    }

    public static void deleteRecord() {

    }

    public static void updateRecord(Record record) {

    }

}
