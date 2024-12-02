package org.ravn.calmusic.config;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {

    private Connection connection;

    public TransactionManager() throws SQLException {
        connection = DatabaseConfig.getConnection();
        connection.setAutoCommit(false); // Disable auto-commit for manual transaction management
    }

    public void commit() throws SQLException {
        connection.commit();
    }

    public void rollback() throws SQLException {
        connection.rollback();
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
