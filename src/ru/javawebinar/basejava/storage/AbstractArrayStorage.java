package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    static final int MAX_STORAGE_SIZE = 10_000;
    final Resume[] storage = new Resume[MAX_STORAGE_SIZE];
    int size = 0;

    @Override
    protected boolean isElementFound(Object index) {
        return (Integer) index >= 0;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected Resume doGet(Object index) {
        return storage[(Integer) index];
    }

    @Override
    protected void doSave(Resume resume, Object index) {
        if (size >= MAX_STORAGE_SIZE) {
            throw new StorageException("Storage overflow, size:" + size + " max size:" + MAX_STORAGE_SIZE, resume.getUuid());
        }
        saveElement(resume, (Integer) index);
        size++;
    }

    @Override
    protected void doUpdate(Resume resume, Object index) {
        storage[(Integer) index] = resume;
    }

    @Override
    protected void doDelete(Object index) {
        deleteElement((Integer) index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public List<Resume> getAllSorted() {
        return Arrays.stream(storage).filter(Objects::nonNull).sorted().collect(Collectors.toList());
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
