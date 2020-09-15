package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.storage.util.YearMonthAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Experience implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Position> positions = new ArrayList<>();
    private Link place;

    public Experience() {
    }

    public Experience(String placeName, String homepage, Position... positions) {
        this(Arrays.asList(positions), new Link(placeName, homepage));
    }

    public Experience(List<Position> positions, Link place) {
        Objects.requireNonNull(positions, "List of positions can't be null");
        Objects.requireNonNull(place, "Link can't be null");
        this.positions = positions;
        this.place = place;
    }

    public Link getPlace() {
        return place;
    }

    public List<Position> getPositions() {
        return positions;
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

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {
        private static final long serialVersionUID = 1L;

        @XmlJavaTypeAdapter(YearMonthAdapter.class)
        private YearMonth startDate;
        @XmlJavaTypeAdapter(YearMonthAdapter.class)
        private YearMonth endDate;
        private String position;
        private String description;

        public Position() {
        }

        public Position(YearMonth startDate, YearMonth endDate, String position, String description) {
            Objects.requireNonNull(startDate);
            Objects.requireNonNull(endDate);
            Objects.requireNonNull(position);
            this.startDate = startDate;
            this.endDate = endDate;
            this.position = position;
            this.description = description;
        }

        public YearMonth getStartDate() {
            return startDate;
        }

        public YearMonth getEndDate() {
            return endDate;
        }

        public String getPosition() {
            return position;
        }

        public String getDescription() {
            return description;
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
}
