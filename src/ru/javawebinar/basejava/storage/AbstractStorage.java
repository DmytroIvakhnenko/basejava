package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NonExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        SK elementPointer = getNonExistElementPointer(r.getUuid());
        doSave(r, elementPointer);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK elementPointer = getExistElementPointer(uuid);
        return doGet(elementPointer);
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        SK elementPointer = getExistElementPointer(r.getUuid());
        doUpdate(r, elementPointer);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK elementPointer = getExistElementPointer(uuid);
        doDelete(elementPointer);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted");
        return doGetAll().stream().sorted().collect(Collectors.toList());
    }

    protected abstract boolean isElementFound(SK elementPointer);

    protected abstract SK getElementPointer(String uuid);

    protected abstract Resume doGet(SK elementPointer);

    protected abstract List<Resume> doGetAll();

    protected abstract void doSave(Resume resume, SK elementPointer);

    protected abstract void doUpdate(Resume resume, SK elementPointer);

    protected abstract void doDelete(SK elementPointer);

    private SK getExistElementPointer(String uuid) {
        SK elementPointer = getElementPointer(uuid);
        if (!isElementFound(elementPointer)) {
            LOG.warning("Resume " + uuid + " not exists");
            throw new NonExistStorageException(uuid);
        }
        return elementPointer;
    }

    private SK getNonExistElementPointer(String uuid) {
        SK elementPointer = getElementPointer(uuid);
        if (isElementFound(elementPointer)) {
            LOG.warning("Resume " + uuid + " already exists");
            throw new ExistStorageException(uuid);
        }
        return elementPointer;
    }
}
