package com.project.testapplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcConnection {

    private static Optional<Connection> connection = Optional.empty();

    public static Optional<Connection> getConnection() {
        if (connection.isEmpty()) {
            String url = "jdbc:postgresql://localhost:5432/TestApplication";
            String user = "postgres";
            String password = "studentkaufy18";

            try {
                connection = Optional.ofNullable(
                        DriverManager.getConnection(url, user, password));
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());;
            }
        }

        return connection;
    }
}