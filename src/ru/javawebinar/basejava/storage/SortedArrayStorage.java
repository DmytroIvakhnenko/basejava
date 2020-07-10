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

    @Override
    public void save(Resume r) {
        if (size == MAX_STORAGE_SIZE - 1) {
            System.out.println("Maximum number of saved resumes is reached, saving was unsuccessful!");
            return;
        }
        int position = findIndex(r.getUuid());
        if (position >= 0) {
            System.out.println("Resume with id:'" + r.getUuid() + "' was found in resume storage, saving was unsuccessful!");
        } else {
            position = (position + 1) * (-1);       //insertion point transformation got from binarySearch()
            if (storage[position] != null) {        //shift array right by one element if new element is occupied
                System.arraycopy(storage, position, storage, position + 1, size - position);
            }
        }
        storage[position] = r;
        size++;
    }

    @Override
    public void delete(String uuid) {
        int position = findIndex(uuid);
        if (position >= 0) {
            if (size - position - 1 >= 0) {     //shift array left by one element
                System.arraycopy(storage, position + 1, storage, position, size - position - 1);
                size--;
            }
        } else {
            System.out.println("Resume with id:'" + uuid + "' wasn't found in resume storage, deletion was unsuccessful!\n");
        }
    }

}
