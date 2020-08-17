package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static ru.javawebinar.basejava.storage.AbstractArrayStorage.MAX_STORAGE_SIZE;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflowTest() {
        try {
            int size = storage.size();
            for (int i = 0; i < MAX_STORAGE_SIZE - size; i++) {
                storage.save(new Resume("Testman"));
            }
        } catch (StorageException e) {
            Assert.fail(e.getMessage());
        }
        storage.save(new Resume("Last Testman"));
    }
}
