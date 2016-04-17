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
        Log.d("getAll", " getAllBodyAreas ");
        return DBBodyAreaDAO.getAllBodyAreas();
    }

    public static void deleteBodyArea() {

    }

    public static void updateBodyArea(BodyArea bodyArea) {

    }

    public static void reorderPriority(BodyArea bodyArea) {

    }

    public static long addBodyAreaRecord(int bodyAreaId, int recordId) {
        return DBBodyAreaDAO.addBodyAreaRecord(bodyAreaId, recordId);
    }
}
