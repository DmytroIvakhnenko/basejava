package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer getElementPointer(String uuid) {
        Resume searchKey = new Resume(uuid, "");
        return Arrays.binarySearch(storage, 0, size, searchKey, Comparator.comparing(Resume::getUuid));
    }

    @Override
    protected void saveElement(Resume resume, int position) {
        position = -position - 1;               //insertion point transformation got from binarySearch()
        System.arraycopy(storage, position, storage, position + 1, size - position);    //shift array right by one element
        storage[position] = resume;
    }

    @Override
    protected void deleteElement(int position) {
        System.arraycopy(storage, position + 1, storage, position, size - position - 1);    //shift array left by one element
    }
}
