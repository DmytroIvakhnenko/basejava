package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.util.Map;

import static ru.javawebinar.basejava.model.ResumeSectionType.*;

public class MainResumeTestData {
  public static Resume setTestData() {
    Resume resume = new Resume("Григорий Кислин");
    /*----------------------------------------------------------------------------------------------------------*/
    // contacts
    resume.addContact("Тел.", "+7(921) 855-0482");
    resume.addContact("Skype", "grigory.kislin");
    resume.addContact("Почта", "gkislin@yandex.ru");
    resume.addContact("Профиль LinkedIn", "https://www.linkedin.com/in/gkislin");
    /*----------------------------------------------------------------------------------------------------------*/
    resume.addSection(PERSONAL, new SingleTextSection("Аналитический склад ума ..."));
    /*----------------------------------------------------------------------------------------------------------*/
    resume.addSection(OBJECTIVE, new SingleTextSection("Ведущий стажировок и ..."));
    /*----------------------------------------------------------------------------------------------------------*/
    ListOfTextSections ach = new ListOfTextSections();
    ach.saveItem(new SingleTextSection("С 2013 года: ..."));
    ach.saveItem(new SingleTextSection("Реализация двухфакторной ..."));
    ach.saveItem(new SingleTextSection("Налаживание процесса ..."));
    ach.saveItem(new SingleTextSection("Реализация c нуля ..."));
    ach.saveItem(new SingleTextSection("Создание JavaEE фреймворка ..."));
    ach.saveItem(new SingleTextSection("Реализация протоколов ..."));
    resume.addSection(ACHIEVEMENT, ach);
    /*----------------------------------------------------------------------------------------------------------*/
    ListOfTextSections qua = new ListOfTextSections();
    qua.saveItem(new SingleTextSection("JEE AS", "GlassFish (v2.1, v3), OC4J ..."));
    qua.saveItem(new SingleTextSection("Version control", "Subversion, Git ..."));
    qua.saveItem(new SingleTextSection("DB", "PostgreSQL(наследование ..."));
    qua.saveItem(new SingleTextSection("Languages", "Java, Scala ..."));
    resume.addSection(QUALIFICATIONS, qua);
    /*----------------------------------------------------------------------------------------------------------*/
    ListOfTextSections exp = new ListOfTextSections();
    TextSection expLine1 =
        new WorkPeriodSection(
            "Java Online Projects.",
            "Создание, организация ...",
            "10/2013",
            "now",
            "Автор проекта.");
    TextSection expLine2 =
        new WorkPeriodSection(
            "Wrike.",
            "Проектирование и разработка ...",
            "10/2014",
            "10/2016",
            "Старший разработчик (backend).");
    TextSection expLine3 =
        new WorkPeriodSection(
            "RIT Center.",
            "Организация процесса разработки ...",
            "04/2012",
            "10/2014",
            "Java архитектор");
    exp.saveItem(expLine1);
    exp.saveItem(expLine2);
    exp.saveItem(expLine3);
    resume.addSection(EXPERIENCE, exp);
    /*----------------------------------------------------------------------------------------------------------*/
    ListOfTextSections edu = new ListOfTextSections();
    TextSection eduLine1 =
        new WorkPeriodSection("Coursera", "Functional Programming ...", "03/2013", "05/2013", null);
    TextSection eduLine2 =
        new WorkPeriodSection(
            "Luxoft", "Курс \"Объектно-ориентированный  ...", "03/2011", "04/2011", null);
    TextSection eduLine3 =
        new WorkPeriodSection(
            "Siemens AG", "3 месяца обучения мобильным ...", "01/2005", "04/2005", null);

    ListOfTextSections eduList1 = new ListOfTextSections("Санкт-Петербургский национальный ...");
    TextSection eduList1Line1 =
        new WorkPeriodSection("Аспирантура (программист С ...", "09/1993", "07/1996");
    TextSection eduList1Line2 =
        new WorkPeriodSection("Инженер (программист Fortran ...", "09/1987", "07/1993");
    eduList1.saveItem(eduList1Line1);
    eduList1.saveItem(eduList1Line2);

    edu.saveItem(eduLine1);
    edu.saveItem(eduLine2);
    edu.saveItem(eduLine3);
    edu.saveItem(eduList1);

    resume.addSection(EDUCATION, edu);
    /*----------------------------------------------------------------------------------------------------------*/
    return resume;
  }

  public static void main(String[] args) {
    // print to console
    Resume resume = setTestData();
    System.out.println(resume.getFullName());
    Map<String, String> contactsMap = resume.getAllContacts();
    for (String key : contactsMap.keySet()) {
      System.out.println(key + ": " + contactsMap.get(key));
    }

    Map<ResumeSectionType, TextSection> resumeMap = resume.getAllSections();
    for (ResumeSectionType key : resumeMap.keySet()) {
      if (key != null) {
        System.out.println(key.getTitle());
      }
      System.out.println(resumeMap.get(key).toString());
    }
  }
}
