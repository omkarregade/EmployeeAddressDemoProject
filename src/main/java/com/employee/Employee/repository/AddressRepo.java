package com.employee.Employee.repository;

import com.employee.Employee.entities.Address;
import com.employee.Employee.entities.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepo extends JpaRepository<Address,String> {
    int deleteByCity(String city);
    int deleteByState(String state);

    void deleteByEmployeeId(String employeeId);

}
