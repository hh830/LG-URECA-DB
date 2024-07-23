package jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// 개별 메소를 파라미터, 리턴을 추가해서 main 협업 메소드 내 하드코딩 제거
// Statement -> PreparedStatement : SQL 문자열 쓰기 힘들어서 도입 - ? 쓸 수 있게 함
// select query 처리에 dto 적용
// + 전체 dto 적용 - main에서도
// Utility class DBManager
// Collection 객체 중요
public class Test4 {


    public static void main(String[] args){
        int ret = -1;

        CustomerDTO dto = new CustomerDTO(6, "손흥민", "영국 토트넘", "010-2222-4444");
        ret = insertCustomer(dto);
        System.out.println(ret);

        CustomerDTO dto1 = new CustomerDTO(6, "손흥민", "한국 서울", "010-2222-4444");
        ret = updateCustomer(dto1);
        System.out.println(ret);

//        List<CustomerDTO> list = listCustomer();
//        for(CustomerDTO dto : list){
//            System.out.println(dto);
//        }


        ret = deleteCustomer(6);
        System.out.println(dto);
    }

    static int insertCustomer(CustomerDTO dto) {
        Connection con = null;
        PreparedStatement pstmt = null;

        String insertSql = "insert into customer values (?, ?, ?, ?); "; // ? 는 value 로 대체되어야 하는 항목
        int ret = -1;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(insertSql);
            pstmt.setInt(1, dto.getCustId());
            pstmt.setString(2, dto.getName());
            pstmt.setString(3, dto.getAddress());
            pstmt.setString(4, dto.getPhone());

            ret = pstmt.executeUpdate();

        }catch(SQLException e) {
            e.printStackTrace();
        }finally {
            DBManager.releaseConnection(pstmt, con);
        }

        return ret;
    }

    static int updateCustomer(CustomerDTO dto) {
        Connection con = null;
        PreparedStatement pstmt = null;

        String updateSql = "update customer set address = ? where custid = ?; "; // ? 는 value 로 대체되어야 하는 항목
        int ret = -1;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(updateSql);
            pstmt.setString(1, dto.getAddress());
            pstmt.setInt(2, dto.getCustId());

            ret = pstmt.executeUpdate();

        }catch(SQLException e) {
            e.printStackTrace();
        }finally {
            DBManager.releaseConnection(pstmt, con);
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
            con = DBManager.getConnection();
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
            DBManager.releaseConnection(rs, pstmt, con);
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
            con = DBManager.getConnection();
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
            DBManager.releaseConnection(rs, pstmt, con);
        }
        return dto;
    }

    static int deleteCustomer(int custId) {
        Connection con = null;
        PreparedStatement pstmt = null;

        String deleteSql = "delete from customer where custid = ?; "; // ? 는 value 로 대체되어야 하는 항목
        int ret = -1;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(deleteSql);
            pstmt.setInt(1, custId);

            ret = pstmt.executeUpdate();

        }catch(SQLException e) {
            e.printStackTrace();
        }finally {
            DBManager.releaseConnection(pstmt, con);
        }

        return ret;
    }

    // AutoCloseable
    static int deleteCustomerAutoCloseable(int custId) {
        String deleteSql = "delete from customer where custid = ?; "; // ? 는 value 로 대체되어야 하는 항목
        int ret = -1;
        // try with resources 블록 안에서 선언 생성됨. AutoCloseable 객체는 자동으로 close() 호출된다.
        try (
                Connection con = DBManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(deleteSql);
                // 자동으로 close가 됨 / 대신 AutoCloseable을 implements한 자원이어야 함(확인)
                ){
            pstmt.setInt(1, custId);

            ret = pstmt.executeUpdate();

        }catch(SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }
}