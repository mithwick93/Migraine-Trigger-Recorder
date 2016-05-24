package shehan.com.migrainetrigger.controller;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shehan.com.migrainetrigger.data.dao.DBMedicineDAO;
import shehan.com.migrainetrigger.data.model.Medicine;
import shehan.com.migrainetrigger.view.model.AnswerSectionViewData;

/**
 * Created by Shehan on 4/13/2016.
 */
public class MedicineController {

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

    public static long addMedicine(Medicine medicine) {
        return DBMedicineDAO.addMedicine(medicine);
    }

    public static long deleteMedicine(int id) {
        return DBMedicineDAO.deleteMedicine(id);
    }

    public static List<AnswerSectionViewData> getAnswerSectionViewData() {
        ArrayList<Medicine> lst = getAllMedicines();
        List<AnswerSectionViewData> answerSectionViewDataLst = new ArrayList<>();
        for (int i = 0; i < lst.size(); i++) {
            Medicine medicine = lst.get(i);

            answerSectionViewDataLst.add(new AnswerSectionViewData(medicine.getMedicineId(), medicine.getMedicineName(), medicine.getPriority()));
        }
        return answerSectionViewDataLst;
    }

    public static ArrayList<Medicine> getAllMedicines() {
        Log.d("MedicineController", " getAllMedicines ");
        ArrayList<Medicine> medicineArrayList = DBMedicineDAO.getAllMedicines();
        Collections.sort(medicineArrayList);
        return medicineArrayList;
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

    public static long updateMedicineRecord(Medicine medicine) {
        return DBMedicineDAO.updateMedicineRecord(medicine);
    }
}