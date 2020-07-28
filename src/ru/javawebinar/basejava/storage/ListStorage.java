package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class ListStorage extends AbstractStorage {
    ListEntry head = null;
    ListEntry tail = null;

    @Override
    protected void deleteAllElements() {
        head = null;
        tail = null;
    }

    @Override
    protected Resume[] getAllElements() {
        Resume[] resumes = new Resume[size];
        ListEntry entry = head;
        for (int i = 0; i < size; i++) {
            resumes[i] = entry.getResume();
            entry = entry.next;
        }
        return resumes;
    }

    @Override
    protected boolean contains(String uuid) {
        return this.getElement(uuid) != null;
    }

    @Override
    protected Resume getElement(String uuid) {
        Resume resume = null;
        if (head == null) {
            return null;
        }
        ListEntry entry = head;
        for (int i = 0; i < size; i++) {
            resume = entry.getResume();
            if (resume.getUuid().equals(uuid)) {
                return resume;
            }
            entry = entry.next;
        }
        return null;
    }

    @Override
    protected void saveElement(Resume resume) {
        if (head == null && tail == null) {
            this.addFirst(resume);
        } else {
            ListEntry entry = new ListEntry(resume, null, tail);
            tail.next = entry;
            tail = entry;
        }
    }

    @Override
    protected void updateElement(Resume resume) {
        Resume resumeToUpdate = this.getElement(resume.getUuid());
        resumeToUpdate = resume;
    }

    @Override
    protected void deleteElement(String uuid) {
        ListEntry entry = tail;
        entry.resume = null;
        tail = tail.prev;
        tail.next.prev = null;
        tail.next = null;
    }

    private void addFirst(Resume resume) {
        ListEntry entry = new ListEntry(resume, null, null);
        head = entry;
        tail = entry;
    }
}

class ListEntry {
    Resume resume;
    ListEntry next;
    ListEntry prev;

    public Resume getResume() {
        return resume;
    }

    public ListEntry(Resume resume, ListEntry next, ListEntry prev) {
        this.resume = resume;
        this.next = next;
        this.prev = prev;
    }
}
