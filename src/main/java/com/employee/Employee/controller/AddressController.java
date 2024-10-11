package com.employee.Employee.controller;

import com.employee.Employee.dto.AddressDTO;
import com.employee.Employee.dto.EmployeeDTO;
import com.employee.Employee.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO) {
        AddressDTO createdAddress = addressService.createAddress(addressDTO);
        return ResponseEntity.ok(createdAddress);
    }

    @GetMapping
    public ResponseEntity<List<AddressDTO>> getAddress( @RequestParam(value = "addressId", required = false) String addressId) {
        List<AddressDTO> address = addressService.getAddress(addressId);
        return ResponseEntity.ok(address);
    }

 /*   @GetMapping
    public ResponseEntity<List<AddressDTO>> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }
*/
    @PutMapping("/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable String addressId, @Valid @RequestBody AddressDTO addressDTO) {
        AddressDTO updatedAddress = addressService.updateAddress(addressId, addressDTO);
        return ResponseEntity.ok(updatedAddress);
    }

    @PatchMapping("/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable String addressId,
                                                    @RequestParam(required = false) String state,
                                                    @RequestParam(required = false) String country,
                                                    @RequestParam(required = false) String pinCode,
                                                    @RequestParam(required = false) String city) {
        AddressDTO updatedAddress = addressService.updateAddress(addressId, state, country, pinCode, city);
        return ResponseEntity.ok(updatedAddress);
    }


    @DeleteMapping("/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable String addressId) {
        String response = addressService.deleteAddress(addressId);
        return ResponseEntity.ok(response);
    }

}
