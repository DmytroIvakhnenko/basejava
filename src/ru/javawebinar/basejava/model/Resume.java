package ru.javawebinar.basejava.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    private final String uuid; // Unique identifier
    private final String fullName;

    private final Map<SectionType, AbstractSection> sections;
    private final Map<ContactType, String> contacts;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "Uuid can't be null");
        Objects.requireNonNull(fullName, "Fullname can't be null");
        this.uuid = uuid;
        this.fullName = fullName;
        sections = new EnumMap<>(SectionType.class);
        contacts = new EnumMap<>(ContactType.class);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public AbstractSection getSection(SectionType type) {
        Objects.requireNonNull(type);
        return sections.get(type);
    }

    public void addContact(ContactType type, String contact) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(contact);
        contacts.put(type, contact);
    }

    public Map<ContactType, String> getAllContacts() {
        return contacts;
    }

    public void addSection(SectionType type, AbstractSection abstractSection) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(abstractSection);
        sections.put(type, abstractSection);
    }

    public Map<SectionType, AbstractSection> getAllSections() {
        return sections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        return uuid.equals(resume.uuid) && fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode() + fullName.hashCode();
    }

    @Override
    public String toString() {
        return uuid + " (fullName:" + fullName + ")";
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp == 0 ? uuid.compareTo(o.uuid) : cmp;
    }
}
