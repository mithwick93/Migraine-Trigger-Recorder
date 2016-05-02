package shehan.com.migrainetrigger.controller;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

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

    public static ArrayList<Symptom> getAllSymptoms() {
        Log.d("SymptomController", " getAllSymptoms ");
        ArrayList<Symptom> symptomArrayList = DBSymptomDAO.getAllSymptoms();
        Collections.sort(symptomArrayList);
        return symptomArrayList;
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

    public static ArrayList<Symptom> getSymptomsForRecord(int recordId) {
        return DBSymptomDAO.getSymptomsForRecord(recordId);
    }
}
