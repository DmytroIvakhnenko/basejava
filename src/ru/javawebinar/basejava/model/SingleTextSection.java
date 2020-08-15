package ru.javawebinar.basejava.model;

public class SingleTextSection extends TextSection {
    private final String sectionText;

    public SingleTextSection(String sectionTitle, String sectionText) {
        this.sectionText = sectionText;
        super.setSectionTitle(sectionTitle);
    }

    public SingleTextSection(String sectionText) {
        this.sectionText = sectionText;
    }

    public String getSectionText() {
        return sectionText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleTextSection that = (SingleTextSection) o;

        return sectionText.equals(that.sectionText);
    }

    @Override
    public int hashCode() {
        return sectionText.hashCode();
    }

    @Override
    public String toString() {
        return super.toString() + " " + getSectionText();
    }
}
