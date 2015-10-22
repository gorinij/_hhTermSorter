package app;

import ru.yaal.project.hhapi.search.SearchException;

/**
 * ��������� ����� ��� ���������� �������� ��������� ��������
 *
 * @sense ����� ���������� <u>ru.yaal.project.hhapi</u> �������: ��� ������
 * ������ <u>ru.yaal.project.hhapi.HhApi.search()</u> ��������� ����������
 * ���������� ����������.
 * @issue ������ ����� ���������� ���������� ��
 * <u>ru.yaal.project.hhapi.HhApi</u>
 * @author gorinij
 */
public class VacancyList extends java.util.ArrayList<Vacancy> {

    /**
     * @param vacancyList ����� �������� �������� ������
     * <b>ru.yaal.project.hhapi.vacancy.VacancyList</b>.
     */
    public VacancyList(ru.yaal.project.hhapi.vacancy.VacancyList vacancyList) {
        try {
            /**
             * ����� �� ������������ ���������� �� Headhunter ������ �������� �
             * ��������� ��� � ���������� ��� ������������� �����.
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
             * ������� ���������� � ���, ��� ��������� ������, ���� ���������
             * �������� � ������ �������
             *
             * @see boolean Main.debugMode
             */
            if (Main.debugMode = true) {
                System.err.println("path:app.VacancyList"); //@debug
            }
            System.err.println("��������� ������ ��� ��������� ��������.");
            e_VacancySearch.printStackTrace();
        }
    }
}
