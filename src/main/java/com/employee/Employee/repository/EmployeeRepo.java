package com.employee.Employee.repository;

import com.employee.Employee.entities.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,String> {
    int deleteByMobileNumber(String mobileNumber);

    @Query("DELETE FROM Employee e WHERE e.mobileNumber = :mobileNumber AND e.designation = :designation")
    int deleteByMobileNumberAndDesignation(@Param("mobileNumber") String mobileNumber, @Param("designation") String designation);

    @Query("SELECT e FROM Employee e WHERE " +
            "(:employeeId IS NULL OR e.id = :employeeId) AND " +
            "(:designation IS NULL OR e.designation = :designation)")
    List<Employee> findByEmployeeIdAndDesignation(@Param("employeeId") String employeeId,
                                                  @Param("designation") String designation);

}
