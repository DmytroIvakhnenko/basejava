package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    protected boolean isElementFound(Object index) {
        return (Integer) index >= 0;
    }

    @Override
    protected Integer getElementPointer(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected Resume doGet(Object index) {
        return storage.get((Integer) index);
    }

    @Override
    protected void doSave(Resume resume, Object index) {
        storage.add(resume);
    }

    @Override
    protected void doUpdate(Resume resume, Object index) {
        storage.set((Integer) index, resume);
    }

    @Override
    protected void doDelete(Object index) {
        storage.remove(((Integer) index).intValue());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected Stream<Resume> doGetAll() {
        return storage.stream();
    }

    @Override
    public int size() {
        return storage.size();
    }
}

