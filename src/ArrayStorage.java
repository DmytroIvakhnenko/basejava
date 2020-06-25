import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = size() - 1; i >= 0; i--) {
            storage[i] = null;
        }
    }

    void save(Resume resume) {
        storage[size()] = resume;
    }

    Resume get(String uuid) {
        Resume resume = null;
        for (int i = 0; i < size(); i++) {
            if (storage[i].uuid.equals(uuid)) {
                resume = storage[i];
                break; //full search skip because uuids are unique (see req)
            }
        }
        return resume;
    }

    void delete(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (storage[i].uuid.equals(uuid)) {
                for (int j = i; j < size() ; j++) {     //shift array by one element
                    storage[j] = storage[j + 1];
                }
                break; //full search skip because uuids are unique (see req)
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[size()];
        resumes = Arrays.copyOfRange(storage, 0, size());
        return resumes;
    }

    int size() {
        Integer lastElementIndex = null;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                break; //full search skip according to the way how data is stored in storage (see req)
            } else {
                lastElementIndex = i;
            }
        }
        return (lastElementIndex != null) ? 1 + lastElementIndex : 0; //size should be zero if no elements were found
    }
}
