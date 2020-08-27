package ru.javawebinar.basejava.storage.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class YearMonthAdapter extends XmlAdapter<String, YearMonth> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

    @Override
    public YearMonth unmarshal(String v) throws Exception {
        return YearMonth.parse(v, formatter);
    }

    @Override
    public String marshal(YearMonth v) throws Exception {
        return v.format(formatter);
    }
}
