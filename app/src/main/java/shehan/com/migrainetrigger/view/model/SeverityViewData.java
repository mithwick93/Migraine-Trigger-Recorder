package shehan.com.migrainetrigger.view.model;

/**
 * Created by Shehan on 4/15/2016.
 */
public class SeverityViewData {
    private String title;
    private String description;
    private int imageUrl;

    public SeverityViewData(String title, String description, int imageUrl) {

        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }
}
