package com.employee.Employee.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

@Data
public class EmployeeDTO {
    private String employeeId;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Designation is mandatory")
    @Size(max = 50, message = "Designation cannot exceed 50 characters")
    private String designation;

    @NotBlank(message = "Mobile number is mandatory")
    @Pattern(regexp = "\\d{10}", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotNull(message = "Date of birth cannot be null")
    @Past(message = "Date of birth must be a past date")
    //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;



    //@NotNull(message = "Addresses cannot be null")
    //@Size(min = 1, message = "At least one address is required")
    private List<AddressDTO> addresses;
}