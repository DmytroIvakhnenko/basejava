package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NonExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume r) {
        Object elementPointer = getElementPointer(r.getUuid());
        if (contains(elementPointer)) {
            doUpdate(r, elementPointer);
        } else {
            throw new NonExistStorageException(r.getUuid());
        }
    }

    @Override
    public void save(Resume r) {
        Object elementPointer = getElementPointer(r.getUuid());
        if (contains(elementPointer)) {
            throw new ExistStorageException(r.getUuid());
        } else {
            doSave(r, elementPointer);
        }
    }

    @Override
    public Resume get(String uuid) {
        Object elementPointer = getElementPointer(uuid);
        if (contains(elementPointer)) {
            return doGet(elementPointer);
        }
        throw new NonExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        Object elementPointer = getElementPointer(uuid);
        if (contains(elementPointer)) {
            doDelete(elementPointer);
        } else {
            throw new NonExistStorageException(uuid);
        }
    }

    protected abstract boolean contains(Object elementPointer);

    protected abstract Object getElementPointer(String uuid);

    protected abstract Resume doGet(Object elementPointer);

    protected abstract void doSave(Resume resume, Object elementPointer);

    protected abstract void doUpdate(Resume resume, Object elementPointer);

    protected abstract void doDelete(Object elementPointer);
}