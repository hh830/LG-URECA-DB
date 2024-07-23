package app_book_common.dao;

import app_book_common.dto.Book;
import jdbc.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// book table에 대한 crud
public class BookDao {
    public int insertBook(Book book){
        int ret = -1;
        String sql = "insert into book values (?, ?, ?, ?); ";

        Connection con = null;
        PreparedStatement preparedStatement = null;

        try{
            con = DBManager.getConnection();
            preparedStatement = con.prepareStatement(sql);

            preparedStatement.setInt(1, book.getBookId());
            preparedStatement.setString(2, book.getBookName());
            preparedStatement.setString(3, book.getPublisher());
            preparedStatement.setInt(4, book.getPrice());

            ret = preparedStatement.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(preparedStatement, con);
        }

        return ret;
    }

    public int updateBook(Book book){
        int ret = -1;
        String sql = "update book set bookname = ?, publisher = ?, price = ? where bookid = ?; ";

        Connection con = null;
        PreparedStatement preparedStatement = null;

        try{
            con = DBManager.getConnection();
            preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, book.getBookName());
            preparedStatement.setString(2, book.getPublisher());
            preparedStatement.setInt(3, book.getPrice());
            preparedStatement.setInt(4, book.getBookId());

            ret = preparedStatement.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(preparedStatement, con);
        }

        return ret;
    }

    public int deleteBook(int bookId){
        int ret = -1;
        String sql = "delete from book where bookid = ?; ";

        Connection con = null;
        PreparedStatement preparedStatement = null;

        try{
            con = DBManager.getConnection();
            preparedStatement = con.prepareStatement(sql);

            preparedStatement.setInt(1, bookId);

            ret = preparedStatement.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(preparedStatement, con);
        }

        return ret;
    }

    public List<Book> listBook(){
        List<Book> list = new ArrayList<>();
        String sql = "select * from book; ";

        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try{
            con = DBManager.getConnection();
            preparedStatement = con.prepareStatement(sql);

            rs = preparedStatement.executeQuery();
            while(rs.next()){
                Book book = new Book();
                book.setBookId(rs.getInt("bookid"));
                book.setBookName(rs.getString("bookname"));
                book.setPublisher(rs.getString("publisher"));
                book.setPrice(rs.getInt("price"));
                list.add(book);
            }

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(preparedStatement, con);
        }

        return list;
    }

    public List<Book> listBook(String searchWord){
        List<Book> list = new ArrayList<>();
        String sql = "select * from book where bookname like ?; "; // % 사용 x

        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try{
            con = DBManager.getConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, "%" + searchWord + "%");

            rs = preparedStatement.executeQuery();

            while(rs.next()){
                Book book = new Book();
                book.setBookId(rs.getInt("bookid"));
                book.setBookName(rs.getString("bookname"));
                book.setPublisher(rs.getString("publisher"));
                book.setPrice(rs.getInt("price"));
                list.add(book);
            }

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(preparedStatement, con);
        }

        return list;
    }

    public Book detailBook(int bookId){
        Book book = null;
        String sql = "select * from book where bookid = ? ; ";

        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try{
            con = DBManager.getConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, bookId);

            rs = preparedStatement.executeQuery();
            if(rs.next()){
                book = new Book();
                book.setBookId(rs.getInt("bookid"));
                book.setBookName(rs.getString("bookname"));
                book.setPublisher(rs.getString("publisher"));
                book.setPrice(rs.getInt("price"));
            }

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(preparedStatement, con);
        }

        return book;
    }
}
