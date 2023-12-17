package org.example.Module;

import org.example.Database.DBConnect;
import org.example.Mail.ErrorEmailSender;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class LoadToMart {
    public static void LoadToMart(int id, Connection connection, String date) throws SQLException {
        try {
            DBConnect.insertStatus(connection, id, "LOADINGM", date);
            CallableStatement callableStatement = connection.prepareCall("{call LoadToMart()}");
            callableStatement.execute();
            callableStatement.close();
            System.out.println("Load to mart successfully!");
            DBConnect.insertStatus(connection, id, "LOADEDM", date);
            DBConnect.insertStatus(connection, id, "FINISHED", date);
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnect.insertErrorStatus(connection, id,"ERROR", "Fail to load to mart: " + e, date);
            ErrorEmailSender.sendMail("Load to mart", "Fail " + e);
            DBConnect.getConnection().close();
        }
    }
}
