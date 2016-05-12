package shehan.com.migrainetrigger.controller;

import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Log;

import java.sql.Timestamp;
import java.util.ArrayList;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.data.dao.DBRecordDAO;
import shehan.com.migrainetrigger.data.dao.DBTransactionHandler;
import shehan.com.migrainetrigger.data.model.Record;
import shehan.com.migrainetrigger.utility.AppUtil;
import shehan.com.migrainetrigger.view.model.RecordViewData;

/**
 * Created by Shehan on 4/13/2016.
 */
public class RecordController {


    public static boolean addNewRecord(Record record, int recordLevel) {
        Log.d("RecordController", "addNewRecord");
        return DBTransactionHandler.addRecordTransaction(record, recordLevel);
    }

    public static boolean updateRecord(Record record) {
        return DBTransactionHandler.updateRecord(record);
    }

    public static boolean deleteRecord(int recordId) {
        return DBTransactionHandler.deleteRecord(recordId);
    }

    public static int getLastId() {
        return DBRecordDAO.getLastRecordId();
    }


    public static Timestamp getFirstRecordStartTimestamp() {
        Record firstRecord = DBRecordDAO.getFirstRecord();
        if (firstRecord != null && firstRecord.getStartTime() != null) {
            return firstRecord.getStartTime();
        } else {
            Log.d("RecordController", "No first record found, creating default");
            return AppUtil.getTimeStampDay("01/01/2016");
        }
    }

    public static Record getRecordById(int id) {

        return DBRecordDAO.getRecord(id);
    }

    /**
     * Get all record detail when record id is known
     *
     * @param recordId relevant record ID
     * @return Record
     */
    @Nullable
    public static Record getRecordAll(int recordId) {
        Log.d("RecordController", "getRecordAll");
        Record record = DBRecordDAO.getRecord(recordId);
        if (record != null) {

            if (record.getLocationId() > -1) {//set location
                record.setLocation(LocationController.getLocationById(record.getLocationId()));
            }

            record.setWeatherData(WeatherDataController.getWeatherDataByRecordId(recordId));//set weather data

            record.setActivities(LifeActivityController.getActivitiesForRecord(recordId));//set activities

            record.setBodyAreas(BodyAreaController.getBodyAreasForRecord(recordId));//set body areas

            record.setMedicines(MedicineController.getMedicinesForRecord(recordId));//Set medicine

            record.setReliefs(ReliefController.getReliefsForRecord(recordId));//Set reliefs

            record.setSymptoms(SymptomController.getSymptomsForRecord(recordId));//Set symptoms

            record.setTriggers(TriggerController.getTriggersForRecord(recordId));//Set triggers

            return record;
        }
        Log.e("RecordController", "getRecordAll -No such record");

        return null;
    }

    public static ArrayList<Record> getAllRecords() {

        return DBRecordDAO.getAllRecords();
    }

    public static ArrayList<Record> getAllRecordsOrderByDate() {

        return DBRecordDAO.getAllRecordsOrderByDate();
    }

    /**
     * Load status from db and show
     *
     * @return status string
     */
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

                status = "Migraine free for\n " + duration;
            } else {
                status = "Get well soon ";
            }
        } else {
            status = "No migraine records yet.";
        }

        return status;
    }

    public static RecordViewData[] getRecordViewData() {
        ArrayList<Record> recordArrayList = getAllRecordsOrderByDate();
        RecordViewData recordViewData[] = new RecordViewData[recordArrayList.size()];

        //Load data to recordViewData[]  from recordArrayList
        for (int i = 0; i < recordArrayList.size(); i++) {
            Record record = recordArrayList.get(i);
            int recordId = record.getRecordId();
            String start = "-";
            String duration = "-";
            int intensity;

            if (record.getStartTime() != null) {
                start = AppUtil.getFriendlyStringDate(record.getStartTime());

                if (record.getEndTime() != null) {
                    Timestamp startTime = record.getStartTime();
                    Timestamp endTime = record.getEndTime();

                    if (startTime != null) {
                        if (endTime != null) {
                            long difference = endTime.getTime() - startTime.getTime();
                            long differenceInSeconds = difference / DateUtils.SECOND_IN_MILLIS;
                            duration = AppUtil.getFriendlyDuration(differenceInSeconds);
                        }
                    }
                }
            }


            switch (record.getIntensity()) {//Set intensity pic
                case 1:
                    intensity = R.drawable.num_1;
                    break;
                case 2:
                    intensity = R.drawable.num_2;
                    break;
                case 3:
                    intensity = R.drawable.num_3;
                    break;
                case 4:
                    intensity = R.drawable.num_4;
                    break;
                case 5:
                    intensity = R.drawable.num_5;
                    break;
                case 6:
                    intensity = R.drawable.num_6;
                    break;
                case 7:
                    intensity = R.drawable.num_7;
                    break;
                case 8:
                    intensity = R.drawable.num_8;
                    break;
                case 9:
                    intensity = R.drawable.num_9;
                    break;
                case 10:
                    intensity = R.drawable.num_10;
                    break;
                default:
                    intensity = 0;
                    break;
            }

            recordViewData[i] = new RecordViewData(recordId, start, duration, intensity);
        }

        return recordViewData;
    }

}
