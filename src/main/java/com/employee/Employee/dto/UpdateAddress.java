package com.employee.Employee.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UpdateAddress {

    @NotNull(message = "ID should not be null")
    private String employeeId;

    @NotNull(message = "Address Details must be needed")
    @Valid
    private List<AddressDTO> addressDTOList;

}
