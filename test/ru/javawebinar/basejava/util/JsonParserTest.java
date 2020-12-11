package ru.javawebinar.basejava.util;

import org.junit.Assert;
import org.junit.Test;
import ru.javawebinar.basejava.ResumeTestData;
import ru.javawebinar.basejava.model.AbstractSection;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.TextSection;
import ru.javawebinar.basejava.storage.util.JsonParser;

public class JsonParserTest {
    @Test
    public void testResume() {
        Resume inputResume = ResumeTestData.setFullResumeTestData("testUUID", "TestResume");
        String json = JsonParser.write(inputResume);
        System.out.println(json);
        Resume outputResume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(inputResume, outputResume);
    }

    @Test
    public void write() {
        AbstractSection inputSection = new TextSection("Test");
        String json = JsonParser.write(inputSection, AbstractSection.class);
        System.out.println(json);
        AbstractSection outputSection = JsonParser.read(json, AbstractSection.class);
        Assert.assertEquals(inputSection, outputSection);
    }
}
