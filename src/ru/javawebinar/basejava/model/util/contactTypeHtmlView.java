package ru.javawebinar.basejava.model.util;

import ru.javawebinar.basejava.model.ContactType;

public class contactTypeHtmlView {
    private static String getLink(String prefix, String link, String title) {
        return "<a href = '" + prefix + link + "' >" + link + "</a > ";
    }

    public static String getHtmlView(String value, ContactType type) {
        String htmlVal;
        switch (type) {
            case TELEPHONE:
                htmlVal = getLink("tel:", value, type.getTitle());
                break;
            case SKYPE:
                htmlVal = getLink("skype:", value, type.getTitle());
                break;
            case EMAIL:
                htmlVal = getLink("mailto:", value, type.getTitle());
                break;
            default:
                htmlVal = getLink("", value, type.getTitle());
                break;
        }
        return htmlVal;
    }
}
