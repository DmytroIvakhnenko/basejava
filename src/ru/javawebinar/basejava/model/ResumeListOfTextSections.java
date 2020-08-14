package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResumeListOfTextSections extends AbstractResumeTextSection {
    private final List<AbstractResumeTextSection> list;

    public ResumeListOfTextSections() {
        list = new ArrayList<>();
    }

    public ResumeListOfTextSections(String title) {
        this();
        super.setSectionTitle(title);
    }

    public void saveItem(AbstractResumeTextSection singleText) {
        Objects.requireNonNull(singleText);
        list.add(singleText);
    }

    public void deleteItem(AbstractResumeTextSection singleText) {
        Objects.requireNonNull(singleText);
        list.remove(singleText);
    }

    public void deleteAllItems() {
        list.clear();
    }

    public AbstractResumeTextSection getItem(int index) {
        return list.get(index);
    }

    public List<AbstractResumeTextSection> getAllItems() {
        return new ArrayList<>(list);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResumeListOfTextSections that = (ResumeListOfTextSections) o;

        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public String toString() {
        String title = super.getSectionTitle();
        return (title != null ? title + " " : "") + list.toString();
    }
}
