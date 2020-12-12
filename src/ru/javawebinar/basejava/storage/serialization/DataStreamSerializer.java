package ru.javawebinar.basejava.storage.serialization;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataStreamSerializer implements SerializationStrategy {
    final static String NULL = "null";
    final static DateTimeFormatter MONTH_YEAR_FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            writeString(dos, r.getUuid());
            writeString(dos, r.getFullName());
            writeList(dos, r.getContacts().entrySet(), this::writeContacts);
            writeList(dos, r.getSections().entrySet(), this::writeSections);
        }
    }

    private void writeContacts(final DataOutputStream d, final Map.Entry<ContactType, String> e) throws IOException {
        writeString(d, e.getKey().name());
        writeString(d, e.getValue());
    }

    private void writeSections(final DataOutputStream dos, final Map.Entry<SectionType, AbstractSection> e) throws IOException {
        writeString(dos, e.getKey().name());
        switch (e.getKey()) {
            case PERSONAL:
            case OBJECTIVE:
                writeString(dos, ((TextSection) e.getValue()).getText());
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                writeList(dos, ((ListSection) e.getValue()).getItems(), this::writeString);
                break;
            case EXPERIENCE:
            case EDUCATION:
                writeList(dos, ((ExperienceSection) e.getValue()).getExperienceList(), this::writeExperience);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + e.getKey());
        }
    }

    private interface BiConsumerWithException<T, U> {
        void accept(T t, U u) throws IOException;
    }

    private <T> void writeList(final DataOutputStream dos, final Collection<T> l, BiConsumerWithException<DataOutputStream, T> sup) throws IOException {
        writeInt(dos, l.size());
        for (T elem : l) {
            sup.accept(dos, elem);
        }
    }

    private void writeExperience(final DataOutputStream dos, final Experience e) throws IOException {
        writeString(dos, e.getPlace().getName());
        writeString(dos, Optional.ofNullable(e.getPlace().getHomepage()).orElse(NULL));
        writeList(dos, e.getPositions(), this::writePosition);
    }

    private void writePosition(final DataOutputStream dos, final Experience.Position p) throws IOException {
        writeString(dos, p.getPosition());
        writeString(dos, Optional.ofNullable(p.getDescription()).orElse(NULL));
        writeString(dos, p.getStartDate().format(MONTH_YEAR_FORMATTER));
        writeString(dos, p.getEndDate().format(MONTH_YEAR_FORMATTER));
    }

    private void writeString(final DataOutputStream dos, final String s) throws IOException {
        dos.writeUTF(s);
    }

    private void writeInt(final DataOutputStream dos, final int i) throws IOException {
        dos.writeInt(i);
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = readString(dis);
            String fullName = readString(dis);
            Resume resume = new Resume(uuid, fullName);
            readResumeBlock(dis, resume, this::readContacts);
            readResumeBlock(dis, resume, this::readSections);
            return resume;
        }
    }

    private void readSections(DataInputStream dis, Resume resume) throws IOException {
        SectionType st = SectionType.valueOf(readString(dis));
        switch (st) {
            case PERSONAL:
            case OBJECTIVE:
                resume.setSection(st, new TextSection(readString(dis)));
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                resume.setSection(st, new ListSection(readList(dis, this::readString)));
                break;
            case EXPERIENCE:
            case EDUCATION:
                resume.setSection(st, new ExperienceSection(readList(dis, this::readExperience)));
                break;
        }
    }

    private void readContacts(DataInputStream dis, Resume resume) throws IOException {
        resume.setContact(ContactType.valueOf(readString(dis)), readString(dis));
    }

    private void readResumeBlock(DataInputStream dis, Resume resume, BiConsumerWithException<DataInputStream, Resume> consumer) throws IOException {
        int size = readInt(dis);
        for (int i = 0; i < size; i++) {
            consumer.accept(dis, resume);
        }
    }

    private Experience readExperience(DataInputStream dis) throws IOException {
        String name = readString(dis);
        String homepage = readNullString(dis);
        Link l = new Link(name, homepage);
        List<Experience.Position> lp = readList(dis, this::readPosition);
        return new Experience(lp, l);
    }

    private Experience.Position readPosition(DataInputStream dis) throws IOException {
        String position = readString(dis);
        String description = readNullString(dis);
        String startDate = readString(dis);
        String endDate = readString(dis);
        return new Experience.Position(YearMonth.parse(startDate, MONTH_YEAR_FORMATTER), YearMonth.parse(endDate, MONTH_YEAR_FORMATTER), position, description);
    }

    public interface FunctionWithException<T, R> {
        R apply(T t) throws IOException;
    }

    private <T> List<T> readList(DataInputStream dis, FunctionWithException<DataInputStream, T> function) throws IOException {
        int listSize = readInt(dis);
        List<T> ls = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            ls.add(function.apply(dis));
        }
        return ls;
    }

    private String readString(final DataInputStream dis) throws IOException {
        return dis.readUTF();
    }

    private String readNullString(DataInputStream dis) throws IOException {
        String s = readString(dis);
        return NULL.equals(s) ? null : s;
    }

    private int readInt(final DataInputStream dis) throws IOException {
        return dis.readInt();
    }
}
