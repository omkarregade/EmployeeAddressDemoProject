package com.employee.Employee.service;

import com.employee.Employee.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto createEmployee(EmployeeDto employeeDto);
    List<EmployeeDto> getAllEmployee();
    EmployeeDto getEmployeeById(String employeeId);
    EmployeeDto updateEmployee(String employeeId,EmployeeDto employeeDto);
    String deleteEmployee(String employeeId);

    List<EmployeeDto> getEmployeeByDesignation(String designation);
    String deleteEmployeeByMobileNumber(String mobileNumber);
    String deleteEmployeeByDesignation(String designation);
    EmployeeDto updateEmployeeName(String employeeId, String name);
    EmployeeDto updateEmployeeDesignation(String employeeId, String designation);
    EmployeeDto updateEmployeeMobileNumber(String employeeId, String mobileNumber);
}
