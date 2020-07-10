package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int MAX_STORAGE_SIZE = 10_000;
    protected final Resume[] storage = new Resume[MAX_STORAGE_SIZE];
    protected int size = 0;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int position = findIndex(uuid);
        if (position >= 0) {
            return storage[position];
        }
        System.out.println("Resume with id:'" + uuid + "' wasn't found in resume storage!");
        return null;
    }

    public void update(Resume resume) {
        int position = findIndex(resume.getUuid());
        if (position >= 0) {
            storage[position] = resume;
        } else {
            System.out.println("Resume with id:'" + resume.getUuid() + "' wasn't found in resume storage, update was unsuccessful!");
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract int findIndex(String uuid);
}
