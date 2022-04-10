package sudoku;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

    private static Connection connection = null;

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (connection == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // connection=DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/e20XExi21E","e20XExi21E","MWl3O0MpFD");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://oopsudoku.cmduinmwunx6.ap-southeast-1.rds.amazonaws.com:3306/OOP_Sudoku", "admin",
                    "admin123");
            System.out.print("connected");
        }
        return connection;
    }
}
