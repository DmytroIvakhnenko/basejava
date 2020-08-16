package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static ru.javawebinar.basejava.model.ContactType.*;
import static ru.javawebinar.basejava.model.ResumeSectionType.*;

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
    ListOfTextSections ach = new ListOfTextSections();
    ach.addItem(new SingleTextSection("С 2013 года: ..."));
    ach.addItem(new SingleTextSection("Реализация двухфакторной ..."));
    ach.addItem(new SingleTextSection("Налаживание процесса ..."));
    ach.addItem(new SingleTextSection("Реализация c нуля ..."));
    ach.addItem(new SingleTextSection("Создание JavaEE фреймворка ..."));
    ach.addItem(new SingleTextSection("Реализация протоколов ..."));
    resume.addSection(ACHIEVEMENT, ach);
    /*----------------------------------------------------------------------------------------------------------*/
    ListOfTextSections qua = new ListOfTextSections();
    qua.addItem(new SingleTextSection("JEE AS", "GlassFish (v2.1, v3), OC4J ..."));
    qua.addItem(new SingleTextSection("Version control", "Subversion, Git ..."));
    qua.addItem(new SingleTextSection("DB", "PostgreSQL(наследование ..."));
    qua.addItem(new SingleTextSection("Languages", "Java, Scala ..."));
    resume.addSection(QUALIFICATIONS, qua);
    /*----------------------------------------------------------------------------------------------------------*/
    ListOfTextSections exp = new ListOfTextSections();
    AbstractTextSection expLine1 =
        new WorkPeriodSection(
            "Java Online Projects.",
            "Создание, организация ...",
            YearMonth.parse("10/2013", format),
            YearMonth.now(),
            "Автор проекта.");
    AbstractTextSection expLine2 =
        new WorkPeriodSection(
            "Wrike.",
            "Проектирование и разработка ...",
            YearMonth.parse("10/2014", format),
            YearMonth.parse("10/2016", format),
            "Старший разработчик (backend).");
    AbstractTextSection expLine3 =
        new WorkPeriodSection(
            "RIT Center.",
            "Организация процесса разработки ...",
            YearMonth.parse("04/2012", format),
            YearMonth.parse("10/2014", format),
            "Java архитектор");
    exp.addItem(expLine1);
    exp.addItem(expLine2);
    exp.addItem(expLine3);
    resume.addSection(EXPERIENCE, exp);
    /*----------------------------------------------------------------------------------------------------------*/
    ListOfTextSections edu = new ListOfTextSections();
    AbstractTextSection eduLine1 =
        new WorkPeriodSection(
            "Coursera",
            "Functional Programming ...",
            YearMonth.parse("03/2013", format),
            YearMonth.parse("05/2013", format),
            null);
    AbstractTextSection eduLine2 =
        new WorkPeriodSection(
            "Luxoft",
            "Курс \"Объектно-ориентированный  ...",
            YearMonth.parse("03/2011", format),
            YearMonth.parse("04/2011", format),
            null);
    AbstractTextSection eduLine3 =
        new WorkPeriodSection(
            "Siemens AG",
            "3 месяца обучения мобильным ...",
            YearMonth.parse("01/2005", format),
            YearMonth.parse("04/2005", format),
            null);

    ListOfTextSections eduList1 = new ListOfTextSections("Санкт-Петербургский национальный ...");
    AbstractTextSection eduList1Line1 =
        new WorkPeriodSection(
            "Аспирантура (программист С ...",
            YearMonth.parse("09/1993", format),
            YearMonth.parse("07/1996", format));
    AbstractTextSection eduList1Line2 =
        new WorkPeriodSection(
            "Инженер (программист Fortran ...",
            YearMonth.parse("09/1987", format),
            YearMonth.parse("07/1993", format));
    eduList1.addItem(eduList1Line1);
    eduList1.addItem(eduList1Line2);

    edu.addItem(eduLine1);
    edu.addItem(eduLine2);
    edu.addItem(eduLine3);
    edu.addItem(eduList1);

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

    Map<ResumeSectionType, AbstractTextSection> resumeMap = resume.getAllSections();
    for (ResumeSectionType key : resumeMap.keySet()) {
      if (key != null) {
        System.out.println(key.getTitle());
      }
      System.out.println(resumeMap.get(key).toString());
    }
  }
}
