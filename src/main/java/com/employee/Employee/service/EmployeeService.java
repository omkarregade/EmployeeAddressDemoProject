package com.employee.Employee.service;

import com.employee.Employee.dto.AddressDTO;
import com.employee.Employee.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    EmployeeDTO createEmployee(EmployeeDTO employeeDto);
    List<EmployeeDTO> getEmployees(String employeeId, String designation);
    EmployeeDTO updateEmployee(String employeeId, EmployeeDTO employeeDto);

    public String deleteEmployee(String employeeId, String mobileNumber);

    EmployeeDTO updateEmployee(String employeeId, String name, String designation, String mobileNumber);
    EmployeeDTO addAddressToEmployee(String employeeId, List<AddressDTO> addressDTOS);
}
