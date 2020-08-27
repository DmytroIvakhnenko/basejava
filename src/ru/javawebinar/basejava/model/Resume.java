package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {
    private static final long serialVersionUID = 1L;

    private String uuid; // Unique identifier
    private String fullName;

    private Map<SectionType, AbstractSection> sections;
    private Map<ContactType, String> contacts;

    public Resume() {
    }

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

    public String getContact(ContactType type) {
        Objects.requireNonNull(type);
        return contacts.get(type);
    }

    public Map<ContactType, String> getAllContacts() {
        return contacts;
    }

    public AbstractSection getSection(SectionType type) {
        Objects.requireNonNull(type);
        return sections.get(type);
    }

    public Map<SectionType, AbstractSection> getAllSections() {
        return sections;
    }

    public void addContact(ContactType type, String contact) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(contact);
        contacts.put(type, contact);
    }

    public void addSection(SectionType type, AbstractSection abstractSection) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(abstractSection);
        sections.put(type, abstractSection);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        if (!fullName.equals(resume.fullName)) return false;
        if (!Objects.equals(sections, resume.sections)) return false;
        return Objects.equals(contacts, resume.contacts);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        result = 31 * result + (sections != null ? sections.hashCode() : 0);
        result = 31 * result + (contacts != null ? contacts.hashCode() : 0);
        return result;
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
