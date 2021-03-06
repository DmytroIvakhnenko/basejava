package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NonExistStorageException;
import ru.javawebinar.basejava.model.*;

import java.io.File;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static ru.javawebinar.basejava.ResumeTestData.setFullResumeTestData;
import static ru.javawebinar.basejava.ResumeTestData.setResumeTestDataNoSections;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.getInstance().getStorageDir();

    final Storage storage;

    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final String UUID_2 = UUID.randomUUID().toString();
    private static final String UUID_3 = UUID.randomUUID().toString();
    private static final String UUID_4 = UUID.randomUUID().toString();

    private static final Resume RESUME_1 = new Resume(UUID_1, "Testman1");//setResumeTestDataWithNoSections(UUID_1, "Testman1");
    private static final Resume RESUME_2 = setFullResumeTestData(UUID_2, "Testman2");
    private static final Resume RESUME_3 = setFullResumeTestData(UUID_3, "Testman3");
    private static final Resume RESUME_4 = setFullResumeTestData(UUID_4, "Testman4");

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
        final List<Resume> fullListOfResumes = Arrays.asList(RESUME_1, RESUME_2, RESUME_3, RESUME_4);
        storage.save(RESUME_4);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(fullListOfResumes, storage.getAllSorted());
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExistTest() {
        storage.save(RESUME_1);
    }

    @Test
    public void updateTest() {
        Resume newResume = setResumeTestDataNoSections(UUID_1, "New Testman");
        newResume.setContact(ContactType.STACKOVERFLOW, "some_so");
        newResume.setSection(SectionType.OBJECTIVE, new TextSection("dummy"));
        newResume.setSection(SectionType.ACHIEVEMENT, new ListSection("dummy1", "dummy2", "dummy3"));
        YearMonth date1 = YearMonth.parse("01/2001", DateTimeFormatter.ofPattern("MM/yyyy"));
        YearMonth date2 = YearMonth.parse("02/2002", DateTimeFormatter.ofPattern("MM/yyyy"));
        Experience.Position pos = new Experience.Position(date1, date2, "testPosition", "testDesc");
        newResume.setSection(SectionType.EXPERIENCE, new ExperienceSection(new Experience("testPlace", "https://www.testsite.org", pos)));
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
