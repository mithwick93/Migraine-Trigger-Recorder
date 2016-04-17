package shehan.com.migrainetrigger.controller;

import android.util.Log;

import java.util.ArrayList;

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
        Log.d("getAll", " getAllMedicines ");
        return DBMedicineDAO.getAllMedicines();
    }

    public static void deleteMedicine() {

    }

    public static void updateMedicine(Medicine medicine) {

    }

    public static void reorderPriority(Medicine medicine) {

    }

    public static long addMedicineRecord(int medicineId, int recordId, boolean effective) {
        return DBMedicineDAO.addMedicineRecord(medicineId, recordId, effective);
    }
}
