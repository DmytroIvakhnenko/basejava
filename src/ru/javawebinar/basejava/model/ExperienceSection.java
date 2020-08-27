package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class ExperienceSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<Experience> experienceList = new ArrayList<>();

    public ExperienceSection() {
    }

    public ExperienceSection(List<Experience> experienceList) {
        Objects.requireNonNull(experienceList, "Experience can't be null");
        this.experienceList = experienceList;
    }

    public ExperienceSection(Experience... experiences) {
        this(Arrays.asList(experiences));
    }

    public List<Experience> getExperienceList() {
        return experienceList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExperienceSection that = (ExperienceSection) o;
        return getExperienceList().equals(that.getExperienceList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getExperienceList());
    }

    @Override
    public String toString() {
        return experienceList.toString();
    }
}
