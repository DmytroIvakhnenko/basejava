package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NonExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.<Void>executeRequest("TRUNCATE TABLE resume CASCADE", (ps) -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.<Void>executeRequest("UPDATE resume SET full_name = ? WHERE uuid = ?", ps -> {
                    ps.setString(1, r.getFullName());
                    ps.setString(2, r.getUuid());
                    if (ps.executeUpdate() == 0) {
                        throw new NonExistStorageException(r.getUuid());
                    }
                    return null;
                }
        );
        if (resumeHasContacts(r)) {
            updateContacts(r);
        } else {
            saveContacts(r);
        }
    }

    @Override
    public void save(Resume r) {
        sqlHelper.<Void>executeRequest("INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
                    ps.setString(1, r.getUuid());
                    ps.setString(2, r.getFullName());
                    ps.execute();
                    return null;
                }
        );
        saveContacts(r);
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
                    getContacts(r, rs);
                    return r;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.<Void>transactionalExecute(conn -> {
                    try (Statement s = conn.createStatement()) {
                        s.addBatch(String.format("DELETE FROM contact c WHERE c.resume_uuid='%s'", uuid));
                        s.addBatch(String.format("DELETE FROM resume c WHERE c.uuid='%s'", uuid));
                        if (Arrays.stream(s.executeBatch()).anyMatch(x -> x == 0)) {
                            throw new NonExistStorageException(uuid);
                        }
                    }
                    return null;
                }
        );
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
                    rs.next();
                    for (int i = 0; i < size(); i++) {
                        Resume r = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                        getContacts(r, rs);
                        resumes.add(r);
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

    private void saveContacts(final Resume r) {
        contactsTransactionalRequest(r, "INSERT INTO contact (value, resume_uuid, type) VALUES (?,?,?)");
    }

    private void updateContacts(final Resume r) {
        contactsTransactionalRequest(r, "UPDATE contact SET value = ? WHERE resume_uuid = ? AND type = ?");
    }

    private void contactsTransactionalRequest(final Resume r, final String sqlRequest) {
        sqlHelper.<Void>transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement(sqlRequest)) {
                        for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                            ps.setString(1, e.getValue());
                            ps.setString(2, r.getUuid());
                            ps.setString(3, e.getKey().name());
                            ps.addBatch();
                        }
                        ps.executeBatch();
                    }
                    return null;
                }
        );
    }

    private void getContacts(final Resume r, final ResultSet rs) throws SQLException {
        do {
            if (rs.getString("resume_uuid") != null) {
                String value = rs.getString("value");
                ContactType type = ContactType.valueOf(rs.getString("type"));
                r.addContact(type, value);
            } else {
                rs.next(); //move cursor in case of resume without contacts
                return;
            }
        }
        while (r.getUuid().equals(rs.getString("resume_uuid")) && rs.next());
    }

    private boolean resumeHasContacts(Resume r) {
        return sqlHelper.executeRequest("SELECT COUNT (*) FROM contact WHERE resume_uuid = ?", ps -> {
            ps.setString(1, r.getUuid());
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("count") > 0;
        });
    }
}
