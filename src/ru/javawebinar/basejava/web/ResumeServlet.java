package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.SqlStorage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class ResumeServlet extends HttpServlet {
    private SqlStorage sqlStorage;

    @Override
    public void init(final ServletConfig config) {
        Config.setConfigurationPath(config.getServletContext().getInitParameter("configurationPath"));
        sqlStorage = Config.getInstance().getSqlStorage();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume resume = isSaveAction(request) ? new Resume(uuid, "") : sqlStorage.get(uuid);
        resume.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (notEmpty(value)) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            switch (type) {
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    String[] values = request.getParameterValues(String.valueOf(type));
                    if (Objects.nonNull(values) && Arrays.stream(values).anyMatch(ResumeServlet::notEmpty)) {
                        resume.addSection(type, new ListSection(Arrays.stream(values).filter(ResumeServlet::notEmpty).collect(Collectors.toList())));
                    } else {
                        resume.getSections().remove(type);
                    }
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    break;
                case PERSONAL:
                case OBJECTIVE:
                    String value = request.getParameter(type.name());
                    if (notEmpty(value)) {
                        resume.addSection(type, new TextSection(value));
                    } else {
                        resume.getSections().remove(type);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Type: " + type + " is illegal");
            }
        }
        if (isSaveAction(request)) {
            sqlStorage.save(resume);
        } else {
            sqlStorage.update(resume);
        }
        response.sendRedirect("resume");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (Objects.isNull(action)) {
            request.setAttribute("allResumes", sqlStorage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete":
                sqlStorage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                resume = sqlStorage.get(uuid);
                break;
            case "add":
                resume = new Resume("");
                break;
            default:
                throw new IllegalArgumentException("Action: " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(action.equals("view") ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp").forward(request, response);
    }

    private static boolean notEmpty(String value) {
        return (value != null && value.trim().length() != 0);
    }

    private static boolean isSaveAction(HttpServletRequest request) {
        return request.getParameter("action").equals("add");
    }
}
