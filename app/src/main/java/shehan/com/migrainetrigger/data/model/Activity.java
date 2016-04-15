package shehan.com.migrainetrigger.data.model;

/**
 * Created by Shehan on 4/13/2016.
 */
public class Activity {
    private int activityId;
    private String activityName;
    private int priority;

    public Activity(int activityId, String activityName, int priority) {
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
}
