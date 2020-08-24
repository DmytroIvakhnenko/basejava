package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;

public class ObjectStreamStorage implements StreamStorage {

    @Override
    public Resume doRead(InputStream in) throws IOException {
        try (ObjectInputStream oin = new ObjectInputStream(in)) {
            return (Resume) oin.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Resume read error", null, e);
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream out) throws IOException {
        try (ObjectOutputStream oout = new ObjectOutputStream(out)) {
            oout.writeObject(resume);
        }
    }
}
