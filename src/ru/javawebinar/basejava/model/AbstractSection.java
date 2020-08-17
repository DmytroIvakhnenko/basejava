package ru.javawebinar.basejava.model;

import java.util.Objects;

public abstract class AbstractSection {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractSection that = (AbstractSection) o;

        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }

    public String toString() {
        return (title != null ? title + ": " : "");
    }
}
