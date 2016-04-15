package shehan.com.migrainetrigger.data.model;

/**
 * Created by Shehan on 4/13/2016.
 */
public class Symptom {
    private int symptomId;
    private String symptomName;
    private int priority;

    public Symptom(int symptomId, String symptomName, int priority) {
        this.symptomId = symptomId;
        this.symptomName = symptomName;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
