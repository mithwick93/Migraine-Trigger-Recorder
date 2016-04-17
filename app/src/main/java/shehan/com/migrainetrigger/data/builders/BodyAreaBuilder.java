package shehan.com.migrainetrigger.data.builders;

import shehan.com.migrainetrigger.data.model.BodyArea;

public class BodyAreaBuilder {
    private int bodyAreaId;
    private String bodyAreaName;

    public BodyAreaBuilder setBodyAreaId(int bodyAreaId) {
        this.bodyAreaId = bodyAreaId;
        return this;
    }

    public BodyAreaBuilder setBodyAreaName(String bodyAreaName) {
        this.bodyAreaName = bodyAreaName;
        return this;
    }

    public BodyArea createBodyArea() {
        return new BodyArea(bodyAreaId, bodyAreaName);
    }
}