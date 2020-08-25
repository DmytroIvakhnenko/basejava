package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.serialization.SerializationStrategy;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final SerializationStrategy serializationStrategy;

    public FileStorage(File directory, SerializationStrategy serializationStrategy) {
        Objects.requireNonNull(directory, "Directory can't be null");
        Objects.requireNonNull(serializationStrategy, "Storage can't be null");
        this.serializationStrategy = serializationStrategy;
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
            return serializationStrategy.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    public List<Resume> doGetAll() {
        File[] list = getFilesList();
        List<Resume> returnList = new LinkedList<>();
        for (File f : list) {
            returnList.add(doGet(f));
        }
        return returnList;
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
            serializationStrategy.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
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
        File[] list = getFilesList();
        for (File f : list) {
            doDelete(f);
        }
    }

    @Override
    public int size() {
        return getFilesList().length;
    }

    private File[] getFilesList() {
        File[] list = directory.listFiles();
        if (list == null) {
            throw new StorageException("Is not a directory no more", directory.getName());
        }
        return list;
    }
}
