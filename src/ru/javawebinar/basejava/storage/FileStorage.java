package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final StreamStorage streamStorage;

    public FileStorage(File directory, StreamStorage streamStorage) {
        Objects.requireNonNull(directory, "Directory can't be null");
        Objects.requireNonNull(streamStorage, "Storage can't be null");
        this.streamStorage = streamStorage;
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public boolean isElementFound(File file) {
        return file.exists();
    }

    @Override
    public File getElementPointer(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    public Resume doGet(File file) {
        try {
            return streamStorage.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    public Stream<Resume> doGetAll() {
        File[] list = directory.listFiles();
        List<Resume> returnList = new LinkedList<>();
        if (list != null) {
            for (File f : list) {
                returnList.add(doGet(f));
            }
        }
        return returnList.stream();
    }

    @Override
    public void doSave(Resume resume, File file) {
        try {
            if (!file.createNewFile()) {
                throw new StorageException("File already exists", file.getName());
            }
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
        doUpdate(resume, file);
    }

    @Override
    public void doUpdate(Resume resume, File file) {
        try {
            streamStorage.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    public void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("File deletion error", file.getName());
        }
    }

    @Override
    public void clear() {
        File[] list = directory.listFiles();
        if (list != null) {
            for (File f : list) {
                doDelete(f);
            }
        }
    }

    @Override
    public int size() {
        File[] list = directory.listFiles();
        if (list == null) {
            throw new StorageException("Is not a directory no more", directory.getName());
        }
        return list.length;
    }
}
