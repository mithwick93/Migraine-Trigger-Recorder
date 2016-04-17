package shehan.com.migrainetrigger.controller;

import android.util.Log;

import java.util.ArrayList;

import shehan.com.migrainetrigger.data.dao.DBRecordDAO;
import shehan.com.migrainetrigger.data.model.Record;

/**
 * Created by Shehan on 4/13/2016.
 */
public class RecordController {

    public static long addNewRecord(Record record) {
        Log.d("RecordControl", "Process record save");

        return DBRecordDAO.addRecordToDB(record);

    }

    public static Record getRecordById(int id) {
        return null;
    }

    public static ArrayList<Record> getAllRecords() {
        return null;
    }

    public static void deleteRecord() {

    }

    public static void updateRecord(Record record) {

    }

}
