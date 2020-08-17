package ru.javawebinar.basejava.storage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.javawebinar.basejava.storage.AbstractArrayStorage.MAX_STORAGE_SIZE;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Before
    public void RaiseLogLevel() {
        LOG.setLevel(Level.WARNING);
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

    @After
    public void LowerLogLevel() {
        LOG.setLevel(Level.INFO);
    }
}
