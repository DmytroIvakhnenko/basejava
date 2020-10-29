package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NonExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
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
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.executeRequest("" +
                        "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "     WHERE r.uuid =? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NonExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        getContact(r, rs);
                    }
                    while (rs.next());
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
        return sqlHelper.executeRequest("" +
                        "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "  ORDER BY full_name, uuid ",
                (ps) -> {
                    ResultSet rs = ps.executeQuery();
                    List<Resume> resumes = new ArrayList<>();
                    Resume r = null;
                    while (rs.next()) {
                        String id = rs.getString("uuid");
                        if (resumes.stream().map(Resume::getUuid).noneMatch(x -> x.equals(id))) {
                            r = new Resume(id, rs.getString("full_name"));
                            resumes.add(r);
                        }
                        getContact(r, rs);
                    }
                    return resumes;
                });
    }

    @Override
    public int size() {
        return sqlHelper.executeRequest("SELECT COUNT(*) FROM resume", (ps) -> {
            ps.execute();
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

    private void getContact(final Resume r, final ResultSet rs) throws SQLException {
        if (rs.getString("resume_uuid") != null) {
            String value = rs.getString("value");
            ContactType type = ContactType.valueOf(rs.getString("type"));
            r.addContact(type, value);
        }
    }
}
