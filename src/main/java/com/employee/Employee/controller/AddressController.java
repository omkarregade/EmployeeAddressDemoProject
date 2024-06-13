package com.employee.Employee.controller;

import com.employee.Employee.dto.AddressDto;
import com.employee.Employee.dto.EmployeeDto;
import com.employee.Employee.service.AddressServiceImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressServiceImp addressServiceImp;

    @PostMapping
    public ResponseEntity<AddressDto> createAddress(@Valid @RequestBody AddressDto addressDTO) {
        AddressDto createdAddress = addressServiceImp.createAddress(addressDTO);
        return ResponseEntity.ok(createdAddress);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable String addressId) {
        AddressDto address = addressServiceImp.getAddressById(addressId);
        return ResponseEntity.ok(address);
    }

    @GetMapping
    public ResponseEntity<List<AddressDto>> getAllAddresses() {
        return ResponseEntity.ok(addressServiceImp.getAllAddresses());
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable String addressId, @Valid @RequestBody AddressDto addressDTO) {
        AddressDto updatedAddress = addressServiceImp.updateAddress(addressId, addressDTO);
        return ResponseEntity.ok(updatedAddress);
    }

    @PutMapping("/employee/{addressId}")
    public ResponseEntity<AddressDto> addEmployeeToAddress( @PathVariable String addressId, @RequestBody @Valid EmployeeDto employeeDto) {
        AddressDto updatedAddress = addressServiceImp.addEmployeeToAddress(addressId, employeeDto);
        return ResponseEntity.ok(updatedAddress);
    }

    @PatchMapping("/{addressId}/city")
    public ResponseEntity<AddressDto> updateCity(@PathVariable String addressId,
                                                 @RequestParam String city) {
        AddressDto updatedAddress = addressServiceImp.updateAddressByCity(addressId, city);
        return ResponseEntity.ok(updatedAddress);
    }

    @PatchMapping("/{addressId}/state")
    public ResponseEntity<AddressDto> updateState(@PathVariable String addressId,
                                                  @RequestParam String state) {
        AddressDto updatedAddress = addressServiceImp.updateAddressByState(addressId, state);
        return ResponseEntity.ok(updatedAddress);
    }

    @PatchMapping("/{addressId}/country")
    public ResponseEntity<AddressDto> updateCountry(@PathVariable String addressId,
                                                    @RequestParam String country) {
        AddressDto updatedAddress = addressServiceImp.updateAddressByCountry(addressId, country);
        return ResponseEntity.ok(updatedAddress);
    }

    @PatchMapping("/{addressId}/pinCode")
    public ResponseEntity<AddressDto> updatePinCode(@PathVariable String addressId,
                                                    @RequestParam String pinCode) {
        AddressDto updatedAddress = addressServiceImp.updateAddressByPinCode(addressId, pinCode);
        return ResponseEntity.ok(updatedAddress);
    }


    @DeleteMapping("/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable String addressId) {
        String response = addressServiceImp.deleteAddress(addressId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/city/{city}")
    public ResponseEntity<String> deleteAddressByCity(@PathVariable String city) {
        String response = addressServiceImp.deleteAddressByCity(city);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/state/{state}")
    public ResponseEntity<String> deleteAddressByState(@PathVariable String state) {
        String response = addressServiceImp.deleteAddressByState(state);
        return ResponseEntity.ok(response);
    }

}
