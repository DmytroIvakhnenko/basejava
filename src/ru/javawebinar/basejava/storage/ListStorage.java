package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    List<Resume> storage = new ArrayList<>();

    @Override
    protected AbstractStoragePointer getPointer(String uuid) {
        int result = storage.indexOf(new Resume(uuid));
        if (result >= 0) {
            return new AbstractStoragePointer(result, null);
        } else {
            return null;
        }
    }

    @Override
    protected Resume getElement(String uuid) {
        AbstractStoragePointer p = getPointer(uuid);
        return storage.get(p.getIntIndex());
    }

    @Override
    protected void saveElement(Resume resume) {
        storage.add(resume);
    }

    @Override
    protected void updateElement(Resume resume) {
        AbstractStoragePointer p = getPointer(resume.getUuid());
        storage.remove(p.getIntIndex());
        storage.add(p.getIntIndex(), resume);
    }

    @Override
    protected void deleteElement(String uuid) {
        AbstractStoragePointer p = getPointer(uuid);
        storage.remove(p.getIntIndex());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}

