package com.employee.Employee.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
public class Address {
    @Id
    private String addressId;

    @Column(name = "City")
    private String city;

    @Column(name = "State")
    private String state;

    @Column(name = "Country")
    private String country;
    private String pinCode;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    //@JsonBackReference
    private Employee employee;
}
