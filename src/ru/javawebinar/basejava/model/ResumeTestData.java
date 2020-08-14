package ru.javawebinar.basejava.model;

import java.util.Map;

import static ru.javawebinar.basejava.model.ResumeSectionType.*;

public class ResumeTestData {
    public static Resume setTestData() {
        Resume resume = new Resume("Григорий Кислин");
        /*----------------------------------------------------------------------------------------------------------*/
        //contacts
        ResumeListOfTextSections con = new ResumeListOfTextSections();
        con.saveItem(new ResumeSingleTextSection("Тел.", "+7(921) 855-0482"));
        con.saveItem(new ResumeSingleTextSection("Skype", "grigory.kislin"));
        con.saveItem(new ResumeSingleTextSection("Почта", "gkislin@yandex.ru"));
        con.saveItem(new ResumeSingleTextSection("Профиль LinkedIn", "https://www.linkedin.com/in/gkislin"));
        resume.addSection(null, con);
        /*----------------------------------------------------------------------------------------------------------*/
        resume.addSection(PERSONAL, new ResumeSingleTextSection("Аналитический склад ума ..."));
        /*----------------------------------------------------------------------------------------------------------*/
        resume.addSection(OBJECTIVE, new ResumeSingleTextSection("Ведущий стажировок и ..."));
        /*----------------------------------------------------------------------------------------------------------*/
        ResumeListOfTextSections ach = new ResumeListOfTextSections();
        ach.saveItem(new ResumeSingleTextSection("С 2013 года: ..."));
        ach.saveItem(new ResumeSingleTextSection("Реализация двухфакторной ..."));
        ach.saveItem(new ResumeSingleTextSection("Налаживание процесса ..."));
        ach.saveItem(new ResumeSingleTextSection("Реализация c нуля ..."));
        ach.saveItem(new ResumeSingleTextSection("Создание JavaEE фреймворка ..."));
        ach.saveItem(new ResumeSingleTextSection("Реализация протоколов ..."));
        resume.addSection(ACHIEVEMENT, ach);
        /*----------------------------------------------------------------------------------------------------------*/
        ResumeListOfTextSections qua = new ResumeListOfTextSections();
        qua.saveItem(new ResumeSingleTextSection("JEE AS", "GlassFish (v2.1, v3), OC4J ..."));
        qua.saveItem(new ResumeSingleTextSection("Version control", "Subversion, Git ..."));
        qua.saveItem(new ResumeSingleTextSection("DB", "PostgreSQL(наследование ..."));
        qua.saveItem(new ResumeSingleTextSection("Languages", "Java, Scala ..."));
        resume.addSection(QUALIFICATIONS, qua);
        /*----------------------------------------------------------------------------------------------------------*/
        ResumeListOfTextSections exp = new ResumeListOfTextSections();
        ResumeSingleTextSection expLine1 = new ResumeSingleTextSection("Java Online Projects.Автор проекта.", "Создание, организация ...");
        expLine1.setDates("10/2013", "now");
        ResumeSingleTextSection expLine2 = new ResumeSingleTextSection("Wrike. Старший разработчик (backend).", "Проектирование и разработка ...");
        expLine2.setDates("10/2014", "10/2016");
        ResumeSingleTextSection expLine3 = new ResumeSingleTextSection("RIT Center. Java архитектор", "Организация процесса разработки ...");
        expLine3.setDates("04/2012", "10/2014");
        exp.saveItem(expLine1);
        exp.saveItem(expLine2);
        exp.saveItem(expLine3);
        resume.addSection(EXPERIENCE, exp);
        /*----------------------------------------------------------------------------------------------------------*/
        ResumeListOfTextSections edu = new ResumeListOfTextSections();
        ResumeSingleTextSection eduLine1 = new ResumeSingleTextSection("Coursera", "Functional Programming ...");
        eduLine1.setDates("03/2013", "05/2013");
        ResumeSingleTextSection eduLine2 = new ResumeSingleTextSection("Luxoft", "Курс \"Объектно-ориентированный  ...");
        eduLine2.setDates("03/2011", "04/2011");
        ResumeSingleTextSection eduLine3 = new ResumeSingleTextSection("Siemens AG", "3 месяца обучения мобильным ...");
        eduLine3.setDates("01/2005", "04/2005");

        ResumeListOfTextSections eduList1 = new ResumeListOfTextSections("Санкт-Петербургский национальный ...");
        ResumeSingleTextSection eduList1Line1 = new ResumeSingleTextSection("Аспирантура (программист С ...");
        eduList1Line1.setDates("09/1993", "07/1996");
        ResumeSingleTextSection eduList1Line2 = new ResumeSingleTextSection("Инженер (программист Fortran ...");
        eduList1Line2.setDates("09/1987", "07/1993");
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
        //print to console
        Resume resume = setTestData();
        System.out.println(resume.getFullName());
        Map<ResumeSectionType, AbstractResumeTextSection> resumeMap = resume.getAllSections();
        for (ResumeSectionType key : resumeMap.keySet()) {
            if (key != null) {
                System.out.println(key.getTitle());
            }
            System.out.println(resumeMap.get(key).toString());
        }
    }
}
