package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.postgresql.util.PSQLState.UNIQUE_VIOLATION;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T executeRequest(String query, SqlExecutor<T> sqlExecutor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            return sqlExecutor.execute(ps);
        } catch (SQLException e) {
            if (e.getSQLState().equals(UNIQUE_VIOLATION.getState())) {
                throw new ExistStorageException();
            } else {
                throw new StorageException(e);
            }

        }
    }
}

