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

public class Transform {
    public static void Transform(int id, Connection connection, String date) throws SQLException {
        try {
            //(Transform) 10.1. insert vào data_files với status = TRANSFORMING
            DBConnect.insertStatus(connection, id, "TRANSFORMING", date);
            //(Transform) 10.2. Copy dữ liệu từ staging.lottery_result_staging sang warehouse.lottery_result
            CallableStatement callableStatement = connection.prepareCall("{call CopyDataFromStagingToWarehouse()}");
            //(Transform) 10.3.Thêm dữ liệu miền đó vào region_dim nếu chưa có miền đó
            CallableStatement callableStatement1 = connection.prepareCall("{call AddRegionDim()}");
            //(Transform) 10.4. Thêm dữ liệu tỉnh đó vào province_dim nếu chưa có tỉnh đó
            CallableStatement callableStatement2 = connection.prepareCall("{call AddProvinceDim()}");
            //(Transform) 10.5. Thêm dữ liệu giải đó vào prize_dim nếu chưa có giải đó
            CallableStatement callableStatement3 = connection.prepareCall("{call AddPrizeDim()}");
            //(Transform) 10.6, 10.7, 10.8 trong file sql
            CallableStatement callableStatement4 = connection.prepareCall("{call Transform()}");

            callableStatement.execute();
            callableStatement1.execute();
            callableStatement2.execute();
            callableStatement3.execute();
            callableStatement4.execute();

            callableStatement.close();
            callableStatement1.close();
            callableStatement2.close();
            callableStatement3.close();
            callableStatement4.close();

            System.out.println("Transform successfully!");
            //(Transform) 10.1.9. insert vào data_files với status = TRANSFORMED
            DBConnect.insertStatus(connection, id, "TRANSFORMED", date);
        }
        catch (SQLException e) {
            //(Transform) 10.10. insert vào data_files với status = ERROR và note là lỗi của nó
            DBConnect.insertErrorStatus(connection, id,"ERROR", "Fail to tranform: " + e, date);
            //(Transform) 10.11. Gửi mail báo lỗi
            ErrorEmailSender.sendMail("Transform", "Fail " + e);
            //(Transform) 10.12. Đóng connection database control
            DBConnect.getConnection().close();
            e.printStackTrace();
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
            if(status.equals("TRANSFORMING") || status.equals("EXTRACTED")) {
                Transform(config.getId(), connection, date);
            }
            else {
                if (status.equals("TRANSFORMED")) {
                    System.out.println("Data has been transformed!");
                    DBConnect.insertErrorStatus(connection, config.getId(), "ERROR", "Data has been transformed!", date);
                }
                else {
                    System.out.println("Data has been another process!");
                    DBConnect.insertErrorStatus(connection, config.getId(), "ERROR", "Data has been another process!", date);
                }
            }
        }
    }
}
