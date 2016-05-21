package shehan.com.migrainetrigger.data.builders;

import shehan.com.migrainetrigger.data.model.Relief;

public class ReliefBuilder {
    private boolean effective;
    private int priority;
    private int reliefId;
    private String reliefName;

    public Relief createRelief() {
        return new Relief(reliefId, reliefName, priority, effective);
    }

    public ReliefBuilder setEffective(boolean effective) {
        this.effective = effective;
        return this;
    }

    public ReliefBuilder setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public ReliefBuilder setReliefId(int reliefId) {
        this.reliefId = reliefId;
        return this;
    }

    public ReliefBuilder setReliefName(String reliefName) {
        this.reliefName = reliefName;
        return this;
    }
}