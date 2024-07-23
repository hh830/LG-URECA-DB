package ch03.dao;

import ch03.dto.BookDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

// mapper : java method - sql
public interface BookDao {
    @Select("select bookid bookId, bookname bookName, publisher, price from book;")
    List<BookDto> listBook();
    @Select("select bookid bookId, bookname bookName, publisher, price from book where bookid = #{bookId}; ")

    BookDto detailBook(int bookId);
    @Insert("insert into book (bookid, bookname, publisher, price) values ( #{bookId}, #{bookName}, #{publisher}, #{price} ); ")
    int insertBook(BookDto bookDto);
    @Update("insert into book (bookid, bookname, publisher, price) values ( #{bookId}, #{bookName}, #{publisher}, #{price} ); ")
    int updateBook(BookDto bookDto);
    @Delete("delete from book where bookid = #{bookId}")
    int deleteBook(int bookId);
}
