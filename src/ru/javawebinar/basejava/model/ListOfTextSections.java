package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListOfTextSections extends AbstractTextSection {
    private final List<AbstractTextSection> list;

    public ListOfTextSections() {
        list = new ArrayList<>();
    }

    public ListOfTextSections(String title) {
        this();
        super.setTitle(title);
    }

    public void addItem(AbstractTextSection singleText) {
        Objects.requireNonNull(singleText);
        list.add(singleText);
    }

    public void deleteItem(AbstractTextSection singleText) {
        Objects.requireNonNull(singleText);
        list.remove(singleText);
    }

    public void deleteAllItems() {
        list.clear();
    }

    public AbstractTextSection getItem(int index) {
        return list.get(index);
    }

    public List<AbstractTextSection> getAllItems() {
        return new ArrayList<>(list);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListOfTextSections that = (ListOfTextSections) o;

        return list.equals(that.list);
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
