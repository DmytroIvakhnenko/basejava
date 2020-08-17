package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static ru.javawebinar.basejava.model.ContactType.*;
import static ru.javawebinar.basejava.model.SectionType.*;

public class MainResumeTestData {
    public static Resume setTestData() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/yyyy");
        Resume resume = new Resume("Григорий Кислин");
        /*----------------------------------------------------------------------------------------------------------*/
        // contacts
        resume.addContact(TELEPHONE, "+7(921) 855-0482");
        resume.addContact(SKYPE, "grigory.kislin");
        resume.addContact(EMAIL, "gkislin@yandex.ru");
        resume.addContact(LINKEDIN, "https://www.linkedin.com/in/gkislin");
        /*----------------------------------------------------------------------------------------------------------*/
        resume.addSection(PERSONAL, new SingleTextSection("Аналитический склад ума ..."));
        /*----------------------------------------------------------------------------------------------------------*/
        resume.addSection(OBJECTIVE, new SingleTextSection("Ведущий стажировок и ..."));
        /*----------------------------------------------------------------------------------------------------------*/
        ListOfSections<SingleTextSection> ach = new ListOfSections<>();
        ach.addItem(new SingleTextSection("С 2013 года: ..."));
        ach.addItem(new SingleTextSection("Реализация двухфакторной ..."));
        ach.addItem(new SingleTextSection("Налаживание процесса ..."));
        ach.addItem(new SingleTextSection("Реализация c нуля ..."));
        ach.addItem(new SingleTextSection("Создание JavaEE фреймворка ..."));
        ach.addItem(new SingleTextSection("Реализация протоколов ..."));
        resume.addSection(ACHIEVEMENT, ach);
        /*----------------------------------------------------------------------------------------------------------*/
        ListOfSections<SingleTextSection> qua = new ListOfSections<>();
        qua.addItem(new SingleTextSection("JEE AS", "GlassFish (v2.1, v3), OC4J ..."));
        qua.addItem(new SingleTextSection("Version control", "Subversion, Git ..."));
        qua.addItem(new SingleTextSection("DB", "PostgreSQL(наследование ..."));
        qua.addItem(new SingleTextSection("Languages", "Java, Scala ..."));
        resume.addSection(QUALIFICATIONS, qua);
        /*----------------------------------------------------------------------------------------------------------*/
        ListOfSections<WorkExperience> exp = new ListOfSections<>();
        WorkExperience expLine1 =
                new WorkExperience(
                        YearMonth.parse("10/2013", format),
                        YearMonth.now(),
                        "Автор проекта.",
                        "Создание, организация ...",
                        "Java Online Projects.",
                        "https://javaops.ru/");
        WorkExperience expLine2 =
                new WorkExperience(
                        YearMonth.parse("10/2014", format),
                        YearMonth.parse("10/2016", format),
                        "Старший разработчик (backend).",
                        "Проектирование и разработка ...",
                        "Wrike.",
                        "https://www.wrike.com/");
        WorkExperience expLine3 =
                new WorkExperience(
                        YearMonth.parse("04/2012", format),
                        YearMonth.parse("10/2014", format),
                        "Java архитектор.",
                        "Организация процесса разработки ...",
                        "RIT Center.",
                        null);
        exp.addItem(expLine1);
        exp.addItem(expLine2);
        exp.addItem(expLine3);
        resume.addSection(EXPERIENCE, exp);
        /*----------------------------------------------------------------------------------------------------------*/
        ListOfSections<WorkExperience> edu = new ListOfSections<>();
        WorkExperience eduLine1 =
                new WorkExperience(
                        YearMonth.parse("03/2013", format),
                        YearMonth.parse("05/2013", format),
                        "Functional Programming ...",
                        null,
                        "Coursera",
                        "https://www.coursera.org/learn/progfun1");
        WorkExperience eduLine2 =
                new WorkExperience(
                        YearMonth.parse("03/2011", format),
                        YearMonth.parse("04/2011", format),
                        "Курс \"Объектно-ориентированный  ...",
                        null,
                        "Luxoft",
                        "https://www.luxoft-training.ru/kurs/obektno-orientirovannyy_analiz_i_proektirovanie_na_uml.html");
        WorkExperience eduLine3 =
                new WorkExperience(
                        YearMonth.parse("01/2005", format),
                        YearMonth.parse("04/2005", format),
                        "3 месяца обучения мобильным ...",
                        null,
                        "Siemens AG",
                        "https://new.siemens.com/ru/ru.html"
                );
        WorkExperience eduLine4 =
                new WorkExperience(
                        YearMonth.parse("09/1993", format),
                        YearMonth.parse("07/1996", format),
                        "Аспирантура (программист С ...",
                        null,
                        "Санкт-Петербургский национальный ...",
                        "https://itmo.ru/ru/");
        WorkExperience eduLine5 =
                new WorkExperience(
                        YearMonth.parse("09/1987", format),
                        YearMonth.parse("07/1993", format),
                        "Инженер (программист Fortran ...",
                        null,
                        "Санкт-Петербургский национальный ...",
                        "https://itmo.ru/ru/");
        edu.addItem(eduLine1);
        edu.addItem(eduLine2);
        edu.addItem(eduLine3);
        edu.addItem(eduLine4);
        edu.addItem(eduLine5);

        resume.addSection(EDUCATION, edu);
        /*----------------------------------------------------------------------------------------------------------*/
        return resume;
    }

    public static void main(String[] args) {
        // print to console
        Resume resume = setTestData();
        System.out.println(resume.getFullName());
        Map<ContactType, String> contactsMap = resume.getAllContacts();
        for (ContactType key : contactsMap.keySet()) {
            System.out.println(key.getTitle() + ": " + contactsMap.get(key));
        }

        Map<SectionType, AbstractSection> resumeMap = resume.getAllSections();
        for (SectionType key : resumeMap.keySet()) {
            if (key != null) {
                System.out.println(key.getTitle());
            }
            System.out.println(resumeMap.get(key).toString());
        }
    }
}
