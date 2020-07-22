package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NonExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

import static ru.javawebinar.basejava.storage.AbstractArrayStorage.MAX_STORAGE_SIZE;

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_4);
    private static final Resume RESUME_4 = new Resume(UUID_4);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void testSize() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void testGet() {
        Assert.assertEquals(RESUME_1, storage.get(UUID_1));
        Assert.assertEquals(RESUME_2, storage.get(UUID_2));
        Assert.assertEquals(RESUME_3, storage.get(UUID_3));
    }

    @Test(expected = NonExistStorageException.class)
    public void testGetNotExist() {
        storage.get(UUID_4);
    }

    @Test
    public void testSave() {
        storage.save(RESUME_4);
        Assert.assertEquals(4, storage.size());
        Assert.assertArrayEquals(new Resume[]{RESUME_1, RESUME_2, RESUME_3, RESUME_4}, storage.getAll());
    }

    @Test(expected = ExistStorageException.class)
    public void testSaveAlreadyExist() {
        storage.save(RESUME_1);
    }

    @Test(expected = StorageException.class)
    public void testSaveOverflow() {
        try {
            for (int i = 0; i < MAX_STORAGE_SIZE - 3; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail(e.getMessage());
        }
        storage.save(new Resume());
    }

    @Test
    public void testUpdate() {
        storage.update(RESUME_1);
        Assert.assertEquals(RESUME_1, storage.get(UUID_1));
    }

    @Test(expected = NonExistStorageException.class)
    public void testUpdateNotExist() {
        storage.update(RESUME_4);
    }

    @Test
    public void testDelete() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
        Assert.assertArrayEquals(new Resume[]{RESUME_2, RESUME_3}, storage.getAll());
    }

    @Test(expected = NonExistStorageException.class)
    public void testDeleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test
    public void testClear() {
        storage.clear();
        Assert.assertArrayEquals(new Resume[]{}, storage.getAll());
    }

    @Test
    public void testGetAll() {
        Assert.assertArrayEquals(new Resume[]{RESUME_1, RESUME_2, RESUME_3}, storage.getAll());
    }
}