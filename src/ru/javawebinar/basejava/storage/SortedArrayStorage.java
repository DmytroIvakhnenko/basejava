package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    protected void saveElement(Resume resume, int position) {
        position = -position - 1;               //insertion point transformation got from binarySearch()
        if (storage[position] != null) {        //shift array right by one element if new element is occupied
            System.arraycopy(storage, position, storage, position + 1, size - position);
        }
        storage[position] = resume;
    }

    protected void deleteElement(int position) {
        System.arraycopy(storage, position + 1, storage, position, size - position - 1);    //shift array left by one element
    }
}
