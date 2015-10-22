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
    /* Заголовок вакансии */

    private String id;
    private String title;
    private String description;
    private String date;
    private String company;

    /**
     * Конструктор
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
     * Отдает заголовок данной вакансии
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Отдает описание данной вакансии
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Присваивает заголовок для данной вакансии
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Присваивает описание для данной вакансии
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
