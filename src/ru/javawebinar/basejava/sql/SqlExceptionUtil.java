package ru.javawebinar.basejava.sql;

import org.postgresql.util.PSQLException;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.SQLException;

import static org.postgresql.util.PSQLState.UNIQUE_VIOLATION;

public class SqlExceptionUtil {
    public SqlExceptionUtil() {
    }

    public static StorageException convertException(SQLException e) {
        if (e instanceof PSQLException) {
//          http://www.postgresql.org/docs/9.3/static/errcodes-appendix.html
            if (e.getSQLState().equals(UNIQUE_VIOLATION.getState())) {
                return new ExistStorageException();
            }
        }
        return new StorageException(e);
    }
}
