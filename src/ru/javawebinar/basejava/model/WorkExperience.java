package ru.javawebinar.basejava.model;

import java.time.YearMonth;
import java.util.Objects;

public class WorkExperience {
    private final YearMonth startDate;
    private final YearMonth endDate;
    private final String position;
    private final String description;
    private final WorkPlace place;

    public WorkExperience(YearMonth startDate, YearMonth endDate, String position, String description, String name, String url) {
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(endDate);
        Objects.requireNonNull(position);
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
        this.description = description;
        this.place = new WorkPlace(name, url);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkExperience that = (WorkExperience) o;

        if (!startDate.equals(that.startDate)) return false;
        if (!endDate.equals(that.endDate)) return false;
        if (!position.equals(that.position)) return false;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        int result = startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + position.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return place.toString() + " " + position + " " + startDate + " " + endDate + " " + (description != null ? description + ": " : "");
    }
}
