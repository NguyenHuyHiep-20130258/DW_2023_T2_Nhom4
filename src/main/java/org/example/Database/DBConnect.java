package org.example.Database;


import org.example.Entity.DataFileConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnect {
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DBProperties.getJdbcUrl(), DBProperties.getUsername(), DBProperties.getPassword());
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Không thể thiết lập kết nối đến cơ sở dữ liệu.");
            }
        }
        return connection;
    }

    public static List<DataFileConfig> getConfigurationsWithFlagOne(Connection connection) {
        List<DataFileConfig> configurations = new ArrayList<>();

        String query = "SELECT id, name, description, source_path, location, flag FROM control.data_file_configs WHERE flag = 1";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("id");
                String description = resultSet.getString("description");
                String source_path = resultSet.getString("source_path");
                String location = resultSet.getString("location");
                int flag = resultSet.getInt("flag");
                configurations.add(new DataFileConfig(id, name, description, source_path, location, flag));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return configurations;
    }

    public static String getLatestStatusWithoutError(Connection connection, int idConfig) {
        String status = "";
        String query = "SELECT `status` FROM control.data_files WHERE df_config_id=? AND status not like '%ERROR%' ORDER BY file_timestamp DESC , data_files.df_config_id DESC, id DESC LIMIT 1";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idConfig);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    status = resultSet.getString("status");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }



    public static void insertStatus(Connection connection, int id, String status, String date) {
        try (CallableStatement callableStatement = connection.prepareCall("{CALL InsertStatus(?,?,?)}")) {
            callableStatement.setInt(1, id);
            callableStatement.setString(2, status);
            callableStatement.setString(3,date);
            callableStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertErrorStatus(Connection connection, int id, String status, String note, String date) {
        try (CallableStatement callableStatement = connection.prepareCall("{CALL InsertErrorStatus(?,?,?,?)}")) {
            callableStatement.setInt(1, id);
            callableStatement.setString(2, status);
            callableStatement.setString(3,note);
            callableStatement.setString(4,date);
            callableStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
