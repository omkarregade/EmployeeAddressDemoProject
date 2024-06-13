package com.employee.Employee.service;

import com.employee.Employee.dto.AddressDto;
import com.employee.Employee.dto.EmployeeDto;

import java.util.List;

public interface AddressService {
    AddressDto createAddress(AddressDto addressDTO);
    AddressDto getAddressById(String addressId);
    List<AddressDto> getAllAddresses();
    AddressDto updateAddress(String addressId, AddressDto addressDTO);
    String deleteAddress(String addressId);

    //custom :
    EmployeeDto addAddressToEmployee(String employeeId, List<AddressDto> addressDtos);
    AddressDto addEmployeeToAddress(String addressId, EmployeeDto employeeDto);

    AddressDto updateAddressByPinCode(String addressId, String pinCode);
    AddressDto updateAddressByCountry(String addressId, String country);
    AddressDto updateAddressByState(String addressId, String state);
    AddressDto updateAddressByCity(String addressId, String city);

    String deleteAddressByState(String state);
    String deleteAddressByCity(String city);
}
