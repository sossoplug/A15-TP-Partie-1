/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author mac
 */
public class DBOperation {
    
    public static Connection connectionBd() throws SQLException {
        String dbURL = "jdbc:oracle:thin:@144.217.163.57:1521:XE";
        String dbUser = "a10e5";
        String dbPass = "anypw5";

        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
        return conn;
    }
}
