package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

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

    protected abstract int findIndex(String uuid);
}
