package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    private List<String> items = new ArrayList<>();

    public ListSection() {

    }

    @SafeVarargs
    public ListSection(String... items) {
        this(Arrays.asList(items));
    }

    public ListSection(List list) {
        Objects.requireNonNull(list, "Items can't be null");
        this.items = list;
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return getItems().equals(that.getItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItems());
    }

    @Override
    public String toString() {
        return items.toString();
    }
}
