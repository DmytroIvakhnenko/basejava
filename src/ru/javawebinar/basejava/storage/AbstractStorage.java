package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NonExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume r) {
        if (getPointer(r.getUuid()) != null) {
            updateElement(r);
        } else {
            throw new NonExistStorageException(r.getUuid());
        }
    }

    @Override
    public void save(Resume r) {
        if (getPointer(r.getUuid()) != null) {
            throw new ExistStorageException(r.getUuid());
        } else {
            saveElement(r);
        }
    }

    @Override
    public Resume get(String uuid) {
        if (getPointer(uuid) != null) {
            return getElement(uuid);
        }
        throw new NonExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        if (getPointer(uuid) != null) {
            deleteElement(uuid);
        } else {
            throw new NonExistStorageException(uuid);
        }
    }

    protected abstract AbstractStoragePointer getPointer(String uuid);

    protected abstract Resume getElement(String uuid);

    protected abstract void saveElement(Resume resume);

    protected abstract void updateElement(Resume resume);

    protected abstract void deleteElement(String uuid);
}

class AbstractStoragePointer {
    final int index;
    final Resume resume;

    public AbstractStoragePointer(int index, Resume resume) {
        this.index = index;
        this.resume = resume;
    }

    public int getIntIndex() {
        return index;
    }

    public Resume getResumeIndex() {
        return resume;
    }
}