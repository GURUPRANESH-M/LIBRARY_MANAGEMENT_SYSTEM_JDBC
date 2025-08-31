import java.sql.*;
public class db {
    private static String url = "jdbc:mysql://localhost:3306/lms";
    private static String usrname = "root";
    private static String pwd = "root";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url,usrname,pwd);
    }
}
