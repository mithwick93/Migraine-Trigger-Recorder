package shehan.com.migrainetrigger.data.builders;

import shehan.com.migrainetrigger.data.model.LifeActivity;

public class ActivityBuilder {
    private int activityId;
    private String activityName;
    private int priority;

    public ActivityBuilder setActivityId(int activityId) {
        this.activityId = activityId;
        return this;
    }

    public ActivityBuilder setActivityName(String activityName) {
        this.activityName = activityName;
        return this;
    }

    public ActivityBuilder setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public LifeActivity createActivity() {
        return new LifeActivity(activityId, activityName, priority);
    }
}