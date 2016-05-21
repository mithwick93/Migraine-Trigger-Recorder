package shehan.com.migrainetrigger.data.model;

/**
 * Created by Shehan on 4/13/2016.
 */
public class Medicine extends PriorityEntity {
    private boolean effective;
    private int medicineId;
    private String medicineName;

    public Medicine(int medicineId, String medicineName, int priority, boolean effective) {
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.priority = priority;
        this.effective = effective;
    }

    @Override
    public boolean equals(Object object) {
        boolean isEqual = false;

        if (object != null && object instanceof Medicine) {
            isEqual = (this.medicineId == ((Medicine) object).medicineId);
        }

        return isEqual;
    }

    @Override
    public int hashCode() {
        return this.medicineId;
    }

    @Override
    public String toString() {
        return medicineName != null ? medicineName : "Medicine : " + medicineId;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isEffective() {
        return effective;
    }

    public void setEffective(boolean effective) {
        this.effective = effective;
    }
}
