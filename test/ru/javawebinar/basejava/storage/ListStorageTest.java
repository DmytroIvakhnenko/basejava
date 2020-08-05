package ru.javawebinar.basejava.storage;

import org.junit.Test;

public class ListStorageTest extends AbstractArrayStorageTest {
    public ListStorageTest() {
        super(new ListStorage());
    }

    @Override
    @Test
    public void saveOverflowTest() {

    }
}