package shehan.com.migrainetrigger.controller;

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
    public static Record getRecordAll(int id) {

        return null;
    }


    /**
     * @return
     */
    public static ArrayList<Record> getAllRecords() {

        return DBRecordDAO.getAllRecords();
    }


    public static String getStatus() {
        String status = "No migraine records yet.";

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
        }

        return status;
    }

    public static void deleteRecord() {

    }

    public static void updateRecord(Record record) {

    }

}
