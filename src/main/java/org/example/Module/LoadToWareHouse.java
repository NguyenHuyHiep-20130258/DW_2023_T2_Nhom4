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

public class LoadToWareHouse {
    public static void LoadtoWareHouse(int id, Connection connection, String date) throws SQLException {
        try {
            //(LoadToWarehouse) 11.1. insert vào data_files với status = LOADINGWH
            DBConnect.insertStatus(connection, id, "LOADINGWH", date);
            //(LoadToWarehouse) 11.2. Copy tất cả dữ liệu từ bảng tạm temp_fact đã tạo từ bước Tranform vào bảng warehouse.lottery_result_fact
            CallableStatement callableStatement = connection.prepareCall("{call LoadToWareHouse()}");
            callableStatement.execute();
            callableStatement.close();

            System.out.println("Load to warehouse successfully!");
            //(LoadToWarehouse) 11.3. insert vào data_files với status = LOADEDWH
            DBConnect.insertStatus(connection, id, "LOADEDWH", date);
        }
        catch (SQLException e) {
            e.printStackTrace();
            //(LoadToWarehouse) 11.4. insert vào data_files với status = ERROR và note là lỗi của nó
            DBConnect.insertErrorStatus(connection, id,"ERROR", "Fail to load to warehouse: " + e, date);
            //(LoadToWarehouse) 11.5. Gửi mail báo lỗi
            ErrorEmailSender.sendMail("Load to warehouse", "Fail " + e);
            //(LoadToWarehouse) 11.6. Đóng connection database control
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
            if(status.equals("LOADINGWH") || status.equals("TRANSFORMED")) {
                LoadtoWareHouse(config.getId(), connection, date);
            }
            else {
                if (status.equals("LOADEDWH")) {
                    System.out.println("Data has been loaded to warehouse!");
                    DBConnect.insertErrorStatus(connection, config.getId(), "ERROR", "Data has been loaded to warehouse!", date);
                }
                else {
                    System.out.println("Data has been another process!");
                    DBConnect.insertErrorStatus(connection, config.getId(), "ERROR", "Data has been another process!", date);
                }
            }
        }
    }
}
