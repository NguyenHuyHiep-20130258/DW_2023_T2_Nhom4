package org.example.Module;

import org.example.Database.DBConnect;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class LoadToWareHouse {
    public static void LoadtoWareHouse(int id, Connection connection, String date) {
        try {
            DBConnect.insertStatus(connection, id, "LOADINGWH", date);
            CallableStatement callableStatement = connection.prepareCall("{call LoadToWareHouse()}");
            callableStatement.execute();
            callableStatement.close();

            System.out.println("Load to warehouse successfully!");
            DBConnect.insertStatus(connection, id, "LOADEDWH", date);
        }
        catch (SQLException e) {
            DBConnect.insertErrorStatus(connection, id,"ERROR", "Fail to load to warehouse: " + e, date);
            e.printStackTrace();
        }

    }
}
