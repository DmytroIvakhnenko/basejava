package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
            throw SqlExceptionUtil.convertException(e);
        }
    }

    public <T> T transactionalExecute(SqlTransaction<T> sqlTransaction) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = sqlTransaction.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw SqlExceptionUtil.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
