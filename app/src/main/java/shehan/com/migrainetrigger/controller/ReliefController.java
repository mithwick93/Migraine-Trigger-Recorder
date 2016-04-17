package shehan.com.migrainetrigger.controller;

import android.util.Log;

import java.util.ArrayList;

import shehan.com.migrainetrigger.data.dao.DBReliefDAO;
import shehan.com.migrainetrigger.data.model.Relief;

/**
 * Created by Shehan on 4/13/2016.
 */
public class ReliefController {

    public static void addNewRelief(Relief relief) {

    }

    public static Relief getReliefById(int id) {
        return null;
    }

    public static ArrayList<Relief> getAllReliefs() {
        Log.d("getAll", " getAllReliefs ");
        return DBReliefDAO.getAllReliefs();
    }

    public static void deleteRelief() {

    }

    public static void updateRelief(Relief relief) {

    }

    public static void reorderPriority(Relief relief) {

    }

    public static long addReliefRecord(int reliefId, int recordId, boolean effective) {
        return DBReliefDAO.addReliefRecord(reliefId, recordId, effective);
    }
}
