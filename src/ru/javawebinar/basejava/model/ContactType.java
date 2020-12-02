package ru.javawebinar.basejava.model;

import static ru.javawebinar.basejava.model.util.contactTypeHtmlView.getHtmlView;

public enum ContactType {
    TELEPHONE("Тел."),
    SKYPE("Skype"),
    EMAIL("Почта"),
    LINKEDIN("LinkedIn"),
    GITHUB("GitHUB"),
    STACKOVERFLOW("StackOverflow"),
    HOMEPAGE("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getHtml(String value) {
        return getHtmlView(value, this);
    }
}
