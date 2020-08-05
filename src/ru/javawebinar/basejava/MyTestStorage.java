package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.AbstractStorage;

public class MyTestStorage extends AbstractStorage {
    public static void main(String[] args) {
        MyTestStorage testStorage = new MyTestStorage();
        System.out.println(testStorage.contains(new Resume("uuid1"))); //здесь случилась "амнезия", и я забыл что нужно передать int
    }

    @Override
    protected boolean contains(Object elementPointer) {
        return (Integer) elementPointer >= 0;
    }

    @Override
    protected Object getElementPointer(String uuid) {
        return null;
    }

    @Override
    protected Resume doGet(Object elementPointer) {
        return null;
    }

    @Override
    protected void doSave(Resume resume, Object elementPointer) {

    }

    @Override
    protected void doUpdate(Resume resume, Object elementPointer) {

    }

    @Override
    protected void doDelete(Object elementPointer) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public int size() {
        return 0;
    }
}
