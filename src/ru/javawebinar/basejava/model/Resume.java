package ru.javawebinar.basejava.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/** Initial resume class */
public class Resume implements Comparable<Resume> {

  private final String uuid; // Unique identifier
  private final String fullName;

  private final Map<ResumeSectionType, AbstractTextSection> resumeContent;
  private final Map<ContactType, String> contacts;

  public void addContact(ContactType type, String contact) {
    Objects.requireNonNull(type);
    Objects.requireNonNull(contact);
    contacts.put(type, contact);
  }

  public void deleteContact(ContactType type, String contact) {
    Objects.requireNonNull(type);
    Objects.requireNonNull(contact);
    contacts.remove(type, contact);
  }

  public Map<ContactType, String> getAllContacts() {
    return contacts;
  }

  public void addSection(ResumeSectionType type, AbstractTextSection abstractTextSection) {
    Objects.requireNonNull(type);
    Objects.requireNonNull(abstractTextSection);
    resumeContent.put(type, abstractTextSection);
  }

  public void deleteSection(ResumeSectionType type) {
    Objects.requireNonNull(type);
    resumeContent.remove(type);
  }

  public void deleteAllSections() {
    resumeContent.clear();
  }

  public AbstractTextSection getSection(ResumeSectionType type) {
    Objects.requireNonNull(type);
    return resumeContent.get(type);
  }

  public Map<ResumeSectionType, AbstractTextSection> getAllSections() {
    return resumeContent;
  }

  public Resume(String fullName) {
    this(UUID.randomUUID().toString(), fullName);
  }

  public Resume(String uuid, String fullName) {
    Objects.requireNonNull(uuid, "Uuid can't be null");
    Objects.requireNonNull(fullName, "Fullname can't be null");
    this.uuid = uuid;
    this.fullName = fullName;
    resumeContent = new HashMap<>();
    contacts = new HashMap<>();
  }

  public String getUuid() {
    return uuid;
  }

  public String getFullName() {
    return fullName;
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
