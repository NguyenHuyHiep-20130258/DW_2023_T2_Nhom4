package org.example.Module;

import org.example.Database.DBConnect;
import org.example.Mail.ErrorEmailSender;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class AggregateData {
    public static void aggregateData(int id, Connection connection, String date) throws SQLException {
        try {
            DBConnect.insertStatus(connection, id, "AGGREGATING", date);
            CallableStatement callableStatement = connection.prepareCall("{call AggregateData()}");
            callableStatement.execute();
            callableStatement.close();
            System.out.println("aggregate successfully!");
            DBConnect.insertStatus(connection, id, "AGGREGATED", date);
        }
        catch (SQLException e) {
            e.printStackTrace();
            DBConnect.insertErrorStatus(connection, id,"ERROR", "Fail to aggregate data: " + e, date);
            ErrorEmailSender.sendMail("Aggregate data", "Fail " + e);
            DBConnect.getConnection().close();
        }

    }
}