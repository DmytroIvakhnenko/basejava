package ru.javawebinar.basejava.model;

public abstract class AbstractResumeTextSection {
    private String sectionTitle;

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    @Override
    public abstract String toString();
}
