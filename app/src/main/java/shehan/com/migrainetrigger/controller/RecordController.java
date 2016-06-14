package shehan.com.migrainetrigger.controller;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.util.ArrayList;

import shehan.com.migrainetrigger.R;
import shehan.com.migrainetrigger.data.dao.DBRecordDAO;
import shehan.com.migrainetrigger.data.dao.DBTransactionHandler;
import shehan.com.migrainetrigger.data.model.BodyArea;
import shehan.com.migrainetrigger.data.model.LifeActivity;
import shehan.com.migrainetrigger.data.model.Location;
import shehan.com.migrainetrigger.data.model.Medicine;
import shehan.com.migrainetrigger.data.model.Record;
import shehan.com.migrainetrigger.data.model.Relief;
import shehan.com.migrainetrigger.data.model.Symptom;
import shehan.com.migrainetrigger.data.model.Trigger;
import shehan.com.migrainetrigger.utility.AppUtil;
import shehan.com.migrainetrigger.view.model.RecordViewData;

/**
 * Created by Shehan on 4/13/2016.
 */
public class RecordController {


    public static boolean addNewRecord(Record record, int recordLevel) {
        Log.d("RecordController", "addNewRecord");
        return DBTransactionHandler.addRecord(record, recordLevel);
    }

    public static boolean deleteRecord(int recordId) {
        return DBTransactionHandler.deleteRecord(recordId);
    }

    public static ArrayList<Record> getAllRecords() {

        return DBRecordDAO.getAllRecords();
    }

    private static ArrayList<Record> getAllRecordsOrderByDate() {
        return DBRecordDAO.getAllRecordsOrderByDate();
    }

    public static ArrayList<String[]> getAllRecordsOrderByDateRAW() {
        return DBRecordDAO.getAllRecordsOrderByDateRAW();
    }

    /**
     * get filtered record list
     *
     * @param filterList           filters to apply
     * @param fullDetailRecordList full record list
     * @return filtered record list
     */
    private static ArrayList<Record> getFilteredRecords(@NotNull ArrayList<ArrayList<String>> filterList, @NotNull ArrayList<Record> fullDetailRecordList) {
        ArrayList<Record> filteredRecordList = new ArrayList<>();//get filtered record list

        //Filtering process
        recordLoop:
        for (Record record : fullDetailRecordList) {
            {
                //Body areas filter
                ArrayList<String> bodyAreaFilters = filterList.get(0);
                ArrayList<BodyArea> bodyAreas = record.getBodyAreas();
                if (bodyAreas != null && bodyAreas.size() > 0) {
                    for (BodyArea bodyArea : bodyAreas) {
                        if (bodyAreaFilters.contains(bodyArea.getBodyAreaName())) {
                            //This record matches filter, add to filteredRecordList, no need to add again , continue
                            filteredRecordList.add(record);
                            continue recordLoop;
                        }
                    }
                }
            }

            {
                //Life lifeActivities filter
                ArrayList<String> activityFilters = filterList.get(1);
                ArrayList<LifeActivity> lifeActivities = record.getActivities();
                if (lifeActivities != null && lifeActivities.size() > 0) {
                    for (LifeActivity lifeActivity : lifeActivities) {
                        if (activityFilters.contains(lifeActivity.getActivityName())) {
                            //This record matches filter, add to filteredRecordList, no need to add again , continue
                            filteredRecordList.add(record);
                            continue recordLoop;
                        }
                    }
                }
            }

            {
                //Location  filter
                ArrayList<String> locationFilters = filterList.get(2);
                Location location = record.getLocation();
                if (location != null) {
                    if (locationFilters.contains(location.getLocationName())) {
                        //This record matches filter, add to filteredRecordList, no need to add again , continue
                        filteredRecordList.add(record);
                        continue;
                    }
                }
            }

            {
                //Medicine filter
                ArrayList<String> medicineFilters = filterList.get(3);
                ArrayList<Medicine> medicines = record.getMedicines();
                if (medicines != null && medicines.size() > 0) {
                    for (Medicine medicine : medicines) {
                        if (medicineFilters.contains(medicine.getMedicineName())) {
                            //This record matches filter, add to filteredRecordList, no need to add again , continue
                            filteredRecordList.add(record);
                            continue recordLoop;
                        }
                    }
                }
            }

            {
                //Relief filter
                ArrayList<String> reliefFilters = filterList.get(4);
                ArrayList<Relief> reliefs = record.getReliefs();
                if (reliefs != null && reliefs.size() > 0) {
                    for (Relief relief : reliefs) {
                        if (reliefFilters.contains(relief.getReliefName())) {
                            //This record matches filter, add to filteredRecordList, no need to add again , continue
                            filteredRecordList.add(record);
                            continue recordLoop;
                        }
                    }
                }
            }

            {
                //Symptom filter
                ArrayList<String> symptomFilters = filterList.get(5);
                ArrayList<Symptom> symptoms = record.getSymptoms();
                if (symptoms != null && symptoms.size() > 0) {
                    for (Symptom symptom : symptoms) {
                        if (symptomFilters.contains(symptom.getSymptomName())) {
                            //This record matches filter, add to filteredRecordList, no need to add again , continue
                            filteredRecordList.add(record);
                            continue recordLoop;
                        }
                    }
                }
            }

            {
                //Trigger filter
                ArrayList<String> triggerFilters = filterList.get(6);
                ArrayList<Trigger> triggers = record.getTriggers();
                if (triggers != null && triggers.size() > 0) {
                    for (Trigger trigger : triggers) {
                        if (triggerFilters.contains(trigger.getTriggerName())) {
                            //This record matches filter, add to filteredRecordList, no need to add again , continue
                            filteredRecordList.add(record);
                            continue recordLoop;
                        }
                    }
                }
            }
        }

        return filteredRecordList;
    }

