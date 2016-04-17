package shehan.com.migrainetrigger.data.builders;

import shehan.com.migrainetrigger.data.model.Medicine;

public class MedicineBuilder {
    private int medicineId;
    private String medicineName;
    private int priority;
    private boolean effective;

    public MedicineBuilder setMedicineId(int medicineId) {
        this.medicineId = medicineId;
        return this;
    }

    public MedicineBuilder setMedicineName(String medicineName) {
        this.medicineName = medicineName;
        return this;
    }

    public MedicineBuilder setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public MedicineBuilder setEffective(boolean effective) {
        this.effective = effective;
        return this;
    }

    public Medicine createMedicine() {
        return new Medicine(medicineId, medicineName, priority, effective);
    }
}