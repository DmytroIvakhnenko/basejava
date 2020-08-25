package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.serialization.SerializationStrategy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final SerializationStrategy serializationStrategy;

    public PathStorage(String dir, SerializationStrategy serializationStrategy) {
        Objects.requireNonNull(dir, "Directory can't be null");
        Objects.requireNonNull(serializationStrategy, "Storage can't be null");
        this.serializationStrategy = serializationStrategy;
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
        return directory.resolve(uuid);
    }

    @Override
    public Resume doGet(Path file) {
        try {
            return serializationStrategy.doRead(new BufferedInputStream(Files.newInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.toString(), e);
        }
    }

    @Override
    public List<Resume> doGetAll() {
        return getFilesList().map(this::doGet).collect(Collectors.toList());
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
            serializationStrategy.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(file)));
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
        getFilesList().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return Math.toIntExact(getFilesList().count());
    }

    private Stream<Path> getFilesList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Files search error", null, e);
        }
    }
}
