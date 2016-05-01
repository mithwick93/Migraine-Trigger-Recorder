package shehan.com.migrainetrigger.controller;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

import shehan.com.migrainetrigger.data.dao.DBMedicineDAO;
import shehan.com.migrainetrigger.data.model.Medicine;

/**
 * Created by Shehan on 4/13/2016.
 */
public class MedicineController {

    public static void addNewMedicine(Medicine medicine) {

    }

    public static Medicine getMedicineById(int id) {
        return null;
    }

    /**
     * @return
     */
    public static ArrayList<Medicine> getAllMedicines() {
        Log.d("MedicineController", " getAllMedicines ");
        ArrayList<Medicine> medicineArrayList = DBMedicineDAO.getAllMedicines();
        Collections.sort(medicineArrayList);
        return medicineArrayList;
    }

    public static void deleteMedicine() {

    }

    public static void updateMedicine(Medicine medicine) {

    }

    public static void reorderPriority(Medicine medicine) {

    }

    /**
     * @param medicineId
     * @param recordId
     * @param effective
     * @return
     */
    public static long addMedicineRecord(int medicineId, int recordId, boolean effective) {
        Log.d("MedicineController", " addMedicineRecord ");
        return DBMedicineDAO.addMedicineRecord(medicineId, recordId, effective);
    }

    public static ArrayList<Medicine> getMedicinesForRecord(int recordId) {
        return DBMedicineDAO.getMedicinesForRecord(recordId);
    }

}