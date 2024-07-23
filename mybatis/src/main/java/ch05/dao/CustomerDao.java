package ch05.dao;

import ch05.dto.CustomerDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CustomerDao {
    @Select("select * from customer; ")
    List<CustomerDto> listCust();
    @Select("select * from customer where custid = #{custId}; ")
    CustomerDto detailCust(int custId);
    @Insert("insert into customer (custid, name, address, phone) values ( #{custId}, #{name}, #{address}, #{phone}; ")
    int insertCust(CustomerDto customerDto);
    @Update("update customer set name = #{name}, address = #{address}, phone = #{phone} where custid = #{custId}; ")
    int updateCust(CustomerDto customerDto);
    @Delete("delete from customer where custid = #{custId}; ")
    int deleteCust(int custId);

}
