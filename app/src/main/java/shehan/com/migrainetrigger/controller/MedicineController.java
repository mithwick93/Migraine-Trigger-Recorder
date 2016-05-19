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

    public static long addMedicineRecord(int medicineId, int recordId, boolean effective) {
        Log.d("MedicineController", " addMedicineRecord ");
        return DBMedicineDAO.addMedicineRecord(medicineId, recordId, effective);
    }

    public static ArrayList<Medicine> getMedicinesForRecord(int recordId) {
        return DBMedicineDAO.getMedicinesForRecord(recordId);
    }

    public static long addMedicine(Medicine medicine) {
        return DBMedicineDAO.addMedicine(medicine);
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
}