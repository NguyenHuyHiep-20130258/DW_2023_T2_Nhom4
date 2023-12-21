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

public class AggregateData {
    public static void aggregateData(int id, Connection connection, String date) throws SQLException {
        try {
            //(Aggregate) 12.1. insert vào data_files với status = AGGREGATING
            DBConnect.insertStatus(connection, id, "AGGREGATING", date);
            //(Aggregate) 12.2, 12.3, 12.4, 12.5, 12.6 trong file sql
            CallableStatement callableStatement = connection.prepareCall("{call AggregateData()}");
            callableStatement.execute();
            callableStatement.close();
            System.out.println("aggregate successfully!");
            //(Aggregate) 12.7. insert vào data_files với status = AGGREGATED
            DBConnect.insertStatus(connection, id, "AGGREGATED", date);
        }
        catch (SQLException e) {
            e.printStackTrace();
            //(Aggregate) 11.8. insert vào data_files với status = ERROR và note là lỗi của nó
            DBConnect.insertErrorStatus(connection, id,"ERROR", "Fail to aggregate data: " + e, date);
            //(Aggregate) 11.9. Gửi mail báo lỗi
            ErrorEmailSender.sendMail("Aggregate data", "Fail " + e);
            //(Aggregate) 11.10. Đóng connection database control
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
            if(status.equals("AGGREGATING") || status.equals("LOADEDWH")) {
                aggregateData(config.getId(), connection, date);
            }
            else {
                if (status.equals("AGGREGATED")) {
                    System.out.println("Data has been aggregated!");
                    DBConnect.insertErrorStatus(connection, config.getId(), "ERROR", "Data has been aggregated!", date);
                }
                else {
                    System.out.println("Data has been another process!");
                    DBConnect.insertErrorStatus(connection, config.getId(), "ERROR", "Data has been another process!", date);
                }
            }
        }
    }
}