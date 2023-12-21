package org.example.Module;

import org.example.Database.DBConnect;
import org.example.Database.DBProperties;
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
            //(LoadToMart) 13.1. insert vào data_files với status = LOADINGM
            DBConnect.insertStatus(connection, id, "LOADINGM", date);
            //(LoadToMart) 13.2, 13.3 trong file sql
            CallableStatement callableStatement = connection.prepareCall("{call LoadToMart()}");
            callableStatement.execute();
            callableStatement.close();
            System.out.println("Load to mart successfully!");
            //(LoadToMart) 13.4. insert vào data_files với status = LOADEDM và status = FINISHED
            DBConnect.insertStatus(connection, id, "LOADEDM", date);
            DBConnect.insertStatus(connection, id, "FINISHED", date);
        } catch (SQLException e) {
            e.printStackTrace();
            //(LoadToMart) 13.5. insert vào data_files với status = ERROR và note là lỗi của nó
            DBConnect.insertErrorStatus(connection, id,"ERROR", "Fail to load to mart: " + e, date);
            //(LoadToMart) 13.6. Gửi mail báo lỗi
            ErrorEmailSender.sendMail("Load to mart", "Fail " + e);
            //(LoadToMart) 13.7. Đóng connection database control
            DBConnect.getConnection().close();
        }
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = DBConnect.getConnection();
        LocalDate dateNow = LocalDate.now();
        String run = DBProperties.getRun();
        String date = (run.equals("auto") ? dateNow.getYear() + "-" + (dateNow.getMonthValue() < 10 ? "0" + dateNow.getMonthValue() : dateNow.getMonthValue()) + "-" + (dateNow.getDayOfMonth() < 10 ? "0" + dateNow.getDayOfMonth() : dateNow.getDayOfMonth()) : run);
        List<DataFileConfig> configs = DBConnect.getConfigurationsWithFlagOne(connection);
        for (DataFileConfig config : configs) {
            String status = DBConnect.getLatestStatusWithoutError(connection, config.getId());
            if(status.equals("LOADINGM") || status.equals("AGGREGATED")) {
                LoadToMart(config.getId(), connection, date);
            }
            else {
                if (status.equals("LOADEDM")) {
                    System.out.println("Data has been loaded mart!");
                    DBConnect.insertErrorStatus(connection, config.getId(), "ERROR", "Data has been loaded mart!", date);
                }
                else {
                    System.out.println("Data has been another process!");
                    DBConnect.insertErrorStatus(connection, config.getId(), "ERROR", "Data has been another process!", date);
                }
            }
        }
    }
}
