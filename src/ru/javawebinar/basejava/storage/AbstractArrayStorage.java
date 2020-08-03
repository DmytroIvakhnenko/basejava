package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    static final int MAX_STORAGE_SIZE = 10_000;
    final Resume[] storage = new Resume[MAX_STORAGE_SIZE];
    int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected AbstractStoragePointer getPointer(String uuid) {
        int result = findIndex(uuid);
        if (result >= 0) {
            return new AbstractStoragePointer(result, null);
        } else {
            return null;
        }
    }

    @Override
    protected Resume getElement(String uuid) {
        return storage[findIndex(uuid)];
    }

    @Override
    protected void saveElement(Resume resume) {
        if (size >= MAX_STORAGE_SIZE) {
            throw new StorageException("Storage overflow, size:" + size + " max size:" + MAX_STORAGE_SIZE, resume.getUuid());
        }
        saveElement(resume, findIndex(resume.getUuid()));
        size++;
    }

    @Override
    protected void updateElement(Resume resume) {
        storage[findIndex(resume.getUuid())] = resume;
    }

    @Override
    protected void deleteElement(String uuid) {
        deleteElement(findIndex(uuid));
        storage[size - 1] = null;
        size--;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public int size() {
        return size;
    }

    protected abstract int findIndex(String uuid);

    protected abstract void saveElement(Resume resume, int position);

    protected abstract void deleteElement(int position);
}
