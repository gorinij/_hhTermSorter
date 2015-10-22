package app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ru.yaal.project.hhapi.HhApi;
import ru.yaal.project.hhapi.search.SearchException;
import ru.yaal.project.hhapi.search.parameter.Text;
import ru.yaal.project.hhapi.vacancy.VacancyList;

/**
 *
 * @author gorjnich
 */
public class Engine {

    private ArrayList<String> sortedTerms = new ArrayList<>();

    private String[] validedTerms;

    public Engine(Integer limit, String name) {
        this.validedTerms = new String[]{
            "JAVA", "PHP", "PYTHON", "RUBY", "JS", "JAVASCRIPT",
            "SQL", "ORACLE", "CORE", "HTML", "JPA", "CSS", "DB",
            "ENTERPRISE", "GIT", "JBOSS", "JMS", "JSP", "MVC",
            "SAP", "SERVLETS", "UNIX", "AGILE", "AJAX", "ANGULAR",
            "APACHE", "API", "BOOTSTRAP", "COLLECTIONS", "CONCURRENCY",
            "CRM", "DEPLOY", "ECLIPSE", "EE", "EJB", "EXCEL", "FRAMEWORK",
            "ESB", "EPC", "ERP", "GLASSFISH", "GROOVY", "GWT", "HIBERNATE",
            "JASPER", "JDBC", "JEE", "J2EE", "JSF", "JSON", "JUNIT",
            "KNOCKOUT", "MERCURIAL", "MSSQL", "MYSQL", "NODE", "NOSQL",
            "OMS", "ORM", "PERL", "PLAY", "REACT", "REPORTS", "SCALA",
            "RESIN", "SCRUM", "SE", "SECURITY", "SHELL", "SPHERE", "SVN",
            "TOMCAT", "SOAP", "SUBVERSION", "WEBLOGIC", "ZEND", "FRAMEWORK",
            "ZF", "YII", "CMS", "SYMFONY", "LINUX", "REDIS", "MONGODB",
            "POSTGRESQL", "POSTGRES", "POSTGRE", "REST", "DRUPAL", "XML",
            "GITHUB", "CODEIGNITER", "LARAVEL", "NGINX", "RABBITMQ", "SOAP",
            "SPHINX", "BASH", "BITRIX", "1C", "HIGH", "HIGHLOAD", "BACKEND",
            "CAKEPHP", "GREP", "JOOMLA", "LUNUX", "REDMINE", "ANGULARJS",
            "BACKBONE", "COMPOSER", "MEMCACHED", "MERCURIAL", "SSH",
            "WORDPRESS", "ANDROID", "BITBUCKET", "DOCTRINE", "FRONTEND",
            "IOS", "JIRA", "MEMCACHE", "PHOTOSHOP", "SMARTY", "HTTP", "MODX",
            "NETCAT", "NODEJS", "PATTERNS", "PHALCON", "PHPUNIT", "UNIT",
            "PRESTASHOP", "SAAS", "SENIOR", "USABILITY", "WINDOWS", "ASP",
            "CLOUD", "COMMON", "CURL", "DJANGO", "EXTJS", "EXT", "FLEXIBLE",
            "FRAMEWORKS", "FULLSTACK", "IAAS", "IDEA", "INNODB", "INTELLIJ",
            "KOHANA", "LESS", "SASS", "STYLUS", "LMS", "MAGENTO", "MONGO",
            "MYISAM", "PATTERN", "RAILS", "REDDIS", "RESTFUL", "RESPONSIBILITY",
            "RESPONSIVE", "ROR", "SCSS", "SOLID", "SYMPHONY", "WEBSOCKETS",
            "ZENDFRAMEWORK", "ZABBIX", "XAMPP", "XAMARIN", "NET", "VPS", "VPN",
            "WP", "SILEX", "SPRING", "CONCURRENT", "WEBSPHERE", "CASSANDRA",
            "HADOOP", "JAX", "JBPM", "KNOCKOUTJS", "JQUERY", "NETBEANS",
            "RESPONSIBILITIES", "SERVLET", "WSDL", "XSLT", "GRADLE", "MAVEN",
            "OOP", "XSD", "", "CISCO", "WEBLOGIC", "LOGIC", "JMM", "IOS",
            "FLEXIBLE", "CVS"
        };
        try {
            /* Получаем с сайта HH некоторый набор запрошенных вакансий */
            VacancyList hhVacancies = findVacancies(limit, name);
            /* Преобразуем эти вакансии в удобный для использования вид */
            app.VacancyList vacancies = getVacancies(hhVacancies);
            /* Получаем пул описаний этих вакансий */
            VacancyDescriptionList descriptions = getDescriptions(vacancies);
            /* Из каждого описания получаем список терминов */
            VacancyTermList terms = getTerms(descriptions);
            /* Сортируем эти термины по рейтингу */
            SortedSet<Map.Entry<String, Integer>> t = sortTerms(terms);
            /* Выводим их на экран */
            outputTerms(t);
        } catch (SearchException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getSortedTerms() {
        return sortedTerms;
    }

    /**
     * Вывод технологий по рейтингу
     *
     * @param terms
     */
    private void outputTerms(SortedSet<Map.Entry<String, Integer>> terms) {
        for (Map.Entry<String, Integer> entry : terms) {
            String s = entry.getKey() + ":" + entry.getValue();
            for (String t : validedTerms) {
                Pattern p = Pattern.compile(".*:");
                Matcher m = p.matcher(s);
                if (m.find()) {
                    int length = m.group().length();
                    String str = m.group().substring(0, length - 1);
                    if (str.equals(t)) {
                        sortedTerms.add(s);
                        break;
                    }
                }
            }
            Main.echoLn(s);
        }
    }

    /**
     * Абстракция для сортировки терминов и их вывода вместе с рейтингом
     *
     * @param terms
     */
    private SortedSet<Map.Entry<String, Integer>> sortTerms(ArrayList<HashSet<String>> terms) {
        TreeMap<String, Integer> hash = new TreeMap<>();
        for (HashSet<String> termList : terms) {
            for (String term : termList) {
                if (hash.get(term) == null) {
                    hash.put(term, 1);
                } else {
                    int value = hash.get(term);
                    hash.put(term, ++value);
                }
            }
        }
        return entriesSortedByValues(hash, false);
    }

    /**
     * Функция для сортировки терминов по возростанию или убиванию.
     *
     * @param <String>
     * @param <Integer>
     * @param map
     * @param sortedBy
     * @return
     * @lurk google "java treemap sort by value"
     */
    private <String, Integer extends Comparable<? super Integer>> SortedSet<Map.Entry<String, Integer>>
            entriesSortedByValues(Map<String, Integer> map, final boolean sortedBy) {
        /*
         То, что здесь происходит - крайне расплывчато.
         На выходе, в зависимости от того ИСТИНА передана вторым параметром
         или ЛОЖЬ, функция отсортирует переданный массив терминов по возростанию
         или убыванию соотвественноб с точки зрения частоты их упоминания.
         */
        SortedSet<Map.Entry<String, Integer>> sortedEntries = new TreeSet<>(
                new Comparator<Map.Entry<String, Integer>>() {
                    @Override
                    public int compare(Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) {
                        int res;
                        if (sortedBy) {
                            res = e1.getValue().compareTo(e2.getValue());
                        } else {
                            res = e2.getValue().compareTo(e1.getValue());
                        }
                        return res != 0 ? res : 1;
                    }
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

    /**
     * Получение пула терминов со всех вакансий
     *
     * @param descriptions
     * @return [Str][]
     */
    private static VacancyTermList getTerms(app.VacancyDescriptionList descriptions) {
        /* Сущность пула терминов */
        VacancyTermList termsCollection = new VacancyTermList();
        /* Итератор описаний */
        Iterator<String> descIterator = descriptions.iterator();
        /* Итерация по каждому из описаний */
        while (descIterator.hasNext()) {
            /* 
             Получаем текст из каждого описания.
             Он буде использоваться для парсинга.
             */
            String s = descIterator.next();
            /*
             Ищем термины по следующему паттерну. 
             Смысл в том, чтобы найти все английские слова в вакансии.
             Подовляющее большинство технологий обозначаются именно через 
             английскую терминологию.
             */
            /**
             * @issue Нужно добавить и русскоязычкую терминологию, терминологии
             * содержащие в себе точку и буквенное продолжение.
             */
            Pattern englishPatern = Pattern.compile("[\\s(]{1}[a-zA-Z]+[)]?");
            /* Парсинг */
            Matcher matcher = englishPatern.matcher(s);
            HashSet<String> terms = new HashSet<>();
            String term;
            /* 
             Пока в тексте описания имеются соответствия шаблону, 
             добавляем каждый из них в пул 
             */
            while (matcher.find()) {
                term = matcher.group().toUpperCase();
                term = term.replaceAll("[\\(\\)\\s]", "");
                terms.add(term);
            }
            /*
             Массив найденных терминов текущего пункта описания заносим в еще 
             больший массив учитывающий все описания 
             */
            termsCollection.add(terms);
        }

        /* Возвращаем пул терминов */
        return termsCollection;
    }

    /**
     * Функция возвращает описания всех вакансий
     *
     * @param vacancies
     * @return ArrayList<String>
     * @throws java.io.IOException
     */
    private static VacancyDescriptionList getDescriptions(app.VacancyList vacancies)
            throws IOException {
        /*
         Создаем динамический массив, в котором будет храниться описания всех
         найденных вакансий. 
         Ее потом и будем и анализировать на наличие терминов-технологий
         */
        VacancyDescriptionList description = new VacancyDescriptionList();
        for (app.Vacancy vacancy : vacancies) {
            description.add(vacancy.getDescription());
        }

        /* Возвращаем пул описаний всех найденных вакансий */
        return description;
    }

    /**
     * Функция поиски вакансий
     *
     * @param vacancyLimit
     * @param vacancyName
     * @return
     */
    private static app.VacancyList getVacancies(VacancyList vacancies) {
        app.VacancyList vacancyPool = null;
        try {
            /*
             * Пользуемся кастомной коллекцией содержащей кастомный класс
             * описания вакансии.
             *
             * @see app.VacancyList
             */
            vacancyPool = new app.VacancyList(vacancies);

            /*
             * Мы получаем наши вакансии в юзабельной форме. Можем теперь
             * номрально с ним работать.
             */
            for (Object el : vacancyPool) {
                /*
                 * Создаем объект кастомного класса для хранения объекта
                 *
                 * @see app.Vacancy
                 */
                app.Vacancy v = (app.Vacancy) el;
                Main.echoLn(v.getTitle());
                Main.echoLn(v.getDescription());
                Main.echoLn();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        /* Возвращаем юзабельный пул вакансий */
        return vacancyPool;
    }

    /**
     * Функция поиска вакансий
     *
     * @param limit Количество вакансий
     * @param name Ключевое слово для вакансий
     * @return hhApi.VacancyList Возвращает список найденных вакансий
     * @throws SearchException
     */
    private static VacancyList findVacancies(
            int limit, String name)
            throws SearchException {
        /*
         * Используя библиотеку ru.yaal.project.hhapi отправляем запрос на
         * HeadHunter, на поиск соответствующих вакансий.
         *
         * @see ru.yaal.project.hhapi.vacancy.VacancyList
         */
        VacancyList vacancies
                = HhApi.search(limit, new Text(name));

        /* Возвращаем HhApi-версию вакансий */
        return vacancies;
    }

    /*<editor-fold desc="Кунсткамера"> -----------------------------------------------------------*/
    /**
     * Вывод терминов в вакансиях в консоль
     *
     * @param terms
     */
    @Deprecated
    private void termsOutput(ArrayList<HashSet<String>> terms) {
        /* Получаем итератор пули терминов всех вакансий*/
        Iterator termsIterator = terms.iterator();
        while (termsIterator.hasNext()) {
            HashSet<String> termList = (HashSet) termsIterator.next();
            /* Получаем итератор пула всех терминов в данном описании */
            Iterator termListIterator = termList.iterator();
            while (termListIterator.hasNext()) {
                /* Получаем значение каждого термина */
                String s = (String) termListIterator.next();
                Main.echoLn(s);
            }
            Main.echoLn();
        }
    }

    /**
     * Функция сохранения полученных данных в файл
     *
     * @param vacancies
     * @return ArrayList<String>
     * @throws java.io.IOException
     */
    private static ArrayList<String> saveIntoFile(app.VacancyList vacancies) throws IOException {
        /*
         Создаем динамический массив, в котором будет храниться описания всех
         найденных вакансий. 
         Ее потом и будем и анализировать на наличие терминов-технологий
         */
        ArrayList<String> description = new ArrayList<>();
        /* Файл в котором будет храниться информация о вакансиях*/
        File file = new File("vacancies.html");
        /* При отсутствии файла, создаем его */
        if (!file.exists()) {
            file.createNewFile();
        }
        /* Записавыем данные о вакансиях в файл */
        try (
                PrintWriter pWriter = new PrintWriter(file);
                BufferedWriter writer = new BufferedWriter(pWriter);) {
            for (app.Vacancy vacancy : vacancies) {
                writer.write("<h2>" + vacancy.getTitle() + "</h2>");
                writer.write(vacancy.getId());
                writer.write(vacancy.getCompany());
                writer.write(vacancy.getDescription());
                writer.write(vacancy.getDate());
                writer.write("<br/>");
                description.add(vacancy.getDescription());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* Возвращаем пул описаний всех найденных вакансий */
        return description;
    }
    /*</editor-fold>*/
}
