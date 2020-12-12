package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NonExistStorageException;
import ru.javawebinar.basejava.model.AbstractSection;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.sql.SqlHelper;
import ru.javawebinar.basejava.storage.util.JsonParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.<Void>executeRequest("DELETE FROM resume", (ps) -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.<Void>transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NonExistStorageException(r.getUuid());
                }
            }
            deleteContacts(conn, r.getUuid());
            saveContacts(conn, r);
            deleteSections(conn, r.getUuid());
            saveSections(conn, r);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.<Void>transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            saveContacts(conn, r);
            saveSections(conn, r);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            Resume r;
            try (PreparedStatement psResumes = conn.prepareStatement("SELECT * FROM resume r WHERE r.uuid= ?");
                 PreparedStatement psContacts = conn.prepareStatement("SELECT * FROM contact c WHERE c.resume_uuid= ?");
                 PreparedStatement psSections = conn.prepareStatement("SELECT * FROM section s WHERE s.resume_uuid= ?")) {
                psResumes.setString(1, uuid);
                psContacts.setString(1, uuid);
                psSections.setString(1, uuid);
                ResultSet rs = psResumes.executeQuery();
                if (!rs.next()) {
                    throw new NonExistStorageException(uuid);
                }
                r = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                rs = psContacts.executeQuery();
                while (rs.next()) {
                    setContact(r, rs);
                }
                rs = psSections.executeQuery();
                while (rs.next()) {
                    setSection(r, rs);
                }
            }
            return r;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.<Void>executeRequest("DELETE FROM resume r WHERE r.uuid=?", (ps) -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NonExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();
            try (PreparedStatement psResumes = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid");
                 PreparedStatement psContacts = conn.prepareStatement("SELECT * FROM contact");
                 PreparedStatement psSections = conn.prepareStatement("SELECT * FROM section")) {
                ResultSet rs = psResumes.executeQuery();
                while (rs.next()) {
                    resumes.put(rs.getString("uuid"), new Resume(rs.getString("uuid"), rs.getString("full_name")));
                }
                rs = psContacts.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    Resume r = resumes.get(uuid);
                    setContact(r, rs);
                }
                rs = psSections.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    Resume r = resumes.get(uuid);
                    setSection(r, rs);
                }
            }
            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.executeRequest("SELECT COUNT(*) FROM resume", (ps) -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("count");
        });
    }

    private void deleteContacts(final Connection conn, final String uuid) throws SQLException {
        try (Statement s = conn.createStatement()) {
            s.execute(String.format("DELETE FROM contact c WHERE c.resume_uuid='%s';", uuid));
        }
    }

    private void deleteSections(final Connection conn, final String uuid) throws SQLException {
        try (Statement s = conn.createStatement()) {
            s.execute(String.format("DELETE FROM section s WHERE s.resume_uuid='%s';", uuid));
        }
    }

    private void saveContacts(final Connection conn, final Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (value, resume_uuid, type) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, e.getValue());
                ps.setString(2, r.getUuid());
                ps.setString(3, e.getKey().name());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void saveSections(final Connection conn, final Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (content, resume_uuid, type) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> e : r.getSections().entrySet()) {
                SectionType st = e.getKey();
                AbstractSection section = e.getValue();
                ps.setString(1, JsonParser.write(section, AbstractSection.class));
                ps.setString(2, r.getUuid());
                ps.setString(3, e.getKey().name());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void setContact(final Resume r, final ResultSet rs) throws SQLException {
        if (rs.getString("resume_uuid") != null) {
            r.setContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
        }
    }

    private void setSection(final Resume r, final ResultSet rs) throws SQLException {
        if (rs.getString("resume_uuid") != null) {
            SectionType st = SectionType.valueOf(rs.getString("type"));
            r.setSection(st, JsonParser.read(rs.getString("content"), AbstractSection.class));
        }
    }
}
