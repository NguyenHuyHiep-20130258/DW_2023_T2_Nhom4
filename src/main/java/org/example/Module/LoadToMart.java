package org.example.Module;

import org.example.Database.DBConnect;
import org.example.Entity.DataFileConfig;
import org.example.Mail.ErrorEmailSender;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class LoadToMart {
    public static void LoadToMart(int id, Connection connection, String date) throws SQLException {
        try {
            //(LoadToMart) 12.1. insert vào data_files với status = LOADINGM
            DBConnect.insertStatus(connection, id, "LOADINGM", date);
            //(LoadToMart) 12.2, 12.3 trong file sql
            CallableStatement callableStatement = connection.prepareCall("{call LoadToMart()}");
            callableStatement.execute();
            callableStatement.close();
            System.out.println("Load to mart successfully!");
            //(LoadToMart) 12.4. insert vào data_files với status = LOADEDM và status = FINISHED
            DBConnect.insertStatus(connection, id, "LOADEDM", date);
            DBConnect.insertStatus(connection, id, "FINISHED", date);
        } catch (SQLException e) {
            e.printStackTrace();
            //(LoadToMart) 12.5. insert vào data_files với status = ERROR và note là lỗi của nó
            DBConnect.insertErrorStatus(connection, id,"ERROR", "Fail to load to mart: " + e, date);
            //(LoadToMart) 12.6. Gửi mail báo lỗi
            ErrorEmailSender.sendMail("Load to mart", "Fail " + e);
            //(LoadToMart) 12.7. Đóng connection database control
            DBConnect.getConnection().close();
        }
    }

    public static void main(String[] args) throws SQLException {
        String date = LocalDate.now().toString();
        Connection connection = DBConnect.getConnection();
        List<DataFileConfig> configs = DBConnect.getConfigurationsWithFlagOne(connection);
        for (DataFileConfig config : configs) {
            LoadToMart(config.getId(), connection, date);
        }
    }
}
