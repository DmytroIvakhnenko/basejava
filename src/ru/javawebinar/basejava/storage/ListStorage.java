package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    List<Resume> storage = new ArrayList<>();

    @Override
    protected boolean contains(Object elementPointer) {
        return (Integer) elementPointer >= 0;
    }

    @Override
    protected Integer getElementPointer(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    protected Resume doGet(Object elementPointer) {
        return storage.get((Integer) elementPointer);
    }

    @Override
    protected void doSave(Resume resume, Object elementPointer) {
        storage.add(resume);
    }

    @Override
    protected void doUpdate(Resume resume, Object elementPointer) {
        int index = (Integer) elementPointer;
        storage.remove(index);
        storage.add(index, resume);
    }

    @Override
    protected void doDelete(Object elementPointer) {
        int index = (Integer) elementPointer;
        storage.remove(index);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}

