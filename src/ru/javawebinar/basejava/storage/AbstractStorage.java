package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NonExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {
    int size = 0;

    @Override
    public void clear() {
        this.deleteAllElements();
        size = 0;
    }

    @Override
    public void update(Resume r) {
        if (this.contains(r.getUuid())) {
            this.updateElement(r);
        } else {
            throw new NonExistStorageException(r.getUuid());
        }
    }

    @Override
    public void save(Resume r) {
        if (this.contains(r.getUuid())) {
            throw new ExistStorageException(r.getUuid());
        } else {
            this.saveElement(r);
            size++;
        }
    }

    @Override
    public Resume get(String uuid) {
        if (this.contains(uuid)) {
            return this.getElement(uuid);
        }
        throw new NonExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        if (this.contains(uuid)) {
            this.deleteElement(uuid);
            size--;
        } else {
            throw new NonExistStorageException(uuid);
        }
    }

    @Override
    public Resume[] getAll() {
        return this.getAllElements();
    }

    @Override
    public int size() {
        return size;
    }

    protected abstract void deleteAllElements();

    protected abstract Resume[] getAllElements();

    protected abstract boolean contains(String uuid);

    protected abstract Resume getElement(String uuid);

    protected abstract void saveElement(Resume resume);

    protected abstract void updateElement(Resume resume);

    protected abstract void deleteElement(String uuid);
}
