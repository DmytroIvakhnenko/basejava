package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NonExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractStorage<SK> implements Storage {

    @Override
    public void save(Resume r) {
        SK elementPointer = getNonExistElementPointer(r.getUuid());
        doSave(r, elementPointer);
    }

    @Override
    public Resume get(String uuid) {
        SK elementPointer = getExistElementPointer(uuid);
        return doGet(elementPointer);
    }

    @Override
    public void update(Resume r) {
        SK elementPointer = getExistElementPointer(r.getUuid());
        doUpdate(r, elementPointer);
    }

    @Override
    public void delete(String uuid) {
        SK elementPointer = getExistElementPointer(uuid);
        doDelete(elementPointer);
    }

    @Override
    public List<Resume> getAllSorted() {
        return doGetAll().sorted().collect(Collectors.toList());
    }

    protected abstract boolean isElementFound(SK elementPointer);

    protected abstract SK getElementPointer(String uuid);

    protected abstract Resume doGet(SK elementPointer);

    protected abstract Stream<Resume> doGetAll();

    protected abstract void doSave(Resume resume, SK elementPointer);

    protected abstract void doUpdate(Resume resume, SK elementPointer);

    protected abstract void doDelete(SK elementPointer);

    private SK getExistElementPointer(String uuid) {
        SK elementPointer = getElementPointer(uuid);
        if (!isElementFound(elementPointer)) {
            throw new NonExistStorageException(uuid);
        }
        return elementPointer;
    }

    private SK getNonExistElementPointer(String uuid) {
        SK elementPointer = getElementPointer(uuid);
        if (isElementFound(elementPointer)) {
            throw new ExistStorageException(uuid);
        }
        return elementPointer;
    }
}