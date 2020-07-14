package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.AbstractArrayStorage;
import ru.javawebinar.basejava.storage.ArrayStorage;
import ru.javawebinar.basejava.storage.SortedArrayStorage;

/**
 * Test for your com.basejava.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final AbstractArrayStorage ARRAY_STORAGE = new ArrayStorage();
    static final AbstractArrayStorage SORTED_ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        //Test(ARRAY_STORAGE);
        //long startTime = System.nanoTime();
        System.out.println("---Sorted array run---");
        Test(SORTED_ARRAY_STORAGE);
        //long endTime = System.nanoTime();
        //System.out.println("Exec time for sorted array storage: " + (endTime - startTime) / 1_000 + " micro seconds");
        //startTime = System.nanoTime();
        System.out.println("---Unsorted array run---");
        Test(ARRAY_STORAGE);
        //endTime = System.nanoTime();
        //System.out.println("Exec time for unsorted array storage: " + (endTime - startTime) / 1_000 + " micro seconds");
    }

    public static void Test(AbstractArrayStorage storage) {
        Resume r1 = new Resume();
        r1.setUuid("uuid1");
        Resume r2 = new Resume();
        r2.setUuid("uuid2");
        Resume r3 = new Resume();
        r3.setUuid("uuid3");
        Resume r6 = new Resume();
        r6.setUuid("uuid6");
        Resume r7 = new Resume();
        r7.setUuid("uuid7");

        storage.save(r6);
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
        storage.save(r7);

        System.out.println("Get r1: " + storage.get(r1.getUuid()));
        System.out.println("Size: " + storage.size());

        System.out.println("Get dummy: " + storage.get("dummy"));

        printAll(storage);
        storage.delete(r1.getUuid());
        printAll(storage);
        storage.delete(r7.getUuid());
        printAll(storage);

        Resume r4 = new Resume();
        r4.setUuid("uuid4");
        storage.update(r4);
        r4.setUuid("uuid2");
        storage.update(r4);

        printAll(storage);
        storage.clear();
        printAll(storage);

        System.out.println("Size: " + storage.size());
    }

    static void printAll(AbstractArrayStorage storage) {
        System.out.println("\nGet All");
        for (Resume r : storage.getAll()) {
            System.out.println(r);
        }
    }
}
