package com.employee.Employee.dto;


import lombok.*;
import jakarta.validation.constraints.*;

@Data
public class AddressDto {

    private String addressId;

    @NotBlank(message = "City is mandatory")
    @Size(max = 30, message = "City cannot exceed 30 characters")
    private String city;

    @NotBlank(message = "State is mandatory")
    @Size(max = 30, message = "State cannot exceed 30 characters")
    private String state;

    @NotBlank(message = "Country is mandatory")
    @Size(max = 30, message = "Country cannot exceed 30 characters")
    private String country;

    @NotBlank(message = "Pin code is mandatory")
    @Pattern(regexp = "\\d{6}", message = "Pin code must be 6 digits")
    private String pinCode;

    //@NotNull(message = "Employee cannot be null")
    private String employeeId;
}

