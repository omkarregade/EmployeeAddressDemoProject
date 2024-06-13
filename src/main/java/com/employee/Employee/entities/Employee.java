package com.employee.Employee.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Employee {
        @Id
        private String employeeId;

        @Column(name = "Employee_Name")
        private String name;

        @Column(name = "Employee_Designation")
        private String designation;

        @Column(name = "Mobile_Number")
        private String mobileNumber;

        @Column(name = "Date_Of_Birth")
        private LocalDate dateOfBirth;

        @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
        //@JsonManagedReference
        private List<Address> addresses = new ArrayList<>();

}
