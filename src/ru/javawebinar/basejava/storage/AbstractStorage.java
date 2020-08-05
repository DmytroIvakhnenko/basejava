package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NonExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume r) {
        Object elementPointer = getExistElementPointer(r.getUuid());
        doUpdate(r, elementPointer);
    }

    @Override
    public void save(Resume r) {
        Object elementPointer = getNonExistElementPointer(r.getUuid());
        doSave(r, elementPointer);
    }

    @Override
    public Resume get(String uuid) {
        Object elementPointer = getExistElementPointer(uuid);
        return doGet(elementPointer);
    }

    @Override
    public void delete(String uuid) {
        Object elementPointer = getExistElementPointer(uuid);
        doDelete(elementPointer);
    }

    private Object getExistElementPointer(String uuid) {
        Object elementPointer = getElementPointer(uuid);
        if (!contains(elementPointer)) {
            throw new NonExistStorageException(uuid);
        }
        return elementPointer;
    }

    private Object getNonExistElementPointer(String uuid) {
        Object elementPointer = getElementPointer(uuid);
        if (contains(elementPointer)) {
            throw new ExistStorageException(uuid);
        }
        return elementPointer;
    }

    protected abstract boolean contains(Object elementPointer);

    protected abstract Object getElementPointer(String uuid);

    protected abstract Resume doGet(Object elementPointer);

    protected abstract void doSave(Resume resume, Object elementPointer);

    protected abstract void doUpdate(Resume resume, Object elementPointer);

    protected abstract void doDelete(Object elementPointer);
}