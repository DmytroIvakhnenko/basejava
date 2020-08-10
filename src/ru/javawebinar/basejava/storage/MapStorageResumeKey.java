package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class MapStorageResumeKey extends AbstractStorage {
    private final Map<String, Resume> storage = new TreeMap<>();

    @Override
    protected boolean isElementFound(Object elementPointer) {
        return elementPointer != null;
    }

    @Override
    protected Resume getElementPointer(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected Resume doGet(Object elementPointer) {
        return (Resume) elementPointer;
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
        storage.remove(((Resume) elementPointer).getUuid());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        return storage.values().stream().sorted().collect(Collectors.toList());
    }

    @Override
    public int size() {
        return storage.size();
    }
}
