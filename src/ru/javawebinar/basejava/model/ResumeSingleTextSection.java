package ru.javawebinar.basejava.model;

import java.util.*;

public class ResumeSingleTextSection extends AbstractResumeTextSection {
    private final String sectionText;
    private String startDate;
    private String endDate;

    public ResumeSingleTextSection(String sectionTitle, String sectionText) {
        this.sectionText = sectionText;
        super.setSectionTitle(sectionTitle);
    }

    public ResumeSingleTextSection(String sectionText) {
        this.sectionText = sectionText;
    }

    public String getSectionText() {
        return sectionText;
    }

    public void setDates(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Map<String, String> getDates() {
        Map<String, String> map = new HashMap<>();
        map.put(startDate, endDate);
        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResumeSingleTextSection that = (ResumeSingleTextSection) o;

        return sectionText.equals(that.sectionText);
    }

    @Override
    public int hashCode() {
        return sectionText.hashCode();
    }

    @Override
    public String toString() {
        String title = super.getSectionTitle();
        return (title != null ? title + ": " : "") + (startDate != null ? startDate + "-" : "") + (endDate != null ? endDate + " " : "") + getSectionText();
    }
}
