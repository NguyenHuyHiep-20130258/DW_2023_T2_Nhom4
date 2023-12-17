package org.example.Module;

import org.example.Database.DBConnect;
import org.example.Mail.ErrorEmailSender;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class LoadToWareHouse {
    public static void LoadtoWareHouse(int id, Connection connection, String date) throws SQLException {
        try {
            //(LoadToWarehouse) 10.1. insert vào data_files với status = LOADINGWH
            DBConnect.insertStatus(connection, id, "LOADINGWH", date);
            //(LoadToWarehouse) 10.2. Copy tất cả dữ liệu từ bảng tạm temp_fact đã tạo từ bước Tranform vào bảng warehouse.lottery_result_fact
            CallableStatement callableStatement = connection.prepareCall("{call LoadToWareHouse()}");
            callableStatement.execute();
            callableStatement.close();

            System.out.println("Load to warehouse successfully!");
            //(LoadToWarehouse) 10.3. insert vào data_files với status = LOADEDWH
            DBConnect.insertStatus(connection, id, "LOADEDWH", date);
        }
        catch (SQLException e) {
            e.printStackTrace();
            //(LoadToWarehouse) 10.4. insert vào data_files với status = ERROR và note là lỗi của nó
            DBConnect.insertErrorStatus(connection, id,"ERROR", "Fail to load to warehouse: " + e, date);
            //(LoadToWarehouse) 10.5. Gửi mail báo lỗi
            ErrorEmailSender.sendMail("Load to warehouse", "Fail " + e);
            //(LoadToWarehouse) 10.6. Đóng connection database control
            DBConnect.getConnection().close();
        }

    }
}
