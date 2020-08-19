package ru.javawebinar.basejava.model;

import java.time.YearMonth;
import java.util.Objects;

public class Position {
    private final YearMonth startDate;
    private final YearMonth endDate;
    private final String position;
    private final String description;

    public Position(YearMonth startDate, YearMonth endDate, String position, String description) {
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(endDate);
        Objects.requireNonNull(position);
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position that = (Position) o;

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
        return position + " " + startDate + " " + endDate + " " + (description != null ? description + ": " : "");
    }
}
