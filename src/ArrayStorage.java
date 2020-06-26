import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size = 0;

    void clear() {
        for (int i = 0; i < size(); i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume resume) {
        storage[size()] = resume;
        size++;
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
                for (int j = i; j < size(); j++) {     //shift array by one element
                    storage[j] = storage[j + 1];
                }
                size--;
                break; //full search skip because uuids are unique (see req)
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size());
    }

    int size() {
        return size;
    }
}
