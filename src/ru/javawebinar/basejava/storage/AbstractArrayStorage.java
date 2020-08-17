package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    static final int MAX_STORAGE_SIZE = 10_000;
    final Resume[] storage = new Resume[MAX_STORAGE_SIZE];
    int size = 0;

    @Override
    protected boolean isElementFound(Integer index) {
        return index >= 0;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected Resume doGet(Integer index) {
        return storage[index];
    }

    @Override
    protected void doSave(Resume resume, Integer index) {
        if (size >= MAX_STORAGE_SIZE) {
            throw new StorageException(
                    "Storage overflow, size:" + size + " max size:" + MAX_STORAGE_SIZE, resume.getUuid());
        }
        saveElement(resume, index);
        size++;
    }

    @Override
    protected void doUpdate(Resume resume, Integer index) {
        storage[index] = resume;
    }

    @Override
    protected void doDelete(Integer index) {
        deleteElement(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected Stream<Resume> doGetAll() {
        return Arrays.stream(storage).filter(Objects::nonNull);
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
