package com.employee.Employee.serviceimplemention;

import com.employee.Employee.Util.IdGenerator;
import com.employee.Employee.Util.Utility;
import com.employee.Employee.dto.AddressDTO;
import com.employee.Employee.dto.EmployeeDTO;
import com.employee.Employee.entities.Address;
import com.employee.Employee.entities.Employee;
import com.employee.Employee.exception.NotFoundException;
import com.employee.Employee.mapper.Mapper;
import com.employee.Employee.repository.AddressRepo;
import com.employee.Employee.repository.EmployeeRepo;
import com.employee.Employee.service.EmployeeService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.io.ObjectInputFilter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImp implements EmployeeService {

    private static final Logger logger = LogManager.getLogger(EmployeeServiceImp.class);

    private EmployeeRepo employeeRepo;
    private AddressRepo addressRepo;


    EmployeeServiceImp(AddressRepo addressRepo, EmployeeRepo employeeRepo){
        this.employeeRepo = employeeRepo;
        this.addressRepo = addressRepo;
    }

    private Employee employee;
    private EmployeeDTO employeeDto;
    private List<Employee> employees;
    private List<EmployeeDTO> employeeDTOS;
    private int count;


    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDto) {

            logger.info("started to creating employee into employee ");
            employee = Mapper.employeeDTOToEmployee(employeeDto);
            //String employeeId = Utility.generateId();
            String employeeId = IdGenerator.generateCustomId(employeeDto.getName());
            employee.setEmployeeId(employeeId);

            if (employee.getAddresses() != null) {
                employee.getAddresses().forEach(address -> {
                    if (address.getAddressId() == null || address.getAddressId().isEmpty()) {
                        address.setAddressId(IdGenerator.generateCustomId(address.getCity()));
                    }
                });
            }
             employee = employeeRepo.save(employee);
            if(employee == null){
                logger.error("Employee Not Saved into DB");
                throw new NotFoundException("Employee Not Saved into DB");
            }
            logger.info("employee is saved into the Data base with employeeId : "+employeeId);
            employeeDto = Mapper.employeeToEmployeeDTO(employee);
            return employeeDto;

    }

    @Override
    public List<EmployeeDTO> getEmployees(String employeeId,String designation) {
        logger.info("Started getting list of employees");
        if (employeeId == null && designation == null) {
            employees = employeeRepo.findAll();
        } else {
            employees = employeeRepo.findByEmployeeIdAndDesignation(employeeId, designation);
        }

        if (employees.isEmpty()) {
            logger.error("Employees not found");
            throw new NotFoundException("Employees not found");
        }
        logger.info("Employee list fetched successfully");
        return Mapper.employeeListToEmployeeDTOList(employees);
    }


    @Override
    public EmployeeDTO updateEmployee(String employeeId, EmployeeDTO employeeDto) {
        logger.info("stated updating employee into employee with employeeId : "+employeeId);

                employee = findEmployeeById(employeeId);

                    if (employeeDto.getAddresses() != null){
                        addAddressToEmployee(employeeId, employeeDto.getAddresses());
                    }
                    else {
                        if (employee.getAddresses() != null) {
                            List<AddressDTO> existingAddressDtos = Mapper.addressListToAddressDTOList(employee.getAddresses());
                            employeeDto.setAddresses(existingAddressDtos);
                        }
                    }

                employee = Mapper.employeeDTOToExsitingEmployee(employeeDto,employee);
                employee.setEmployeeId(employeeId);
                employee = employeeRepo.save(employee);
                if(employee == null){
                    logger.error("Employee is NOT updated into DB");
                    throw new NotFoundException("Employee is NOT updated into DB");
                }
                logger.info("employee is updated succesfullly !!!");
                employeeDto = Mapper.employeeToEmployeeDTO(employee);
                return employeeDto;
    }

    @Transactional
    @Modifying
    @Override
    public EmployeeDTO updateEmployee(String employeeId, String name, String designation, String mobileNumber) {
        employee = findEmployeeById(employeeId);
        if (Utility.isValidName(name)) {
            employee.setName(name);
        }
        if (Utility.isValidDesignation(designation)) {
            employee.setDesignation(designation);
        }
        if (Utility.isValidPhoneNumber(mobileNumber)) {
            employee.setMobileNumber(mobileNumber);
        }
        employeeRepo.save(employee);
        return Mapper.employeeToEmployeeDTO(employee);
    }

    @Transactional
    @Modifying
    @Override
    public String deleteEmployee(String employeeId, String mobileNumber){
        if (employeeId != null) {
            employee = findEmployeeById(employeeId);
                employeeRepo.delete(employee);
                count++;
        }

        if (Utility.isValidPhoneNumber(mobileNumber)) {
            count += employeeRepo.deleteByMobileNumber(mobileNumber);
        }

        return "deleted employee sucessfully : "+count;
    }

    @Override
    @Transactional
    public EmployeeDTO addAddressToEmployee(String employeeId, List<AddressDTO> addressDTOS) {
        logger.info("Starting to adding address into employee with employeeId : "+employeeId);

            Employee employee = findEmployeeById(employeeId);
            List<Address> addresses = addressDTOS.stream().map(addressDto -> {
                Address address = Mapper.addressDTOTOAddress(addressDto);
                address.setAddressId(IdGenerator.generateCustomId(address.getCity()));
                return address;
            }).collect(Collectors.toList());
            logger.info("addresses from dto are converted into the addresses of entity successfully !!! ");
            employee.getAddresses().addAll(addresses);

            employee = employeeRepo.save(employee);
            logger.info("address is added sucessfully into employee !!!");
            return Mapper.employeeToEmployeeDTO(employee);

    }


    public Employee findEmployeeById(String employeeId){
        employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Employee with id " + employeeId + " is not found"));
        logger.info("employee with employeeId : "+employeeId+" is found successfully !!!");
        return employee;
    }

}
