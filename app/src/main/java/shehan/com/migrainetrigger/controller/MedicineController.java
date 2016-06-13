package shehan.com.migrainetrigger.controller;

import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shehan.com.migrainetrigger.data.dao.DBMedicineDAO;
import shehan.com.migrainetrigger.data.model.Medicine;
import shehan.com.migrainetrigger.utility.AppUtil;
import shehan.com.migrainetrigger.utility.MigraineTriggerApplication;
import shehan.com.migrainetrigger.view.model.AnswerSectionViewData;

/**
 * Created by Shehan on 4/13/2016.
 */
public class MedicineController {

    public static long addMedicine(Medicine medicine) {
        return DBMedicineDAO.addMedicine(medicine);
    }

    public static long addMedicineRecord(int medicineId, int recordId, boolean effective) {
        Log.d("MedicineController", " addMedicineRecord ");
        return DBMedicineDAO.addMedicineRecord(medicineId, recordId, effective);
    }

    public static long addMedicines(ArrayList<Medicine> lst) {
        for (Medicine itm : lst) {
            long result = addMedicine(itm);
            if (result < 1) {
                return 0;
            }
        }
        return lst.size();
    }

    public static long deleteMedicine(int id) {
        return DBMedicineDAO.deleteMedicine(id);
    }

    public static ArrayList<Medicine> getAllMedicines(boolean applySuggestions) {
        Log.d("MedicineController", " getAllMedicines ");
        ArrayList<Medicine> lst = DBMedicineDAO.getAllMedicines();

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

    public static List<AnswerSectionViewData> getAnswerSectionViewData() {
        ArrayList<Medicine> lst = getAllMedicines(false);
        List<AnswerSectionViewData> answerSectionViewDataLst = new ArrayList<>();
        for (int i = 0; i < lst.size(); i++) {
            Medicine medicine = lst.get(i);

            answerSectionViewDataLst.add(new AnswerSectionViewData(medicine.getMedicineId(), medicine.getMedicineName(), medicine.getPriority()));
        }
        return answerSectionViewDataLst;
    }

    public static int getLastRecordId() {
        return DBMedicineDAO.getLastRecordId();
    }

    public static Medicine getMedicineById(int id) {
        return DBMedicineDAO.getMedicine(id);
    }

    public static ArrayList<Medicine> getMedicinesForRecord(int recordId) {
        return DBMedicineDAO.getMedicinesForRecord(recordId);
    }

    private static ArrayList<Medicine> getReOrderedLst(ArrayList<Medicine> lst) {
        Log.d("MedicineController", "getReOrderedLst ");
        ArrayList<String> topLst = AppUtil.getTopList("Medicine");

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

    private static ArrayList<Medicine> reorderLst(ArrayList<Medicine> lst, String match) {
        Log.d("MedicineController", "reorderLst ");
        int pos = -1;
        for (int itr = 0; itr < lst.size(); itr++) {
            if (lst.get(itr).getMedicineName().trim().equals(match.trim())) {//get position of element
                pos = itr;
                break;
            }
        }
        if (pos > -1 && pos < lst.size()) {
            Medicine tmp = lst.remove(pos);//remove and insert at beginning
            lst.add(0, tmp);
        } else {
            Log.e("MedicineController", "reorderLst - top lst item not found at index " + 0);
        }
        return lst;
    }

    public static long updateMedicineRecord(Medicine medicine) {
        return DBMedicineDAO.updateMedicineRecord(medicine);
    }
}