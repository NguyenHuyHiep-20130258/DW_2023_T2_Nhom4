package org.example.Database;

import org.jsoup.Connection;

import java.sql.*;

public class DBConnect {
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = (Connection) DriverManager.getConnection(DBProperties.getJdbcUrl(), DBProperties.getUsername(), DBProperties.getPassword());
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Không thể thiết lập kết nối đến cơ sở dữ liệu.");
            }
        }
        return connection;
    }
}
