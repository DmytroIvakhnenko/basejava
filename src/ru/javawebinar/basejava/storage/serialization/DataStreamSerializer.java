package ru.javawebinar.basejava.storage.serialization;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements SerializationStrategy {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                switch (entry.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE:
                        TextSection t = (TextSection) entry.getValue();
                        dos.writeUTF(t.getText());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListSection ls = (ListSection) entry.getValue();
                        dos.writeInt(ls.getItems().size());
                        for (String s : ls.getItems()) {
                            dos.writeUTF(s);
                        }
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        ExperienceSection es = (ExperienceSection) entry.getValue();
                        dos.writeInt(es.getExperienceList().size());
                        for (Experience e : es.getExperienceList()) {
                            dos.writeUTF(e.getPlace().getName());
                            if (e.getPlace().getHomepage() != null) {
                                dos.writeUTF(e.getPlace().getHomepage());
                            } else {
                                dos.writeUTF("null");
                            }
                            dos.writeInt(e.getPositions().size());
                            for (Experience.Position p : e.getPositions()) {
                                dos.writeUTF(p.getPosition());
                                if (p.getDescription() != null) {
                                    dos.writeUTF(p.getDescription());
                                } else {
                                    dos.writeUTF("null");
                                }
                                dos.writeUTF(p.getStartDate().format(DateTimeFormatter.ofPattern("MM/yyyy")));
                                dos.writeUTF(p.getEndDate().format(DateTimeFormatter.ofPattern("MM/yyyy")));
                            }
                        }
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + entry.getKey());
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType st = SectionType.valueOf(dis.readUTF());
                switch (st) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(st, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        int listSize = dis.readInt();
                        List<String> ls1 = new ArrayList<>();
                        for (int j = 0; j < listSize; j++) {
                            ls1.add(dis.readUTF());
                        }
                        resume.addSection(st, new ListSection(ls1));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Experience> ls2 = null;
                        List<Experience.Position> ls3 = null;
                        listSize = dis.readInt();
                        ls2 = new ArrayList<>(listSize);
                        for (int j = 0; j < listSize; j++) {
                            String name = dis.readUTF();
                            String homepage = dis.readUTF();
                            if (homepage.equals("null")) {
                                homepage = null;
                            }
                            Link l = new Link(name, homepage);
                            int listSize2 = dis.readInt();
                            ls3 = new ArrayList<>(listSize2);
                            for (int k = 0; k < listSize2; k++) {
                                String position = dis.readUTF();
                                String description = dis.readUTF();
                                if (description.equals("null")) {
                                    description = null;
                                }
                                String startDate = dis.readUTF();
                                String endDate = dis.readUTF();
                                Experience.Position pos = new Experience.Position(YearMonth.parse(startDate, DateTimeFormatter.ofPattern("MM/yyyy")), YearMonth.parse(endDate, DateTimeFormatter.ofPattern("MM/yyyy")), position, description);
                                ls3.add(pos);
                            }
                            Experience e = new Experience(ls3, l);
                            ls2.add(e);
                        }
                        ExperienceSection ex = new ExperienceSection(ls2);
                        resume.addSection(st, ex);
                        break;
                }
            }
            return resume;
        }
    }
}

