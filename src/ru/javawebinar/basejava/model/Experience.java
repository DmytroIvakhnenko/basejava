package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class Experience {
    private final List<Position> positions;
    private final Link place;

    public Experience(List<Position> positions, Link place) {
        Objects.requireNonNull(positions, "List of positions can't be null");
        Objects.requireNonNull(place, "Link can't be null");
        this.positions = positions;
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Experience that = (Experience) o;

        if (!positions.equals(that.positions)) return false;
        return place.equals(that.place);
    }

    @Override
    public int hashCode() {
        int result = positions.hashCode();
        result = 31 * result + place.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return positions.toString() + place.toString();
    }
}
