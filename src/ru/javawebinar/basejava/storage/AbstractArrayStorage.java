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
    protected boolean contains(Object elementPointer) {
        return (Integer) elementPointer >= 0;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected Resume doGet(Object elementPointer) {
        return storage[(Integer) elementPointer];
    }

    @Override
    protected void doSave(Resume resume, Object elementPointer) {
        if (size >= MAX_STORAGE_SIZE) {
            throw new StorageException("Storage overflow, size:" + size + " max size:" + MAX_STORAGE_SIZE, resume.getUuid());
        }
        saveElement(resume, (Integer) elementPointer);
        size++;
    }

    @Override
    protected void doUpdate(Resume resume, Object elementPointer) {
        storage[(Integer) elementPointer] = resume;
    }

    @Override
    protected void doDelete(Object elementPointer) {
        deleteElement((Integer) elementPointer);
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

    @Override
    protected abstract Integer getElementPointer(String uuid);

    protected abstract void saveElement(Resume resume, int position);

    protected abstract void deleteElement(int position);
}
