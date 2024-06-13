package com.employee.Employee.repository;

import com.employee.Employee.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,String> {
    List<Employee> getEmployeeByDesignation(String designation);
    Employee findByMobileNumber(String mobileNumber);
    String deleteByMobileNumber(String mobileNumber);
    List<Employee> findByDesignation(String designation);
}
