package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NonExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    static final int MAX_STORAGE_SIZE = 10_000;
    final Resume[] storage = new Resume[MAX_STORAGE_SIZE];
    int size = 0;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int position = findIndex(uuid);
        if (position >= 0) {
            return storage[position];
        }
        throw new NonExistStorageException(uuid);
    }

    public void save(Resume resume) {
        if (size >= MAX_STORAGE_SIZE) {
            throw new StorageException("Storage overflow, size:" + size + " max size:" + MAX_STORAGE_SIZE, resume.getUuid());
        }
        int position = findIndex(resume.getUuid());
        if (position >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            saveElement(resume, position);
            size++;
        }
    }

    public void update(Resume resume) {
        int position = findIndex(resume.getUuid());
        if (position >= 0) {
            storage[position] = resume;
        } else {
            throw new NonExistStorageException(resume.getUuid());
        }
    }

    public void delete(String uuid) {
        int position = findIndex(uuid);
        if (position >= 0) {
            deleteElement(position);
            storage[size - 1] = null;
            size--;
        } else {
            throw new NonExistStorageException(uuid);
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

    protected abstract void saveElement(Resume resume, int position);

    protected abstract void deleteElement(int position);
}
