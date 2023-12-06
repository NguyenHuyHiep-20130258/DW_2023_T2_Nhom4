package org.example.Module;

import org.example.Database.DBConnect;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class TransformDimToFact {
    public static void Transform(int id, Connection connection, String date) throws SQLException {
        try {
            DBConnect.insertStatus(connection, id, "TRANSFORMING", date);
            CallableStatement callableStatement = connection.prepareCall("{call CopyDataFromStagingToWarehouse()}");
            CallableStatement callableStatement1 = connection.prepareCall("{call TransformRegionDim()}");
            CallableStatement callableStatement2 = connection.prepareCall("{call TransformProvinceDim()}");
            CallableStatement callableStatement3 = connection.prepareCall("{call TransformPrizeDim()}");
            CallableStatement callableStatement4 = connection.prepareCall("{call TranferToFact()}");

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

            System.out.println("Tranform successfully!");
            DBConnect.insertStatus(connection, id, "TRANSFORMED", date);
        }
        catch (SQLException e) {
            DBConnect.insertErrorStatus(connection, id,"ERROR", "Fail to tranform: " + e, date);
            DBConnect.getConnection().close();
            e.printStackTrace();
        }

    }
}
