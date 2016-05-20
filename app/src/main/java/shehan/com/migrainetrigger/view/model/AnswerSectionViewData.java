package shehan.com.migrainetrigger.view.model;

/**
 * Created by Shehan on 21/05/2016.
 */
public class AnswerSectionViewData {
    private int id;
    private String name;
    private int priority; //may be not set

    public AnswerSectionViewData(int id, String name, int priority) {
        this.id = id;
        this.name = name;
        this.priority = priority;
    }

    public AnswerSectionViewData(int id, String name) {
        this.id = id;
        this.name = name;
        this.priority = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
