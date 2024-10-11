package com.employee.Employee.mapper;

import com.employee.Employee.dto.AddressDTO;
import com.employee.Employee.dto.EmployeeDTO;
import com.employee.Employee.entities.Address;
import com.employee.Employee.entities.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    private static final Logger logger = LogManager.getLogger(Mapper.class);


    private static ModelMapper modelMapper = new ModelMapper();
    private static Employee employee;
    private static EmployeeDTO employeeDto;
    private static Address address;
    private static AddressDTO addressDto;
    private static List<Employee> employees;
    private static List<EmployeeDTO> employeeDTOS;
    private static List<AddressDTO> addressDTOList;

    public static EmployeeDTO employeeToEmployeeDTO(Employee employee){
      try {
          employeeDto =  modelMapper.map(employee, EmployeeDTO.class);
      }
      catch (MappingException exception){
          logger.error("Getting exception when trying to map from employee to employeeDTO"+exception.getMessage());
      }
      return employeeDto;
    }
    public static Employee employeeDTOToEmployee(EmployeeDTO employeeDto){
        try {
            employee = modelMapper.map(employeeDto,Employee.class);
        }
        catch (MappingException exception){
            logger.error("Getting exception when trying to map from employeeDTO to employee"+exception.getMessage());
        }
        return employee;
    }
    public static Employee employeeDTOToExsitingEmployee(EmployeeDTO employeeDto, Employee employee){
        try {
            modelMapper.map(employeeDto,employee);
        }
        catch (MappingException exception){
            logger.error("Getting exception when trying to map from employee to employeeDTO"+exception.getMessage());
        }
        return employee;
    }
    public static Address addressDTOTOAddress(AddressDTO addressDto){
       try {
           address = modelMapper.map(addressDto,Address.class);
       }
       catch (MappingException exception){
           logger.error("Getting exception when trying to map from employee to employeeDTO"+exception.getMessage());
       }
        return address;
    }
    public static AddressDTO addressToAddressDTO(Address address){
        try {
            addressDto = modelMapper.map(address,AddressDTO.class);
        }
        catch (MappingException exception){
            logger.error("Getting exception when trying to map from employee to employeeDTO"+exception.getMessage());
        }
        return addressDto;
    }
    public static List<EmployeeDTO> employeeListToEmployeeDTOList(List<Employee> employees){
        employeeDTOS = employees.stream()
                .map(employee -> Mapper.employeeToEmployeeDTO(employee))
                .collect(Collectors.toList());
        return employeeDTOS;
    }
    public static List<AddressDTO> addressListToAddressDTOList(List<Address> addresses){
        addressDTOList = addresses.stream()
                .map(address1 -> Mapper.addressToAddressDTO(address1)).toList();
        return addressDTOList;
    }
}
