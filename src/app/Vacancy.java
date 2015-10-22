package app;

/**
 *
 * app.Vacancy: Construct(title, description); private Str title [get, set];
 * private Str description [get, set]; private Str date [get];
 *
 * @author gorjnich
 *
 */
public class Vacancy {
    /* ��������� �������� */

    private String id;
    private String title;
    private String description;
    private String date;
    private String company;

    /**
     * �����������
     *
     * @param id
     * @param title
     * @param description
     * @param company
     * @param date
     */
    public Vacancy(
            String id,
            String title,
            String description,
            String company,
            String date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    /**
     * ������ ��������� ������ ��������
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * ������ �������� ������ ��������
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * ����������� ��������� ��� ������ ��������
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * ����������� �������� ��� ������ ��������
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }
}
