package app;

import ru.yaal.project.hhapi.search.SearchException;

/**
 * Кастомный класс для реализации хранения кастомных вакансий
 *
 * @sense Класс библиотеки <u>ru.yaal.project.hhapi</u> косячит: при вызове
 * метода <u>ru.yaal.project.hhapi.HhApi.search()</u> выводится отладочная
 * информация библиотеки.
 * @issue Убрать вывод отладочной информации из
 * <u>ru.yaal.project.hhapi.HhApi</u>
 * @author gorinij
 */
public class VacancyList extends java.util.ArrayList<Vacancy> {

    /**
     * @param vacancyList Нужно передать экземляр класса
     * <b>ru.yaal.project.hhapi.vacancy.VacancyList</b>.
     */
    public VacancyList(ru.yaal.project.hhapi.vacancy.VacancyList vacancyList) {
        try {
            /**
             * Здесь мы развертываем полученный от Headhunter список вакансий и
             * формируем его в приемлимую для использования форму.
             */
            for (ru.yaal.project.hhapi.vacancy.Vacancy vacancy : vacancyList) {
                app.Vacancy v = new app.Vacancy(
                        vacancy.getId(),
                        vacancy.getName(),
                        vacancy.getDescription(),
                        vacancy.getEmployer().toString(),
                        vacancy.getCreatedAt().toString()
                );
                add(v);
            }
        } catch (SearchException e_VacancySearch) {
            /**
             * Выводим информацию о том, где произошла ошибка, если программа
             * запущена в режиме отладки
             *
             * @see boolean Main.debugMode
             */
            if (Main.debugMode = true) {
                System.err.println("path:app.VacancyList"); //@debug
            }
            System.err.println("Произошла ошибка при обработке вакансий.");
            e_VacancySearch.printStackTrace();
        }
    }
}
