package shehan.com.migrainetrigger.data.dao;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import shehan.com.migrainetrigger.data.model.BodyArea;
import shehan.com.migrainetrigger.data.model.LifeActivity;
import shehan.com.migrainetrigger.data.model.Medicine;
import shehan.com.migrainetrigger.data.model.Record;
import shehan.com.migrainetrigger.data.model.Relief;
import shehan.com.migrainetrigger.data.model.Symptom;
import shehan.com.migrainetrigger.data.model.Trigger;
import shehan.com.migrainetrigger.utility.database.DatabaseHandler;

/**
 * Created by Shehan on 4/18/2016.
 */
public class DBTransactionHandler {

    /**
     * Add new record in a transaction
     *
     * @param record      New record
     * @param recordLevel record level (basic - 0 ,intermediate -1 , full - 2 )
     * @return weather transaction is successful
     */
    public static boolean addRecordTransaction(Record record, int recordLevel) {
        Log.d("DBTransactionHandler", "addRecordTransaction - start transaction");

        SQLiteDatabase db = DatabaseHandler.getWritableDatabase();
        db.beginTransaction();
        try {

            if (recordLevel == 1 || recordLevel == 2) {
                //Triggers
                if (record.getTriggers() != null && record.getTriggers().size() > 0) {
                    Log.d("DBTransactionHandler", "addRecordTransaction - Triggers");
                    for (Trigger trigger : record.getTriggers()) {
                        long result = DBTriggerDAO.addTriggerRecord(db, trigger.getTriggerId(), record.getRecordId());
                        if (result < 1) {
                            throw new Exception("Trigger record insert failed. code : " + result);
                        }
                    }
                }

                //Symptoms
                if (record.getSymptoms() != null && record.getSymptoms().size() > 0) {
                    Log.d("DBTransactionHandler", "addRecordTransaction - Symptoms");
                    for (Symptom symptom : record.getSymptoms()) {
                        long result = DBSymptomDAO.addSymptomRecord(db, symptom.getSymptomId(), record.getRecordId());
                        if (result < 1) {
                            throw new Exception("Symptom record insert failed. code : " + result);
                        }
                    }
                }

                //Activities
                if (record.getActivities() != null && record.getActivities().size() > 0) {
                    Log.d("DBTransactionHandler", "addRecordTransaction - Activities");
                    for (LifeActivity lifeActivity : record.getActivities()) {
                        long result = DBActivityDAO.addActivityRecord(db, lifeActivity.getActivityId(), record.getRecordId());
                        if (result < 1) {
                            throw new Exception("Activity record insert failed. code : " + result);
                        }
                    }
                }
            }

            if (recordLevel == 2) {
                //Body areas
                if (record.getBodyAreas() != null && record.getBodyAreas().size() > 0) {
                    Log.d("DBTransactionHandler", "addRecordTransaction - BodyAreas");
                    for (BodyArea bodyArea : record.getBodyAreas()) {
                        long result = DBBodyAreaDAO.addBodyAreaRecord(db, bodyArea.getBodyAreaId(), record.getRecordId());
                        if (result < 1) {
                            throw new Exception("Body area record insert failed. code : " + result);
                        }
                    }
                }

                //Medicines
                if (record.getMedicines() != null && record.getMedicines().size() > 0) {
                    Log.d("DBTransactionHandler", "addRecordTransaction - Medicines");
                    for (Medicine medicine : record.getMedicines()) {
                        long result = DBMedicineDAO.addMedicineRecord(db, medicine.getMedicineId(), record.getRecordId(), medicine.isEffective());
                        if (result < 1) {
                            throw new Exception("Medicine record insert failed. code : " + result);
                        }
                    }
                }

                //Reliefs
                if (record.getReliefs() != null && record.getReliefs().size() > 0) {
                    Log.d("DBTransactionHandler", "addRecordTransaction - Reliefs");
                    for (Relief relief : record.getReliefs()) {
                        long result = DBReliefDAO.addReliefRecord(db, relief.getReliefId(), record.getRecordId(), relief.isEffective());
                        if (result < 1) {
                            throw new Exception("Relief record insert failed. code : " + result);
                        }
                    }
                }
            }

            //Weather data
            if (record.getWeatherData() != null) {
                Log.d("DBTransactionHandler", "addRecordTransaction WeatherData");

                long result = DBWeatherDataDAO.addWeatherData(db, record.getRecordId(), record.getWeatherData());
                if (result < 1) {
                    throw new Exception("Weather Data insert failed. code : " + result);
                }

            }

            //Add record itself
            long result = DBRecordDAO.addRecordToDB(db, record);
            if (result < 1) {
                throw new Exception("Record insert failed. code : " + result);
            }
            db.setTransactionSuccessful();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.endTransaction();
            db.close();
        }
    }
}
