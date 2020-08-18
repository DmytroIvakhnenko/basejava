package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NonExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractStorageTest {
    protected final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_1 = new Resume(UUID_1, "Testman1");
    private static final Resume RESUME_2 = new Resume(UUID_2, "Testman2");
    private static final Resume RESUME_3 = new Resume(UUID_3, "Testman3");
    private static final Resume RESUME_4 = new Resume(UUID_4, "Testman4");

    private static final List<Resume> FULL_LIST_OF_RESUMES = Arrays.asList(RESUME_1, RESUME_2, RESUME_3, RESUME_4);

    public AbstractStorageTest(Storage storage) {
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
    public void sizeTest() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void getTest() {
        Assert.assertEquals(RESUME_1, storage.get(UUID_1));
        Assert.assertEquals(RESUME_2, storage.get(UUID_2));
        Assert.assertEquals(RESUME_3, storage.get(UUID_3));
    }

    @Test(expected = NonExistStorageException.class)
    public void getNotExistTest() {
        storage.get(UUID_4);
    }

    @Test
    public void saveTest() {
        storage.save(RESUME_4);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(FULL_LIST_OF_RESUMES, storage.getAllSorted());
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExistTest() {
        storage.save(RESUME_1);
    }

    @Test
    public void updateTest() {
        Resume newResume = new Resume(UUID_1, "New Testman");
        storage.update(newResume);
        Assert.assertEquals(newResume, storage.get(UUID_1));
    }

    @Test(expected = NonExistStorageException.class)
    public void updateNotExistTest() {
        storage.update(RESUME_4);
    }

    @Test
    public void deleteTest() {
        storage.delete(UUID_3);
        Assert.assertEquals(2, storage.size());
        Assert.assertEquals(Arrays.asList(RESUME_1, RESUME_2), storage.getAllSorted());
    }

    @Test(expected = NonExistStorageException.class)
    public void deleteNotExistTest() {
        storage.delete(UUID_4);
    }

    @Test
    public void clearTest() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void getAllTest() {
        Assert.assertEquals(Arrays.asList(RESUME_1, RESUME_2, RESUME_3), storage.getAllSorted());
    }
}