    /**
     * get start time of 1st record
     *
     * @return timestamp
     */
    public static Timestamp getFirstRecordStartTimestamp() {
        Record firstRecord = DBRecordDAO.getFirstRecord();
        if (firstRecord != null && firstRecord.getStartTime() != null) {
            return firstRecord.getStartTime();
        } else {
            Log.d("RecordController", "No first record found, creating default");
            return AppUtil.getTimeStampDay("01/01/2016");
        }
    }

    public static int getLastId() {
        return DBRecordDAO.getLastRecordId();
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

            if (record.getLocationId() > 0) {//set location
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

    public static Record getRecordById(int id) {
        return DBRecordDAO.getRecord(id);
    }

    public static RecordViewData[] getRecordViewData() {
        return getRecordViewData(getAllRecordsOrderByDate());

    }

    /**
     * Convert arrayList to RecordViewData array
     *
     * @param recordArrayList recordArrayList
     * @return RecordViewData[]
     */
    private static RecordViewData[] getRecordViewData(@NonNull ArrayList<Record> recordArrayList) {

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

    /**
     * get filtered record data
     *
     * @param filterList filter list to apply
     * @return record view data array
     */
    @Nullable
    public static RecordViewData[] getRecordViewDataFiltered(@NotNull ArrayList<ArrayList<String>> filterList) {
        Log.d("RecordController", "getRecordViewDataFiltered");
        if (filterList.size() == 7) {

            ArrayList<Record> fullDetailRecordList = new ArrayList<>();//get full detailed record list
            {
                ArrayList<Record> recordArrayList = getAllRecordsOrderByDate();
                if (recordArrayList.size() < 1) return null; //check if no of records are 0

                for (Record record : recordArrayList) {
                    fullDetailRecordList.add(getRecordAll(record.getRecordId()));
                }
            }
            if (fullDetailRecordList.size() < 1) return null; //check if no of records are 0

            ArrayList<Record> filteredRecordList = getFilteredRecords(filterList, fullDetailRecordList);

            if (filteredRecordList.size() < 1) return null; //check if no of records are 0

            return getRecordViewData(filteredRecordList);

        } else {
            Log.e("RecordController", "Incorrect no of filters , must be 7");
        }
        return null;

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
                Timestamp startTime = lastRecord.getStartTime();
                Timestamp currentTime = new Timestamp(new java.util.Date().getTime());

                long difference = currentTime.getTime() - startTime.getTime();
                long differenceInSeconds = difference / DateUtils.SECOND_IN_MILLIS;
                String duration = AppUtil.getFriendlyDuration(differenceInSeconds);
                status = "Suffering from migraine for " + duration;
            }
        } else {
            status = "No migraine records yet.";
        }

        return status;
    }

    public static boolean updateRecord(Record record) {
        return DBTransactionHandler.updateRecord(record);
    }

}
