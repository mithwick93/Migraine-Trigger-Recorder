package shehan.com.migrainetrigger.data.model;

/**
 * Created by Shehan on 4/13/2016.
 */
public class Relief extends PriorityEntity {

    private boolean effective;
    private int reliefId;
    private String reliefName;

    public Relief(int reliefId, String reliefName, int priority, boolean effective) {
        this.reliefId = reliefId;
        this.reliefName = reliefName;
        this.priority = priority;
        this.effective = effective;
    }

    @Override
    public boolean equals(Object object) {
        boolean isEqual = false;

        if (object != null && object instanceof Relief) {
            isEqual = (this.reliefId == ((Relief) object).reliefId);
        }

        return isEqual;
    }

    @Override
    public int hashCode() {
        return this.reliefId;
    }

    @Override
    public String toString() {
        return reliefName != null ? reliefName : "Relief : " + reliefId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getReliefId() {
        return reliefId;
    }

    public void setReliefId(int reliefId) {
        this.reliefId = reliefId;
    }

    public String getReliefName() {
        return reliefName;
    }

    public void setReliefName(String reliefName) {
        this.reliefName = reliefName;
    }

    public boolean isEffective() {
        return effective;
    }

    public void setEffective(boolean effective) {
        this.effective = effective;
    }
}
