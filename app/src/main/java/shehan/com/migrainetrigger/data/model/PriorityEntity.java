package shehan.com.migrainetrigger.data.model;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by Shehan on 4/20/2016.
 * Support class to be used to sort entities by their priority
 */
public abstract class PriorityEntity implements Comparable<PriorityEntity> {
    protected int priority;

    @Override
    public int compareTo(@NonNull PriorityEntity priorityEntity) {
        Log.d("PriorityEntity", "compareTo");
        return (priority - priorityEntity.priority);
    }

}
