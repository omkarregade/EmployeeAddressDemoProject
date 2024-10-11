package com.employee.Employee.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateEmployeeDTO {
    @NotNull(message = "ID should not be null")
    private String employeeId;

    @NotNull(message = "Employee Details must be needed")
    @Valid
    private EmployeeDTO employeeDTO;

}
