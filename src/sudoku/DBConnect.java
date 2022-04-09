package sudoku;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

    private static Connection connection = null;

	public static Connection getConnection() throws ClassNotFoundException, SQLException{
        if(connection == null){
            Class.forName("com.mysql.cj.jdbc.Driver");  
            connection=DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/e20XExi21E","e20XExi21E","MWl3O0MpFD");
            System.out.print("connected");
        }
        return connection;
    }
}
