package shehan.com.migrainetrigger.controller;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shehan.com.migrainetrigger.data.dao.DBSymptomDAO;
import shehan.com.migrainetrigger.data.model.Symptom;
import shehan.com.migrainetrigger.view.model.AnswerSectionViewData;

/**
 * Created by Shehan on 4/13/2016.
 */
public class SymptomController {

    public static long addSymptomRecord(int symptomId, int recordId) {
        Log.d("SymptomController", " addSymptomRecord ");
        return DBSymptomDAO.addSymptomRecord(symptomId, recordId);
    }

    public static long addSymptoms(ArrayList<Symptom> lst) {
        for (Symptom itm : lst) {
            long result = addSymptom(itm);
            if (result < 1) {
                return 0;
            }
        }
        return lst.size();
    }

    public static long addSymptom(Symptom symptom) {
        return DBSymptomDAO.addSymptom(symptom);
    }

    public static long deleteSymptom(int id) {
        return DBSymptomDAO.deleteSymptom(id);
    }

    public static List<AnswerSectionViewData> getAnswerSectionViewData() {
        ArrayList<Symptom> lst = getAllSymptoms();
        List<AnswerSectionViewData> answerSectionViewDataLst = new ArrayList<>();
        for (int i = 0; i < lst.size(); i++) {
            Symptom symptom = lst.get(i);

            answerSectionViewDataLst.add(new AnswerSectionViewData(symptom.getSymptomId(), symptom.getSymptomName(), symptom.getPriority()));
        }
        return answerSectionViewDataLst;
    }

    public static ArrayList<Symptom> getAllSymptoms() {
        Log.d("SymptomController", " getAllSymptoms ");
        ArrayList<Symptom> symptomArrayList = DBSymptomDAO.getAllSymptoms();
        Collections.sort(symptomArrayList);
        return symptomArrayList;
    }

    public static int getLastRecordId() {
        return DBSymptomDAO.getLastRecordId();
    }

    public static Symptom getSymptomById(int id) {
        return DBSymptomDAO.getSymptom(id);
    }

    public static ArrayList<Symptom> getSymptomsForRecord(int recordId) {
        return DBSymptomDAO.getSymptomsForRecord(recordId);
    }

    public static long updateSymptomRecord(Symptom symptom) {
        return DBSymptomDAO.updateSymptomRecord(symptom);
    }
}
