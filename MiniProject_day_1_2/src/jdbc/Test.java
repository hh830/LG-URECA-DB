package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class Test {
    static String url = "jdbc:mysql://localhost:3306/madangdb";
    static String user = "root";
    static String pwd = "root";

    public static void main(String[] args) throws Exception{


        // No driver ... jdbc old version에서 필요할 수도 있음
        // Class.forName("com.mysql.cj.jdbc.Driver");

         //DB 연결 시도하고 연결되면 Connection 객체 획득
        Connection con = DriverManager.getConnection(url, user, pwd);
        Statement stmt = con.createStatement();
        ResultSet rs = null;

        // insert
        {
            String insertSql = "insert into customer values (6, '손흥민', '영국 토트넘', '010-6666-6666'); ";
            int ret = stmt.executeUpdate(insertSql);
            System.out.println(ret);
        }

        // update
        {
            String updateSql = "update customer set address = '한국 서울' where custid = 6; ";
            int ret = stmt.executeUpdate(updateSql);
            System.out.println(ret);
        }

        // select list
        {
            String selectListSql = "select * from customer ";
            rs = stmt.executeQuery(selectListSql);
            while(rs.next()){
                // row 한개당 행(컬럼) 값 추출
                System.out.println(rs.getInt("custid")+ " | " + rs.getString("name") + " | " + rs.getString("address"));
            }
        }

        // select detail (one by pk)
        {
            String selectDetailSql = "select * from customer where custid = 6; ";
            rs = stmt.executeQuery(selectDetailSql);
            if(rs.next()){
                //row 한 개당 행(컬럼) 값 추출
                System.out.println(rs.getInt("custid")+ " | " + rs.getString("name") + " | " + rs.getString("address"));
            }
        }

        // delete
        {
            String deleteSql = "delete from customer where custid = 6; ";
            int ret = stmt.executeUpdate(deleteSql);
            System.out.println(ret);
        }

        // con, stmt <- resource
        stmt.close();
        con.close();

    }


}
//buildpath -> 컴피규어빌드패스? -> javabuildpath > 라이브러리 > class path > 외부 파일로 가서 jar파일 추가
//> referenced libraries