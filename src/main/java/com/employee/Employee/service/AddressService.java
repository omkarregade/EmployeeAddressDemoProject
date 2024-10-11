package com.employee.Employee.service;

import com.employee.Employee.dto.AddressDTO;
import com.employee.Employee.dto.EmployeeDTO;

import java.util.List;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO);
    List<AddressDTO> getAddress(String addressId);
    AddressDTO updateAddress(String addressId, AddressDTO addressDTO);
    String deleteAddress(String addressId);

    //custom :
    public AddressDTO updateAddress(String addressId, String state, String country, String pinCode, String city);

}
