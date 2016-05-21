package shehan.com.migrainetrigger.data.model;

/**
 * Created by Shehan on 4/13/2016.
 */
public class Symptom extends PriorityEntity {
    private int symptomId;
    private String symptomName;

    public Symptom(int symptomId, String symptomName, int priority) {
        this.symptomId = symptomId;
        this.symptomName = symptomName;
        this.priority = priority;
    }

    @Override
    public boolean equals(Object object) {
        boolean isEqual = false;

        if (object != null && object instanceof Symptom) {
            isEqual = (this.symptomId == ((Symptom) object).symptomId);
        }

        return isEqual;
    }

    @Override
    public int hashCode() {
        return this.symptomId;
    }

    @Override
    public String toString() {
        return symptomName != null ? symptomName : "Symptom : " + symptomId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(int symptomId) {
        this.symptomId = symptomId;
    }

    public String getSymptomName() {
        return symptomName;
    }

    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName;
    }
}
