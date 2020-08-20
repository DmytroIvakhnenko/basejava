package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListOfSections<T> extends AbstractSection {
    private final List<T> list;

    @SafeVarargs
    public ListOfSections(String title, T... items) {
        this(title, Arrays.asList(items));
    }

    public ListOfSections(String title, List<T> list) {
        Objects.requireNonNull(list, "List can't be null");
        this.list = list;
        super.setTitle(title);
    }

    public ListOfSections() {
        list = new ArrayList<>();
    }

    public ListOfSections(String title) {
        this();
        super.setTitle(title);
    }

    public void addItem(T item) {
        Objects.requireNonNull(item);
        list.add(item);
    }

    public void deleteItem(T item) {
        Objects.requireNonNull(item);
        list.remove(item);
    }

    public T getItem(int index) {
        return list.get(index);
    }

    public List<T> getAllItems() {
        return new ArrayList<>(list);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ListOfSections<?> that = (ListOfSections<?>) o;

        return Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public String toString() {
        return super.toString() + " " + list.toString();
    }
}
