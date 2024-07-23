package app_book_common.common;

import java.sql.*;

// Connection 객체를 생성, 전달
// ResultSet, PreparedStatement, Connection 객체 종료 close()
public class DBManager {
    static String url = "jdbc:mysql://localhost:3306/madangdb";
    static String user = "root";
    static String pwd = "root";

    public static Connection getConnection(){
        Connection con = null;
        try{
            con = DriverManager.getConnection(url, user, pwd);
        } catch (SQLException e){
            e.printStackTrace();
        }

        return con;
    }

    public static void releaseConnection(PreparedStatement pstmt, Connection con){
        try {
            pstmt.close();
            con.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void releaseConnection(ResultSet rs, PreparedStatement pstmt, Connection con){
        try {
            rs.close();
            pstmt.close();
            con.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
