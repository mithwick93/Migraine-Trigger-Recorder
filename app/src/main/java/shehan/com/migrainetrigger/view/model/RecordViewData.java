package shehan.com.migrainetrigger.view.model;

/**
 * Created by Shehan on 4/20/2016.
 */
public class RecordViewData {
    private String duration;
    private int imgUrl;
    private int recordId;
    private String startTime;

    public RecordViewData(int recordId, String startTime, String duration, int imgUrl) {
        this.recordId = recordId;
        this.startTime = startTime;
        this.duration = duration;
        this.imgUrl = imgUrl;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
