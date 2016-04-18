package shehan.com.migrainetrigger.controller;

import android.util.Log;

import java.util.ArrayList;

import shehan.com.migrainetrigger.data.dao.DBSymptomDAO;
import shehan.com.migrainetrigger.data.model.Symptom;

/**
 * Created by Shehan on 4/13/2016.
 */
public class SymptomController {
    public static void addNewSymptom(Symptom symptom) {

    }

    public static Symptom getSymptomById(int id) {
        return null;
    }

    /**
     *
     * @return
     */
    public static ArrayList<Symptom> getAllSymptoms() {
        Log.d("SymptomController", " getAllSymptoms ");
        return DBSymptomDAO.getAllSymptoms();
    }

    public static void deleteSymptom() {

    }

    public static void updateSymptom(Symptom symptom) {

    }

    public static void reorderPriority(Symptom symptom) {

    }

    public static long addSymptomRecord(int symptomId, int recordId) {
        Log.d("SymptomController", " addSymptomRecord ");
        return DBSymptomDAO.addSymptomRecord(symptomId, recordId);
    }

}
