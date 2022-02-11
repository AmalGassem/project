package com.best.app.patientmedicalfile.PackageClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connection {
    String USER_NAME="userforall";
    String PASSWORD="123456";
    String CONNECTION_URL="jdbc:mysql://10.0.2.2:3306/patientmedicalfiledb";
   // GRANT ALL PRIVILEGES ON `patientmedicalfiledb`.* TO 'userforall'@'localhost' WITH GRANT OPTION;
    public Connection connection;
    public Connection DB_Connection_open ()
    {
        try {
            connection = DriverManager.getConnection(CONNECTION_URL,USER_NAME,PASSWORD);
            return connection;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public void DB_Close()
    {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
