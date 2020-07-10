package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume resume) {
        if (size == MAX_STORAGE_SIZE - 1) {
            System.out.println("Maximum number of saved resumes is reached, saving was unsuccessful!");
            return;
        }
        if (findIndex(resume.getUuid()) >= 0) {
            System.out.println("Resume with id:'" + resume.getUuid() + "' was found in resume storage, saving was unsuccessful!");
        } else {
            storage[size] = resume;
            size++;
        }
    }

    public void delete(String uuid) {
        int position = findIndex(uuid);
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

    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
