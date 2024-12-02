package org.ravn.calmusic.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    // Add Logger
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    private static final Dotenv dotenv = Dotenv.load();

    public static Connection getConnection() throws SQLException {
        String dbUrl = dotenv.get("DB_URL");
        String dbUsername = dotenv.get("DB_USERNAME");
        String dbPassword = dotenv.get("DB_PASSWORD");

        logger.info("The credentials are: {}, {}, {}", dbUrl, dbUsername, dbPassword);

        if (dbUrl == null || dbUsername == null || dbPassword == null) {
            throw new SQLException("Database configuration is missing");
        }

        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }
}
