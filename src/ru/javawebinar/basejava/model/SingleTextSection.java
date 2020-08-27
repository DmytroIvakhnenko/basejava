package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class SingleTextSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private String text;

    public SingleTextSection() {
    }

    public SingleTextSection(String title, String text) {
        this.text = text;
        super.setTitle(title);
    }

    public SingleTextSection(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleTextSection that = (SingleTextSection) o;

        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    @Override
    public String toString() {
        return super.toString() + getText();
    }
}
