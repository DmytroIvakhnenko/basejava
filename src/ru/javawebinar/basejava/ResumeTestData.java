package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
        resume.addSection(PERSONAL, new TextSection("Аналитический склад ума ..."));
        /*----------------------------------------------------------------------------------------------------------*/
        resume.addSection(OBJECTIVE, new TextSection("Ведущий стажировок и ..."));
        /*----------------------------------------------------------------------------------------------------------*/
        ListSection ach = new ListSection("С 2013 года: ...", "Реализация двухфакторной ...", "Налаживание процесса ...", "Реализация c нуля ...", "Создание JavaEE фреймворка ...", "Реализация протоколов ...");
        resume.addSection(ACHIEVEMENT, ach);
        /*----------------------------------------------------------------------------------------------------------*/
        ListSection qua = new ListSection("JEE AS: GlassFish (v2.1, v3), OC4J ...", "Version control: Subversion, Git ...", "DB: PostgreSQL(наследование ...", "Languages: Java, Scala ...");
        resume.addSection(QUALIFICATIONS, qua);
        /*----------------------------------------------------------------------------------------------------------*/
        List<Experience> expl = new ArrayList<>();
        expl.add(new Experience("Java Online Projects.", "https://javaops.ru/", new Experience.Position(YearMonth.parse("10/2013", format), YearMonth.now(), "Автор проекта.", "Создание, организация ...")));
        expl.add(new Experience("Wrike.", "https://www.wrike.com/", new Experience.Position(YearMonth.parse("10/2014", format), YearMonth.parse("10/2016", format), "Старший разработчик (backend).", "Проектирование и разработка ...")));
        expl.add(new Experience("RIT Center.", null, new Experience.Position(YearMonth.parse("04/2012", format), YearMonth.parse("10/2014", format), "Java архитектор.", "Организация процесса разработки ...")));
        ExperienceSection exp = new ExperienceSection(expl);
        resume.addSection(EXPERIENCE, exp);
        /*----------------------------------------------------------------------------------------------------------*/
        List<Experience> edul = new ArrayList<>();
        edul.add(new Experience("Coursera", "https://www.coursera.org/learn/progfun1", new Experience.Position(YearMonth.parse("03/2013", format), YearMonth.parse("05/2013", format), "Functional Programming ...", null)));
        edul.add(new Experience("Luxoft", "https://www.luxoft-training.ru/kurs/obektno-orientirovannyy_analiz_i_proektirovanie_na_uml.html", new Experience.Position(YearMonth.parse("03/2011", format), YearMonth.parse("04/2011", format), "Курс \"Объектно-ориентированный  ...", null)));
        edul.add(new Experience("Siemens AG", "https://new.siemens.com/ru/ru.html", new Experience.Position(YearMonth.parse("01/2005", format), YearMonth.parse("04/2005", format), "3 месяца обучения мобильным ...", null)));
        edul.add(new Experience("Санкт-Петербургский национальный ...", "https://itmo.ru/ru/", new Experience.Position(YearMonth.parse("09/1993", format), YearMonth.parse("07/1996", format), "Аспирантура (программист С ...", null),
                new Experience.Position(YearMonth.parse("09/1987", format), YearMonth.parse("07/1993", format), "Инженер (программист Fortran ...", null)));
        ExperienceSection edu = new ExperienceSection(edul);
        resume.addSection(EDUCATION, edu);
        return resume;
    }

    public static Resume setResumeTestDataWithNoSectionsAndContacts(String uuid, String fullName) {
        return new Resume(uuid, fullName);
    }

    public static Resume setResumeTestDataWithNoSections(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.addContact(TELEPHONE, "+7(921) 855-0482");
        resume.addContact(SKYPE, "grigory.kislin");
        resume.addContact(EMAIL, "gkislin@yandex.ru");
        resume.addContact(LINKEDIN, "https://www.linkedin.com/in/gkislin");
        return resume;
    }

    public static void main(String[] args) {
        // print to console
        Resume resume = setResumeTestData("testUuid", "Григорий Кислин");
        System.out.println(resume.getFullName());
        Map<ContactType, String> contactsMap = resume.getContacts();
        for (ContactType key : contactsMap.keySet()) {
            System.out.println(key.getTitle() + ": " + contactsMap.get(key));
        }

        Map<SectionType, AbstractSection> resumeMap = resume.getSections();
        for (SectionType key : resumeMap.keySet()) {
            if (key != null) {
                System.out.println(key.getTitle());
            }
            System.out.println(resumeMap.get(key).toString());
        }
    }
}
