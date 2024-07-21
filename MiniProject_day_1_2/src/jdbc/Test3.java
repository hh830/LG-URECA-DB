package jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// 개별 메소를 파라미터, 리턴을 추가해서 main 협업 메소드 내 하드코딩 제거
// Statement -> PreparedStatement : SQL 문자열 쓰기 힘들어서 도입 - ? 쓸 수 있게 함
// select query 처리에 dto 적용
public class Test3 {
    static String url = "jdbc:mysql://localhost:3306/madangdb";
    static String user = "root";
    static String pwd = "root";

    public static void main(String[] args){
        int ret = -1;
        //ret = insertCustomer(6, "손흥민", "영국 토트넘", "010-2222-4444");
        System.out.println(ret);

        //ret = updateCustomer(6, "한국 서울");
        //listCustomer();
        //detailCustomer();
        //deleteCustomer();
        System.out.println(ret);

//        List<CustomerDTO> list = listCustomer();
//        for(CustomerDTO dto : list){
//            System.out.println(dto);
//        }

        CustomerDTO dto = detailCustomer(6);
        System.out.println(dto);
    }

    static int insertCustomer(int custId, String name, String address, String phone) {
        Connection con = null;
        PreparedStatement pstmt = null;

        String insertSql = "insert into customer values (?, ?, ?, ?); "; // ? 는 value 로 대체되어야 하는 항목
        int ret = -1;

        try {
            con = DriverManager.getConnection(url, user, pwd);
            pstmt = con.prepareStatement(insertSql);
            pstmt.setInt(1, custId);
            pstmt.setString(2, name);
            pstmt.setString(3, address);
            pstmt.setString(4, phone);

            ret = pstmt.executeUpdate();

        }catch(SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                pstmt.close();
                con.close();
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }

        return ret;
    }

    static int updateCustomer(int custId, String address) {
        Connection con = null;
        PreparedStatement pstmt = null;

        String updateSql = "update customer set address = ? where custid = ?; "; // ? 는 value 로 대체되어야 하는 항목
        int ret = -1;

        try {
            con = DriverManager.getConnection(url, user, pwd);
            pstmt = con.prepareStatement(updateSql);
            pstmt.setString(1, address);
            pstmt.setInt(2, custId);

            ret = pstmt.executeUpdate();

        }catch(SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                pstmt.close();
                con.close();
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }

        return ret;
    }

    static List<CustomerDTO> listCustomer() {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<CustomerDTO> list = new ArrayList<>();
        String selectListSql = "select * from customer ";

        try {
            con = DriverManager.getConnection(url, user, pwd);
            pstmt = con.prepareStatement(selectListSql);

            rs = pstmt.executeQuery();
            while(rs.next()){
                // row 한개당 DTO를 만들어서 List를 보내야 함
                CustomerDTO dto = new CustomerDTO();
                dto.setCustId(rs.getInt("custid"));
                dto.setName(rs.getString("name"));
                dto.setAddress(rs.getString("address"));
                dto.setPhone(rs.getString("phone"));

                list.add(dto);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                rs.close();
                pstmt.close();
                con.close();
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    static CustomerDTO detailCustomer(int custId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        CustomerDTO dto = null;
        String selectDetailSql = "select * from customer where custid = ? ";

        try {
            con = DriverManager.getConnection(url, user, pwd);
            pstmt = con.prepareStatement(selectDetailSql);
            pstmt.setInt(1, custId);

            rs = pstmt.executeQuery();
            while(rs.next()){
                // row 한개당 DTO를 만들어서 List를 보내야 함
                dto = new CustomerDTO();
                dto.setCustId(rs.getInt("custid"));
                dto.setName(rs.getString("name"));
                dto.setAddress(rs.getString("address"));
                dto.setPhone(rs.getString("phone"));
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                rs.close();
                pstmt.close();
                con.close();
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return dto;
    }

    static int deleteCustomer(int custId) {
        Connection con = null;
        PreparedStatement pstmt = null;

        String deleteSql = "delete from customer where custid = ?; "; // ? 는 value 로 대체되어야 하는 항목
        int ret = -1;

        try {
            con = DriverManager.getConnection(url, user, pwd);
            pstmt = con.prepareStatement(deleteSql);
            pstmt.setInt(1, custId);

            ret = pstmt.executeUpdate();

        }catch(SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                pstmt.close();
                con.close();
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }

        return ret;
    }
}