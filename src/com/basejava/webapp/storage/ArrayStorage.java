package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int MAX_STORAGE_SIZE = 10_000;
    private Resume[] storage = new Resume[MAX_STORAGE_SIZE];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (size == MAX_STORAGE_SIZE - 1) {
            System.out.println("Maximum number of saved resumes is reached, saving was unsuccessful!");
            return;
        }
        int position = findResume(resume.getUuid());
        if (position >= 0) {
            System.out.println("Resume with id:'" + resume.getUuid() + "' was found in resume storage, saving was unsuccessful!");
        } else {
            storage[size] = resume;
            size++;
        }
    }

    public void update(Resume resume) {
        int position = findResume(resume.getUuid());
        if (position >= 0) {
            storage[position] = resume;
        } else {
            System.out.println("Resume with id:'" + resume.getUuid() + "' wasn't found in resume storage, update was unsuccessful!");
        }
    }


    public Resume get(String uuid) {
        int position = findResume(uuid);
        if (position >= 0) {
            return storage[position];
        }
        System.out.println("Resume with id:'" + uuid + "' wasn't found in resume storage!");
        return null;
    }

    public void delete(String uuid) {
        int position = findResume(uuid);
        if (position >= 0) {
            //shift array by one element
            if (size - position - 1 >= 0) {
                System.arraycopy(storage, position + 1, storage, position, size - 1 - position);
                size--;
            }
        } else {
            System.out.println("Resume with id:'" + uuid + "' wasn't found in resume storage, deletion was unsuccessful!\n");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    private int findResume(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
