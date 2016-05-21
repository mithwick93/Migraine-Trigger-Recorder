package shehan.com.migrainetrigger.data.builders;

import shehan.com.migrainetrigger.data.model.Trigger;

public class TriggerBuilder {
    private int priority;
    private int triggerId;
    private String triggerName;

    public Trigger createTrigger() {
        return new Trigger(triggerId, triggerName, priority);
    }

    public TriggerBuilder setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public TriggerBuilder setTriggerId(int triggerId) {
        this.triggerId = triggerId;
        return this;
    }

    public TriggerBuilder setTriggerName(String triggerName) {
        this.triggerName = triggerName;
        return this;
    }
}