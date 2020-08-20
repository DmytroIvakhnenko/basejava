package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static ru.javawebinar.basejava.model.ContactType.*;
import static ru.javawebinar.basejava.model.SectionType.*;

public class ResumeTestData {
    public static Resume setResumeTestData(String uuid, String fullName) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/yyyy");
        Resume resume = new Resume(uuid, fullName);
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
        ListOfSections<Experience> exp = new ListOfSections<>();
        exp.addItem(new Experience("Java Online Projects.", "https://javaops.ru/", new Experience.Position(YearMonth.parse("10/2013", format), YearMonth.now(), "Автор проекта.", "Создание, организация ...")));
        exp.addItem(new Experience("Wrike.", "https://www.wrike.com/", new Experience.Position(YearMonth.parse("10/2014", format), YearMonth.parse("10/2016", format), "Старший разработчик (backend).", "Проектирование и разработка ...")));
        exp.addItem(new Experience("RIT Center.", null, new Experience.Position(YearMonth.parse("04/2012", format), YearMonth.parse("10/2014", format), "Java архитектор.", "Организация процесса разработки ...")));
        resume.addSection(EXPERIENCE, exp);
        /*----------------------------------------------------------------------------------------------------------*/
        ListOfSections<Experience> edu = new ListOfSections<>();
        edu.addItem(new Experience("Coursera", "https://www.coursera.org/learn/progfun1", new Experience.Position(YearMonth.parse("03/2013", format), YearMonth.parse("05/2013", format), "Functional Programming ...", null)));
        edu.addItem(new Experience("Luxoft", "https://www.luxoft-training.ru/kurs/obektno-orientirovannyy_analiz_i_proektirovanie_na_uml.html", new Experience.Position(YearMonth.parse("03/2011", format), YearMonth.parse("04/2011", format), "Курс \"Объектно-ориентированный  ...", null)));
        edu.addItem(new Experience("Siemens AG", "https://new.siemens.com/ru/ru.html", new Experience.Position(YearMonth.parse("01/2005", format), YearMonth.parse("04/2005", format), "3 месяца обучения мобильным ...", null)));
        edu.addItem(new Experience("Санкт-Петербургский национальный ...", "https://itmo.ru/ru/", new Experience.Position(YearMonth.parse("09/1993", format), YearMonth.parse("07/1996", format), "Аспирантура (программист С ...", null),
                new Experience.Position(YearMonth.parse("09/1987", format), YearMonth.parse("07/1993", format), "Инженер (программист Fortran ...", null)));
        resume.addSection(EDUCATION, edu);
        /*----------------------------------------------------------------------------------------------------------*/
        return resume;
    }

    public static void main(String[] args) {
        // print to console
        Resume resume = setResumeTestData("testUuid", "Григорий Кислин");
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
