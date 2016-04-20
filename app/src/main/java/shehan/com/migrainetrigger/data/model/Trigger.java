package shehan.com.migrainetrigger.data.model;

/**
 * Created by Shehan on 4/13/2016.
 */
public class Trigger extends PriorityEntity {

    private int triggerId;
    private String triggerName;

    public Trigger(int triggerId, String triggerName, int priority) {
        this.triggerId = triggerId;
        this.triggerName = triggerName;
        this.priority = priority;
    }

    public int getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(int triggerId) {
        this.triggerId = triggerId;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return triggerName != null ? triggerName : "Trigger : " + triggerId;
    }
}
