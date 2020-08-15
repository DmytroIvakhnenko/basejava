package ru.javawebinar.basejava.model;

import java.util.Objects;

public class TextSection {
    private String sectionTitle;

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection that = (TextSection) o;

        return Objects.equals(sectionTitle, that.sectionTitle);
    }

    @Override
    public int hashCode() {
        return sectionTitle != null ? sectionTitle.hashCode() : 0;
    }

    public String toString() {
        return (sectionTitle != null ? sectionTitle + ": " : "");
    }
}
