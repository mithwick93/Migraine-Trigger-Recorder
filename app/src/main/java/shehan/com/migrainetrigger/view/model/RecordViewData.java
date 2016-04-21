package shehan.com.migrainetrigger.view.model;

/**
 * Created by Shehan on 4/20/2016.
 */
public class RecordViewData {
    private String startTime;
    private String duration;
    private int imgUrl;

    public RecordViewData(String startTime, String duration, int imgUrl) {
        this.startTime = startTime;
        this.duration = duration;
        this.imgUrl = imgUrl;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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
}
