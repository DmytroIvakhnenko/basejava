package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MapStorageUuidKey extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected boolean isElementFound(Object elementPointer) {
        return elementPointer != null;
    }

    @Override
    protected String getElementPointer(String uuid) {
        return storage.containsKey(uuid) ? uuid : null;
    }

    @Override
    protected Resume doGet(Object elementPointer) {
        return storage.get(elementPointer);
    }

    @Override
    protected void doSave(Resume resume, Object elementPointer) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void doUpdate(Resume resume, Object elementPointer) {
        storage.replace(resume.getUuid(), resume);
    }

    @Override
    protected void doDelete(Object elementPointer) {
        storage.remove(elementPointer);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected Stream<Resume> doGetAll() {
        return storage.values().stream();
    }

    @Override
    public int size() {
        return storage.size();
    }
}
