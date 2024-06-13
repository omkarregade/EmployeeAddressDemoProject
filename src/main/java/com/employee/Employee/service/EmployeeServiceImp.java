package com.employee.Employee.service;

import com.employee.Employee.dto.EmployeeDto;
import com.employee.Employee.entities.Employee;
import com.employee.Employee.exception.NoResourceFoundException;
import com.employee.Employee.exception.NotFoundException;
import com.employee.Employee.repository.AddressRepo;
import com.employee.Employee.repository.EmployeeRepo;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImp implements EmployeeService{

    private static final Logger logger = LogManager.getLogger(EmployeeServiceImp.class);

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private AddressRepo addressRepo;


    @Autowired
    private ModelMapper modelMapper;


    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
            logger.info("started to creating employee into employee ");
            Employee employee = modelMapper.map(employeeDto, Employee.class);
            String employeeId = UUID.randomUUID().toString();
            employee.setEmployeeId(employeeId);

            if (employee.getAddresses() != null) {
                employee.getAddresses().forEach(address -> {
                    // Generating addressId
                    if (address.getAddressId() == null || address.getAddressId().isEmpty()) {
                        address.setAddressId(UUID.randomUUID().toString());
                    }
                    address.setEmployee(employee);
                });
            }
            employeeRepo.save(employee);
            logger.info("employee is saved into the Data base with employeeId : "+employeeId);
            return modelMapper.map(employee,EmployeeDto.class);
    }

    @Override
    public List<EmployeeDto> getAllEmployee() {
        logger.info("stated getting list of employees into employee ");
        List<EmployeeDto> employeeDtos = employeeRepo.findAll().stream().map(employee -> modelMapper.map(employee,EmployeeDto.class)).collect(Collectors.toList());
        if(employeeDtos.isEmpty()){
            logger.error("No employee found in the database");
            throw new NotFoundException("No employee found in the database");
        }
        logger.info("employee list is fetched successfully !!!!");
        return employeeDtos;
    }

    @Override
    public EmployeeDto getEmployeeById(String employeeId) {
        logger.info("stated getting employee into employee with employeeId : "+employeeId);
        try{
            Employee employee = employeeRepo.findById(employeeId).orElseThrow(()-> new NotFoundException("Employee with id "+employeeId+" is Not Found "));
            logger.info("employee is fetched successfully with employeeId : "+employeeId);
            return modelMapper.map(employee,EmployeeDto.class);
        }
        catch (Exception ex) {
            logger.error("An error occurred while getting employee into employee with id {}: {}", employeeId, ex.getMessage());
            throw ex;
        }
    }

    @Override
    public List<EmployeeDto> getEmployeeByDesignation(String designation) {
        logger.info("stated getting list of employeees by designation : "+designation+" into employee");
        List<EmployeeDto> employeeDtos = employeeRepo.getEmployeeByDesignation(designation).stream().map(employee -> modelMapper.map(employee,EmployeeDto.class)).collect(Collectors.toUnmodifiableList());
        if(employeeDtos.isEmpty()){
            logger.error("No employee found with designation : "+designation+" in the database");
            throw new NotFoundException("No employee found with designation : "+designation+" in the database");
        }
        logger.info("employee list is fetched successfully !!!!");
        return employeeDtos;
    }


    @Override
    public EmployeeDto updateEmployee(String employeeId, EmployeeDto employeeDto) {
        logger.info("stated updating employee into employee with employeeId : "+employeeId);
            try{
                Employee existingEmployee = employeeRepo.findById(employeeId)
                        .orElseThrow(() -> new NotFoundException("Employee with id " + employeeId + " is not found"));
                logger.info("employee with employeeId : "+employeeId+" is found successfully !!!");
                modelMapper.map(employeeDto, existingEmployee);
                existingEmployee.setEmployeeId(employeeId);

                Employee updatedEmployee = employeeRepo.save(existingEmployee);
                logger.info("employee is updated succesfullly !!!");
                return modelMapper.map(updatedEmployee, EmployeeDto.class);
            }
            catch (Exception ex) {
                logger.error("An error occurred while updating employee into employee with id {}: {}", employeeId, ex.getMessage());
                throw ex;
            }
    }

    @Override
    @Transactional
    public EmployeeDto updateEmployeeName(String employeeId, String name) {
        logger.info("stated updating name of employee with employeeId : "+employeeId);
        if (!isValidDesignation(name) || name == null) {
            logger.error("name", "Invalid name provided: " + name);
            throw new NoResourceFoundException("name", "Invalid name provided: " + name);
        }
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee with id " + employeeId + " not found"));
        logger.info("employee with employeeId : "+employeeId+" is found successfully !!!");
        employee.setName(name);
        Employee updatedEmployee = employeeRepo.save(employee);
        logger.info("name is updated into employee of employeeId :"+employeeId);
        return modelMapper.map(employee,EmployeeDto.class);
    }

    @Override
    @Transactional
    public EmployeeDto updateEmployeeDesignation(String employeeId, String designation) {
        logger.info("stated updating designation of employee with employeeId : "+employeeId);
        if (!isValidDesignation(designation) || designation == null) {
            logger.error("designation", "Invalid designation provided: " + designation);
            throw new NoResourceFoundException("designation", "Invalid designation provided: " + designation);
        }
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee with id " + employeeId + " not found"));
        logger.info("employee with employeeId : "+employeeId+" is found successfully !!!");
        employee.setDesignation(designation);
        Employee updatedEmployee = employeeRepo.save(employee);
        logger.info("designation is updated into employee of employeeId :"+employeeId);
        return modelMapper.map(employee,EmployeeDto.class);
    }

    @Override
    @Transactional
    public EmployeeDto updateEmployeeMobileNumber(String employeeId, String mobileNumber) {
        logger.info("stated updating mobile number of employee with employeeId : "+employeeId);
        if (!isValidPhoneNumber(mobileNumber)) {
            logger.error("phoneNumber", "Invalid phone number provided: " + mobileNumber);
            throw new NoResourceFoundException("phoneNumber", "Invalid phone number provided: " + mobileNumber);
        }
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee with id " + employeeId + " not found"));
        logger.info("employee with employeeId : "+employeeId+" is found successfully !!!");
        employee.setMobileNumber(mobileNumber);
        Employee updatedEmployee = employeeRepo.save(employee);
        logger.info("mobile number is updated into employee of employeeId :"+employeeId);
        return modelMapper.map(employee,EmployeeDto.class);
    }

    @Override
    public String deleteEmployee(String employeeId) {
        logger.info("stated deleting employee with employeeId : "+employeeId);
        Employee employee = employeeRepo.findById(employeeId).orElseThrow(()-> new NotFoundException("Employee with id "+employeeId+" is Not Found "));
        logger.info("employee with employeeId : "+employeeId+" is found successfully !!!");
        employeeRepo.delete(employee);
        return "Employee deleted successfully !!!";
    }


    @Override
    @Transactional
    public String deleteEmployeeByMobileNumber(String mobileNumber) {
        Employee employee = employeeRepo.findByMobileNumber(mobileNumber);
        if(employee == null){
            logger.error("Employee with phone number "+mobileNumber+" is Not Found ");
           throw new NotFoundException("Employee with phone number "+mobileNumber+" is Not Found ");
        }
        employeeRepo.deleteByMobileNumber(mobileNumber);
        return "Employee with phone number "+mobileNumber+" is Deleted Successfully !!!";
    }

    @Override
    @Transactional
    public String deleteEmployeeByDesignation(String designation) {
        List<Employee> employees = employeeRepo.findByDesignation(designation);
        if (employees.isEmpty()) {
            logger.error("No employees with designation " + designation + " found");
            throw new NotFoundException("No employees with designation " + designation + " found");
        }
        employeeRepo.deleteAll(employees);
        return "Employees with designation " + designation + " are deleted successfully";
    }

    private boolean isValidName(String name) {
        return name != null && name.length() <= 20 &&name.matches("[a-zA-Z]+") && !name.trim().isEmpty();
    }
    private boolean isValidDesignation(String designation) {
        return designation != null && designation.length() <= 20 && designation.matches("[a-zA-Z ]+") && !designation.trim().isEmpty();
    }
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("\\d{10}");
    }
}
