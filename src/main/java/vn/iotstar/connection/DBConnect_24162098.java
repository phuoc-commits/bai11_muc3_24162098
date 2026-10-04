package vn.iotstar.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect_24162098 {
    private static final String URL = "jdbc:mysql://localhost:3306/WebDB_24162098?useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root"; 
    private static final String PASS = "phuoc2006@"; 

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}