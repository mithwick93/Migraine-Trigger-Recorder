package shehan.com.migrainetrigger.controller;

import android.util.Log;

import java.util.ArrayList;

import shehan.com.migrainetrigger.data.dao.DBBodyAreaDAO;
import shehan.com.migrainetrigger.data.model.BodyArea;

/**
 * Created by Shehan on 4/13/2016.
 */
public class BodyAreaController {

    public static void addNewBodyArea(BodyArea bodyArea) {
    }

    public static BodyArea getBodyAreaById(int id) {
        return null;
    }

    public static ArrayList<BodyArea> getAllBodyAreas() {
        Log.d("BodyAreaController", " getAllBodyAreas ");
        return DBBodyAreaDAO.getAllBodyAreas();
    }

    public static void deleteBodyArea() {

    }

    public static void updateBodyArea(BodyArea bodyArea) {

    }

    public static void reorderPriority(BodyArea bodyArea) {

    }

    public static long addBodyAreaRecord(int bodyAreaId, int recordId) {
        Log.d("BodyAreaController", " addBodyAreaRecord ");
        return DBBodyAreaDAO.addBodyAreaRecord(bodyAreaId, recordId);
    }

    public static ArrayList<BodyArea> getBodyAreasForRecord(int recordId) {
        return DBBodyAreaDAO.getBodyAreasForRecord(recordId);
    }

    public static long addBodyArea(BodyArea bodyArea) {
        return DBBodyAreaDAO.addBodyArea(bodyArea);
    }

    public static long addBodyAreas(ArrayList<BodyArea> lst) {
        for (BodyArea itm : lst) {
            long result = addBodyArea(itm);
            if (result < 1) {
                return 0;
            }
        }
        return lst.size();
    }

    public static long deleteBodyArea(int id) {
        return DBBodyAreaDAO.deleteBodyArea(id);
    }
}
