package shehan.com.migrainetrigger.data.builders;

import shehan.com.migrainetrigger.data.model.Relief;

public class ReliefBuilder {
    private int reliefId;
    private String reliefName;
    private int priority;
    private boolean effective;

    public ReliefBuilder setReliefId(int reliefId) {
        this.reliefId = reliefId;
        return this;
    }

    public ReliefBuilder setReliefName(String reliefName) {
        this.reliefName = reliefName;
        return this;
    }

    public ReliefBuilder setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public ReliefBuilder setEffective(boolean effective) {
        this.effective = effective;
        return this;
    }

    public Relief createRelief() {
        return new Relief(reliefId, reliefName, priority, effective);
    }
}