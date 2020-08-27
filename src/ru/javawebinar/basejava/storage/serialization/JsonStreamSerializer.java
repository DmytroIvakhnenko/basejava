package ru.javawebinar.basejava.storage.serialization;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.util.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonStreamSerializer implements SerializationStrategy {
    @Override
    public Resume doRead(InputStream in) throws IOException {
        try (Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            return JsonParser.read(reader, Resume.class);
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream out) throws IOException {
        try (Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
            JsonParser.write(resume, writer);
        }
    }
}
