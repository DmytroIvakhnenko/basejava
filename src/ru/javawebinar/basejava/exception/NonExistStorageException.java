package ru.javawebinar.basejava.exception;

public class NonExistStorageException extends StorageException {
    public NonExistStorageException(String uuid) {
        super("Resume with id: '" + uuid + "' wasn't found in resume storage", uuid);
    }
}
