package util;

import java.sql.*;

public class ConnectionFactory {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/loja", "root", "root"
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

