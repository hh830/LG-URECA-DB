package ch03;

import ch03.config.MyBatisConfig;
import ch03.dao.BookDao;
import ch03.dto.BookDto;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

// config : java
// sql(mapper) : java
public class Test {
    public static void main(String[] args) throws IOException {
        // java 설정 이용.
        SqlSessionFactory sqlSessionFactory = new MyBatisConfig().getSqlSessionFactory();
        SqlSession session = sqlSessionFactory.openSession();

        // SQL (mapper) + Java
        // Java의 어떤 메소드가 호출되면 mapper의 어떤 sql이 수행되는지 연결
        // book-mapper.xml에서 mapper namespace에 적기
        BookDao bookDao = session.getMapper(BookDao.class); // BookDao 내용을 session으로 스캐닝, book-mapper.xml과 bookDao가 연결

        // 목록
        {
            List<BookDto> bookList = bookDao.listBook();
            for(BookDto bookDto : bookList){
                System.out.println(bookDto);
            }
        }

        // 상세
//        {
//            BookDto bookDto = bookDao.detailBook(1);
//            System.out.println(bookDto);
//        }

        // 등록
//        {
//            BookDto bookDto = new BookDto(11, "11번째 책", "uplus", 5000);
//            int ret = bookDao.insertBook(bookDto);
//            System.out.println(ret);
//            // 세션을 얻고 insert, update 등 하면 커밋 해야됨
//            session.commit();
//        }

        // 수정
//        {
//            BookDto bookDto = new BookDto(11, "11번째 책 수정", "uplus", 5000);
//            int ret = bookDao.updateBook(bookDto);
//            System.out.println(ret);
//            // 세션을 얻고 insert, update 등 하면 커밋 해야됨
//            session.commit();
//        }

        // 삭제
//        {
//            int ret = bookDao.deleteBook(11);
//            System.out.println(ret);
//            session.commit();
//        }

        // 다 끝나면 close
        session.close();
    }
}