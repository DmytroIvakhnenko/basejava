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

    @Override
    protected void deleteAllElements() {
        Arrays.fill(storage, 0, size, null);
    }

    @Override
    protected Resume[] getAllElements() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    protected boolean contains(String uuid) {
        return this.findIndex(uuid) >= 0;
    }

    @Override
    protected Resume getElement(String uuid) {
        return storage[this.findIndex(uuid)];
    }

    @Override
    protected void saveElement(Resume resume) {
        if (size >= MAX_STORAGE_SIZE) {
            throw new StorageException("Storage overflow, size:" + size + " max size:" + MAX_STORAGE_SIZE, resume.getUuid());
        }
        this.saveElement(resume, this.findIndex(resume.getUuid()));
    }

    @Override
    protected void updateElement(Resume resume) {
        storage[this.findIndex(resume.getUuid())] = resume;
    }

    @Override
    protected void deleteElement(String uuid) {
        this.deleteElement(this.findIndex(uuid));
        storage[size - 1] = null;
    }

    protected abstract int findIndex(String uuid);

    protected abstract void saveElement(Resume resume, int position);

    protected abstract void deleteElement(int position);
}
