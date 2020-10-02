package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NonExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    public static final String INTEGRITY_CONSTRAINT_VIOLATION_CODE = "23";
    private final ConnectionFactory connectionFactory;
    private final SqlHelper sqlHelper = new SqlHelper();

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.executeRequest(connectionFactory, "DELETE FROM resume", (ps) -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.executeRequest(connectionFactory, "UPDATE resume SET full_name = ? WHERE uuid = ?", (ps) -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NonExistStorageException(r.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.executeRequest(connectionFactory, "INSERT INTO resume (uuid, full_name) VALUES (?,?)", (ps) -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            try {
                ps.execute();
            } catch (SQLException e) {
                if (e.getSQLState().startsWith(INTEGRITY_CONSTRAINT_VIOLATION_CODE)) {
                    throw new ExistStorageException(r.getUuid());
                } else {
                    throw e;
                }
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return (Resume) sqlHelper.executeRequest(connectionFactory, "SELECT * FROM resume r WHERE r.uuid =?", (ps) -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NonExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.executeRequest(connectionFactory, "DELETE FROM resume r WHERE r.uuid=?", (ps) -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NonExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return (List<Resume>) sqlHelper.executeRequest(connectionFactory, "SELECT * FROM resume r ORDER BY uuid", (ps) -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        return (int) sqlHelper.executeRequest(connectionFactory, "SELECT COUNT(*) FROM resume", (ps) -> {
            ps.execute();
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("count");
        });
    }

    public static class SqlHelper<R> {
        public R executeRequest(ConnectionFactory cf, String query, CodeBlock<PreparedStatement, R> codeBlock) {
            try (Connection conn = cf.getConnection();
                 PreparedStatement ps = conn.prepareStatement(query)) {
                return codeBlock.execute(ps);
            } catch (SQLException e) {
                throw new StorageException(e);
            }
        }
    }

    public interface CodeBlock<T, R> {
        R execute(PreparedStatement ps) throws SQLException;
    }
}
