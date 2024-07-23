package ch05;


import ch05.dao.CustomerDao;
import ch05.dto.CustomerDto;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

// config : xml
// sql(mapper) : java
public class Test {
    public static void main(String[] args) throws IOException {
        // Mybatis 설정 파일을 읽어온다.
        Reader reader = Resources.getResourceAsReader("config/mybatis-config-2.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sqlSessionFactory.openSession();

        // SQL (mapper) + Java
        // Java의 어떤 메소드가 호출되면 mapper의 어떤 sql이 수행되는지 연결
        // book-mapper.xml에서 mapper namespace에 적기
        CustomerDao customerDao = session.getMapper(CustomerDao.class);

        // 목록
        {
            List<CustomerDto> listCust = customerDao.listCust();
            for(CustomerDto customerDto : listCust){
                System.out.println(customerDto);
            }
        }

        // 상세
//        {
//            CustomerDto customerDto = customerDao.detailCust(1);
//            System.out.println(customerDto);
//        }

        // 등록
//        {
//            CustomerDto customerDto = new CustomerDto(7, "장현희", "경기도", "010-1111-2222");
//            int ret = customerDao.insertCust(customerDto);
//            System.out.println(ret);
//
//            session.commit();
//        }

        //  수정
//        {
//            CustomerDto customerDto = new CustomerDto(7, "장현희", "한국", "010-2222-3333");
//            int ret = customerDao.updateCust(customerDto);
//            System.out.println(ret);
//
//            session.commit();
//        }

        // 삭제
//        {
//            int ret = customerDao.deleteCust(7);
//            System.out.println(ret);
//        }

        // 다 끝나면 close
        session.close();

    }
}