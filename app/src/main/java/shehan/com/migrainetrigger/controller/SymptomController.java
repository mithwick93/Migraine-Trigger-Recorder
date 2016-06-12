package shehan.com.migrainetrigger.controller;

import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shehan.com.migrainetrigger.data.dao.DBSymptomDAO;
import shehan.com.migrainetrigger.data.model.Symptom;
import shehan.com.migrainetrigger.utility.AppUtil;
import shehan.com.migrainetrigger.utility.MigraineTriggerApplication;
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
        ArrayList<Symptom> lst = getAllSymptoms(false);
        List<AnswerSectionViewData> answerSectionViewDataLst = new ArrayList<>();
        for (int i = 0; i < lst.size(); i++) {
            Symptom symptom = lst.get(i);

            answerSectionViewDataLst.add(new AnswerSectionViewData(symptom.getSymptomId(), symptom.getSymptomName(), symptom.getPriority()));
        }
        return answerSectionViewDataLst;
    }

    public static ArrayList<Symptom> getAllSymptoms(boolean applySuggestions) {
        Log.d("SymptomController", " getAllSymptoms ");
        ArrayList<Symptom> lst = DBSymptomDAO.getAllSymptoms();

        Collections.sort(lst);

        if (applySuggestions) {
            //enable suggestions
            boolean suggestions = PreferenceManager.getDefaultSharedPreferences(MigraineTriggerApplication.getAppContext()).getBoolean("pref_suggestions", false);
            if (lst.size() > 0 && suggestions) {
                return getReOrderedLst(lst);
            }
        }

        return lst;
    }

    private static ArrayList<Symptom> getReOrderedLst(ArrayList<Symptom> lst) {
        Log.d("SymptomController", "getReOrderedLst ");
        ArrayList<String> topLst = AppUtil.getTopList("Symptom");

        if (topLst != null && topLst.size() > 0) {
            Collections.reverse(topLst);//reverse priority
            //check for 1
            lst = reorderLst(lst, topLst.get(0));

            if (topLst.size() > 1) {//check for 2
                lst = reorderLst(lst, topLst.get(1));
            }
            if (topLst.size() > 2) {//check for 3
                lst = reorderLst(lst, topLst.get(2));
            }
        }

        return lst;
    }

    private static ArrayList<Symptom> reorderLst(ArrayList<Symptom> lst, String match) {
        Log.d("SymptomController", "reorderLst ");
        int pos = -1;
        for (int itr = 0; itr < lst.size(); itr++) {
            if (lst.get(itr).getSymptomName().trim().equals(match.trim())) {//get position of element
                pos = itr;
                break;
            }
        }
        if (pos > -1 && pos < lst.size()) {
            Symptom tmp = lst.remove(pos);//remove and insert at beginning
            lst.add(0, tmp);
        } else {
            Log.e("SymptomController", "reorderLst - top lst item not found at index " + 0);
        }
        return lst;
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
