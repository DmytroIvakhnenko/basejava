package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MapStorageResumeKey extends AbstractStorage<Resume> {
  private final Map<String, Resume> storage = new HashMap<>();

  @Override
  protected boolean isElementFound(Resume elementPointer) {
    return elementPointer != null;
  }

  @Override
  protected Resume getElementPointer(String uuid) {
    return storage.get(uuid);
  }

  @Override
  protected Resume doGet(Resume elementPointer) {
    return elementPointer;
  }

  @Override
  protected void doSave(Resume resume, Resume elementPointer) {
    storage.put(resume.getUuid(), resume);
  }

  @Override
  protected void doUpdate(Resume resume, Resume elementPointer) {
    storage.replace(resume.getUuid(), resume);
  }

  @Override
  protected void doDelete(Resume elementPointer) {
    storage.remove(elementPointer.getUuid());
  }

  @Override
  public void clear() {
    storage.clear();
  }

  @Override
  protected Stream<Resume> doGetAll() {
    return storage.values().stream();
  }

  @Override
  public int size() {
    return storage.size();
  }
}
