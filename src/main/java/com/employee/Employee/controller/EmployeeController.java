package com.employee.Employee.controller;

import com.employee.Employee.dto.EmployeeDTO;
import com.employee.Employee.dto.UpdateAddress;
import com.employee.Employee.dto.UpdateEmployeeDTO;
import com.employee.Employee.service.AddressService;
import com.employee.Employee.serviceimplemention.AddressServiceImp;
import com.employee.Employee.service.EmployeeService;
import com.employee.Employee.serviceimplemention.EmployeeServiceImp;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

       private EmployeeService employeeService;
       private AddressService addressService;

       EmployeeController(EmployeeServiceImp employeeService, AddressServiceImp addressService){
           this.employeeService = employeeService;
           this.addressService = addressService;
       }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO employeeDto){
            return ResponseEntity.ok().body(employeeService.createEmployee(employeeDto));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getEmployees(
            @RequestParam(value = "designation", required = false) String designation,
            @RequestParam(value = "employeeId", required = false) String employeeId) {

        List<EmployeeDTO> employeeDTOS = employeeService.getEmployees(employeeId, designation);
        return ResponseEntity.ok().body(employeeDTOS);
    }

    @PutMapping
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody @Valid UpdateEmployeeDTO employeeDTO) {
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(employeeDTO.getEmployeeId(), employeeDTO.getEmployeeDTO());
        return ResponseEntity.ok().body(updatedEmployee);
    }

    @PutMapping("/addresses")
    public ResponseEntity<EmployeeDTO> addAddressToEmployee(@RequestBody @Valid UpdateAddress updateAddress){
        EmployeeDTO employeeDto = employeeService.addAddressToEmployee(updateAddress.getEmployeeId(), updateAddress.getAddressDTOList());
        return new ResponseEntity<>(employeeDto,HttpStatus.OK);
    }

    @PatchMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable String employeeId,
                                                      @RequestParam(required = false) String name,
                                                      @RequestParam(required = false) String designation,
                                                      @RequestParam(required = false) String mobileNumber) {
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(employeeId, name, designation, mobileNumber);
        return ResponseEntity.ok(updatedEmployee);
    }

@DeleteMapping
public String deleteEmployeeById(@RequestParam(required = false) String employeeId,
                                 @RequestParam(required = false) String mobileNumber) {
        return employeeService.deleteEmployee(employeeId, mobileNumber);
}

}
