package ru.javawebinar.basejava.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("Resume with id: '" + uuid + "' was already found in resume storage", uuid);
    }

    public ExistStorageException() {
        super("Resume was already found in resume storage");
    }
}
