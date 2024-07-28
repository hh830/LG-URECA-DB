package ch04;

import ch04.dao.CustomerDao;
import ch04.dto.CustomerDto;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

// config-xml, sql-xml
public class Test {
    public static void main(String[] args) throws IOException {
        Reader reader = Resources.getResourceAsReader("config/mybatis-config-4.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sqlSessionFactory.openSession();

        CustomerDao customerDao = session.getMapper(CustomerDao.class);

        // 목록
        {
            List<CustomerDto> custList = customerDao.listCust();
            for(CustomerDto customerDto : custList){
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
