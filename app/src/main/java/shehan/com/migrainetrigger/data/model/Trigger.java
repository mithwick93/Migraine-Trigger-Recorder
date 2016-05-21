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

    @Override
    public boolean equals(Object object) {
        boolean isEqual = false;

        if (object != null && object instanceof Trigger) {
            isEqual = (this.triggerId == ((Trigger) object).triggerId);
        }

        return isEqual;
    }

    @Override
    public int hashCode() {
        return this.triggerId;
    }

    @Override
    public String toString() {
        return triggerName != null ? triggerName : "Trigger : " + triggerId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
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
}
