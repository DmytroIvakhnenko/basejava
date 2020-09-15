package ru.javawebinar.basejava.storage.serialization;

import ru.javawebinar.basejava.exception.ResumeDeserializationException;
import ru.javawebinar.basejava.exception.ResumeSerializationException;
import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private void writeContacts(DataOutputStream d, Map.Entry<ContactType, String> e) {
        writeString(d, e.getKey().name());
        writeString(d, e.getValue());
    }

    private void writeSections(final DataOutputStream dos, final Map.Entry<SectionType, AbstractSection> e) {
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

    private <T> void writeList(final DataOutputStream dos, final Collection<T> l, BiConsumer<DataOutputStream, T> sup) {
        writeInt(dos, l.size());
        l.forEach((e) -> sup.accept(dos, e));
    }

    private void writeExperience(final DataOutputStream dos, final Experience e) {
        writeString(dos, e.getPlace().getName());
        writeString(dos, Optional.ofNullable(e.getPlace().getHomepage()).orElse(NULL));
        writeList(dos, e.getPositions(), this::writePosition);
    }

    private void writePosition(DataOutputStream dos, final Experience.Position p) {
        writeString(dos, p.getPosition());
        writeString(dos, Optional.ofNullable(p.getDescription()).orElse(NULL));
        writeString(dos, p.getStartDate().format(MONTH_YEAR_FORMATTER));
        writeString(dos, p.getEndDate().format(MONTH_YEAR_FORMATTER));
    }

    private void writeString(final DataOutputStream dos, final String s) {
        try {
            dos.writeUTF(s);
        } catch (IOException e) {
            throw new ResumeSerializationException("Error writing string: " + s, e);
        }
    }

    private void writeInt(final DataOutputStream dos, final int i) {
        try {
            dos.writeInt(i);
        } catch (IOException e) {
            throw new ResumeSerializationException("Error writing int: " + i, e);
        }
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

    private void readSections(DataInputStream dis, Resume resume) {
        SectionType st = SectionType.valueOf(readString(dis));
        switch (st) {
            case PERSONAL:
            case OBJECTIVE:
                resume.addSection(st, new TextSection(readString(dis)));
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                resume.addSection(st, new ListSection(readList(dis, this::readString)));
                break;
            case EXPERIENCE:
            case EDUCATION:
                resume.addSection(st, new ExperienceSection(readList(dis, this::readExperience)));
                break;
        }
    }

    private void readContacts(DataInputStream dis, Resume resume) {
        resume.addContact(ContactType.valueOf(readString(dis)), readString(dis));
    }

    private void readResumeBlock(DataInputStream dis, Resume resume, BiConsumer<DataInputStream, Resume> consumer) {
        int size = readInt(dis);
        for (int i = 0; i < size; i++) {
            consumer.accept(dis, resume);
        }
    }

    private Experience readExperience(DataInputStream dis) {
        String name = readString(dis);
        String homepage = readNullString(dis);
        Link l = new Link(name, homepage);
        List<Experience.Position> lp = readList(dis, this::readPosition);
        return new Experience(lp, l);
    }

    private Experience.Position readPosition(DataInputStream dis) {
        String position = readString(dis);
        String description = readNullString(dis);
        String startDate = readString(dis);
        String endDate = readString(dis);
        return new Experience.Position(YearMonth.parse(startDate, MONTH_YEAR_FORMATTER), YearMonth.parse(endDate, MONTH_YEAR_FORMATTER), position, description);
    }

    private <T> List<T> readList(DataInputStream dis, Function<DataInputStream, T> function) {
        int listSize = readInt(dis);
        return Stream.generate(() -> function.apply(dis)).limit(listSize).collect(Collectors.toList());
    }

    private String readString(final DataInputStream dis) {
        try {
            return dis.readUTF();
        } catch (IOException e) {
            throw new ResumeDeserializationException("Error reading string value", e);
        }
    }

    private String readNullString(DataInputStream dis) {
        String s = readString(dis);
        return NULL.equals(s) ? null : s;
    }

    private int readInt(final DataInputStream dis) {
        try {
            return dis.readInt();
        } catch (IOException e) {
            throw new ResumeDeserializationException("Error reading int value", e);
        }
    }
}
