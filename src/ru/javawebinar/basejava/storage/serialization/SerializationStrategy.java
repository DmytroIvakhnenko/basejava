package ru.javawebinar.basejava.storage.serialization;

import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializationStrategy {

    Resume doRead(InputStream in) throws IOException;

    void doWrite(Resume resume, OutputStream out) throws IOException;
}
