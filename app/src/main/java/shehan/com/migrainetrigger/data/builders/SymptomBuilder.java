package shehan.com.migrainetrigger.data.builders;

import shehan.com.migrainetrigger.data.model.Symptom;

public class SymptomBuilder {
    private int priority;
    private int symptomId;
    private String symptomName;

    public Symptom createSymptom() {
        return new Symptom(symptomId, symptomName, priority);
    }

    public SymptomBuilder setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public SymptomBuilder setSymptomId(int symptomId) {
        this.symptomId = symptomId;
        return this;
    }

    public SymptomBuilder setSymptomName(String symptomName) {
        this.symptomName = symptomName;
        return this;
    }
}