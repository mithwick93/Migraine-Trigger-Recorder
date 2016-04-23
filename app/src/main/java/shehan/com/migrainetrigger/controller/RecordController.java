package shehan.com.migrainetrigger.controller;

import android.util.Log;

import java.util.ArrayList;

import shehan.com.migrainetrigger.data.dao.DBRecordDAO;
import shehan.com.migrainetrigger.data.dao.DBTransactionHandler;
import shehan.com.migrainetrigger.data.model.Record;

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

    public static void deleteRecord() {

    }

    public static void updateRecord(Record record) {

    }

}
