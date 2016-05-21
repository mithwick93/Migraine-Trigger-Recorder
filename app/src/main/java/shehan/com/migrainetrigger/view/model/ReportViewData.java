package shehan.com.migrainetrigger.view.model;

/**
 * Created by Shehan on 11/05/2016.
 */
public class ReportViewData {

    private String content_1;
    private String content_2;
    private String content_3;
    private String topic;

    public ReportViewData(String topic) {
        this.topic = topic;
        this.content_1 = "EMPTY";
        this.content_2 = "EMPTY";
        this.content_3 = "EMPTY";
    }

    public String getContent_1() {
        return content_1;
    }

    public void setContent_1(String content_1) {
        this.content_1 = content_1;
    }

    public String getContent_2() {
        return content_2;
    }

    public void setContent_2(String content_2) {
        this.content_2 = content_2;
    }

    public String getContent_3() {
        return content_3;
    }

    public void setContent_3(String content_3) {
        this.content_3 = content_3;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
