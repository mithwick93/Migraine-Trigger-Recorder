package shehan.com.migrainetrigger.data.model;

/**
 * Created by Shehan on 4/13/2016.
 */
public class LifeActivity extends PriorityEntity {
    private int activityId;
    private String activityName;

    public LifeActivity(int activityId, String activityName, int priority) {
        this.activityId = activityId;
        this.activityName = activityName;
        this.priority = priority;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return activityName != null ? activityName : "Activity : " + activityId;
    }

}
