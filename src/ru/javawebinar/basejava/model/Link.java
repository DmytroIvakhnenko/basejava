package ru.javawebinar.basejava.model;

import java.util.Objects;

public class Link {
    private final String name;
    private final String homepage;

    public Link(String name, String homepage) {
        Objects.requireNonNull(name);
        this.name = name;
        this.homepage = homepage;
    }

    public String getName() {
        return name;
    }

    public String getHomepage() {
        return homepage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if (!name.equals(link.name)) return false;
        return Objects.equals(homepage, link.homepage);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (homepage != null ? homepage.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name + (homepage != null ? homepage : "");
    }
}
