package com.employee.Employee.controller;

import com.employee.Employee.dto.AddressDto;
import com.employee.Employee.dto.EmployeeDto;
import com.employee.Employee.exception.*;
import com.employee.Employee.service.AddressServiceImp;
import com.employee.Employee.service.EmployeeServiceImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

   private EmployeeServiceImp employeeServiceImp;

   @Autowired
   EmployeeController(EmployeeServiceImp employeeServiceImp){
       this.employeeServiceImp = employeeServiceImp;
   }

    @Autowired
    private AddressServiceImp addressServiceImp;

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto employeeDto){
            return ResponseEntity.ok().body(employeeServiceImp.createEmployee(employeeDto));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployee(){
        List<EmployeeDto> employeeDtos = employeeServiceImp.getAllEmployee();
        return ResponseEntity.ok().body(employeeDtos);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable String employeeId){
        return ResponseEntity.ok().body(employeeServiceImp.getEmployeeById(employeeId));
    }

    @GetMapping("/designation/{designation}")
    public ResponseEntity<List<EmployeeDto>> getEmployeeByDesignation(@PathVariable  String designation){
       List<EmployeeDto> employeeDtos = employeeServiceImp.getEmployeeByDesignation(designation);
        return ResponseEntity.ok().body(employeeDtos);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable String employeeId,@Valid @RequestBody EmployeeDto employeeDto) {
        EmployeeDto updatedEmployee = employeeServiceImp.updateEmployee(employeeId, employeeDto);
        return ResponseEntity.ok().body(updatedEmployee);
    }

    //custom
    @PutMapping("/address/{employeeId}")
    public ResponseEntity<EmployeeDto> addAddressToEmployee(@PathVariable String employeeId,@Valid @RequestBody List<AddressDto> addressDtos){
       EmployeeDto employeeDto = addressServiceImp.addAddressToEmployee(employeeId,addressDtos);
       return new ResponseEntity<>(employeeDto,HttpStatus.OK);
    }

    @PatchMapping("/name/{employeeId}/{name}")
    public ResponseEntity<EmployeeDto> updateEmployeeName(@PathVariable String employeeId,
                                                          @PathVariable String name) {

        EmployeeDto updatedEmployee = employeeServiceImp.updateEmployeeName(employeeId, name);
        return ResponseEntity.ok(updatedEmployee);
    }

    @PatchMapping("/designation/{employeeId}/{designation}")
    public ResponseEntity<EmployeeDto> updateEmployeeDesignation(@PathVariable String employeeId,
                                                                 @PathVariable String designation) {
        EmployeeDto updatedEmployee = employeeServiceImp.updateEmployeeDesignation(employeeId, designation);
        return ResponseEntity.ok(updatedEmployee);
    }

    @PatchMapping("/mobile-number/{employeeId}/{mobileNumber}")
    public ResponseEntity<EmployeeDto> updateEmployeeMobileNumber(@PathVariable String employeeId,
                                                                  @PathVariable String mobileNumber) {
        EmployeeDto updatedEmployee = employeeServiceImp.updateEmployeeMobileNumber(employeeId, mobileNumber);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable String employeeId){
        return new ResponseEntity<>(employeeServiceImp.deleteEmployee(employeeId),HttpStatus.OK);
    }

    @DeleteMapping("/number/{mobileNumber}")
    public ResponseEntity<String> deleteEmployeeByMobileNumber(@PathVariable String mobileNumber) {
        return new ResponseEntity<>(employeeServiceImp.deleteEmployeeByMobileNumber(mobileNumber), HttpStatus.OK);
    }

    @DeleteMapping("/designation/{designation}")
    public ResponseEntity<String> deleteEmployeeByDesignation(@PathVariable String designation) {
        return new ResponseEntity<>(employeeServiceImp.deleteEmployeeByDesignation(designation), HttpStatus.OK);
    }

}
