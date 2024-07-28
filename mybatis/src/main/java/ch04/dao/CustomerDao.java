package ch04.dao;

import ch04.dto.CustomerDto;

import java.util.List;

public interface CustomerDao {
    List<CustomerDto> listCust();
    CustomerDto detailCust(int custId);
    int insertCust(CustomerDto customerDto);
    int updateCust(CustomerDto customerDto);
    int deleteCust(int custId);
}
