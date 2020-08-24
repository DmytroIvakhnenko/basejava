package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final StreamStorage streamStorage;

    public PathStorage(String dir, StreamStorage streamStorage) {
        Objects.requireNonNull(dir, "Directory can't be null");
        Objects.requireNonNull(streamStorage, "Storage can't be null");
        this.streamStorage = streamStorage;
        directory = Paths.get(dir);
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory ot is not writable");
        }
    }

    @Override
    public boolean isElementFound(Path file) {
        return Files.exists(file);
    }

    @Override
    public Path getElementPointer(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    public Resume doGet(Path file) {
        try {
            return streamStorage.doRead(new BufferedInputStream(new FileInputStream(file.toString())));
        } catch (IOException e) {
            throw new StorageException("IO error", file.toString(), e);
        }
    }

    @Override
    public Stream<Resume> doGetAll() {
        try {
            return Files.walk(directory).filter(Files::isRegularFile).map(this::doGet);
        } catch (IOException e) {
            throw new StorageException("IO error", null, e);
        }
    }

    @Override
    public void doSave(Resume resume, Path file) {
        try {
            Files.createFile(file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.toString(), e);
        }
        doUpdate(resume, file);
    }

    @Override
    public void doUpdate(Resume resume, Path file) {
        try {
            streamStorage.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file.toString())));
        } catch (IOException e) {
            throw new StorageException("IO error", file.toString(), e);
        }
    }

    @Override
    public void doDelete(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new StorageException("File deletion error", null);
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path deletion error", null);
        }
    }

    @Override
    public int size() {
        try {
            return Math.toIntExact(Files.list(directory).count());
        } catch (IOException e) {
            throw new StorageException("Files search error", null, e);
        }
    }
}
