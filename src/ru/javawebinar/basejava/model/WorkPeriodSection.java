package ru.javawebinar.basejava.model;

import java.time.YearMonth;
import java.util.Objects;

public class WorkPeriodSection extends SingleTextSection {
    private final YearMonth startDate;
    private final YearMonth endDate;
    private String position;

    public WorkPeriodSection(String sectionTitle, String sectionText, YearMonth startDate, YearMonth endDate, String position) {
        super(sectionTitle, sectionText);
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
    }

    public WorkPeriodSection(
            String sectionText, YearMonth startDate, YearMonth endDate, String position) {
        super(sectionText);
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
    }

    public WorkPeriodSection(String sectionText, YearMonth startDate, YearMonth endDate) {
        super(sectionText);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public YearMonth getStartDate() {
        return startDate;
    }

    public YearMonth getEndDate() {
        return endDate;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        WorkPeriodSection that = (WorkPeriodSection) o;

        if (!Objects.equals(startDate, that.startDate)) return false;
        if (!Objects.equals(endDate, that.endDate)) return false;
        return Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return super.toString() + " " + (position != null ? position : "") + " " + getStartDate() + " " + getEndDate();
    }
}
