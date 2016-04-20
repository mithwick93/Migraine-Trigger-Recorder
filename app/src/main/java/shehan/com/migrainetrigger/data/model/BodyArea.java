package shehan.com.migrainetrigger.data.model;

/**
 * Created by Shehan on 4/13/2016.
 */
public class BodyArea {
    private int bodyAreaId;
    private String bodyAreaName;

    public BodyArea(int bodyAreaId, String bodyAreaName) {
        this.bodyAreaId = bodyAreaId;
        this.bodyAreaName = bodyAreaName;
    }

    public int getBodyAreaId() {
        return bodyAreaId;
    }

    public void setBodyAreaId(int bodyAreaId) {
        this.bodyAreaId = bodyAreaId;
    }

    public String getBodyAreaName() {
        return bodyAreaName;
    }

    public void setBodyAreaName(String bodyAreaName) {
        this.bodyAreaName = bodyAreaName;
    }


}
