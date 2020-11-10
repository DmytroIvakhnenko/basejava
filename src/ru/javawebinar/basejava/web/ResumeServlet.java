package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.SqlStorage;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class ResumeServlet extends HttpServlet {
    SqlStorage sqlStorage;

    @Override
    public void init(final ServletConfig config) {
        Config.setConfigurationPath(config.getServletContext().getInitParameter("configurationPath"));
        sqlStorage = Config.getInstance().getSqlStorage();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        Writer writer = response.getWriter();
        writer.write("" +
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <link rel=\"stylesheet\" href=\"css/style.css\">\n" +
                "    <title>Resume list</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<header>Приложение вебинара <a href=\"http://javawebinar.ru/basejava/\" target=\"_blank\">Практика Java. Разработка Web\n" +
                "    приложения.\"</a></header>\n" +
                "<table style=\"width:100%\">\n" +
                "    <tr>\n" +
                "        <th>UUID</th>\n" +
                "        <th>Full name</th>\n" +
                "    </tr>\n");
        for (Resume r : sqlStorage.getAllSorted()) {
            writer.write(String.format("<tr><td>%s</td><td>%s</td></tr>", r.getUuid(), r.getFullName()));
        }
        writer.write("" +
                "</table>\n" +
                "<footer>Приложение вебинара <a href=\"http://javawebinar.ru/basejava/\" target=\"_blank\">Практика Java. Разработка Web\n" +
                "    приложения.\"</a></footer>\n" +
                "</body>\n" +
                "</html>");
    }
}
