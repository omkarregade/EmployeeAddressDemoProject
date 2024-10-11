package com.employee.Employee.serviceimplemention;

import com.employee.Employee.Util.IdGenerator;
import com.employee.Employee.Util.Utility;
import com.employee.Employee.dto.AddressDTO;
import com.employee.Employee.dto.EmployeeDTO;
import com.employee.Employee.entities.Address;
import com.employee.Employee.entities.Employee;
import com.employee.Employee.exception.NoResourceFoundException;
import com.employee.Employee.exception.NotFoundException;
import com.employee.Employee.mapper.Mapper;
import com.employee.Employee.repository.AddressRepo;
import com.employee.Employee.repository.EmployeeRepo;
import com.employee.Employee.service.AddressService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AddressServiceImp implements AddressService {

    private static final Logger logger = LogManager.getLogger(AddressServiceImp.class);

    private AddressRepo addressRepo;
    private EmployeeRepo employeeRepo;

    public AddressServiceImp(AddressRepo addressRepo, EmployeeRepo employeeRepo) {
        this.addressRepo = addressRepo;
        this.employeeRepo = employeeRepo;
    }

    private Address address;
    private AddressDTO addressDTO;
    private List<Address> addresses;
    private List<AddressDTO> addressDTOList;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {
        logger.info("stated creating address into address");
        address = Mapper.addressDTOTOAddress(addressDTO);
        String addressId = IdGenerator.generateCustomId(address.getCity());
        address.setAddressId(addressId);

        addressRepo.save(address);
        logger.info("addess is saved into the Data base with addressId : "+addressId);
        addressDTO = Mapper.addressToAddressDTO(address);
        return addressDTO;
    }

    @Override
    public List<AddressDTO> getAddress(String addressId) {
        addressDTOList = (addressId != null) ? Collections.singletonList(findAddressById(addressId)).stream().map(address1 -> Mapper.addressToAddressDTO(address1)).toList() :
                    addressRepo.findAll().stream().map(address -> Mapper.addressToAddressDTO(address)).toList();
        if (addressDTOList.isEmpty()) {
            logger.error("No address found in the database");
            throw new NotFoundException("No address found in the database");
        }
        logger.info("address list is fetched successfully !!!!");
        return addressDTOList;
    }

    @Override
    public AddressDTO updateAddress(String addressId, AddressDTO addressDTO) {
        try{
            address = findAddressById(addressId);
                address = Mapper.addressDTOTOAddress(addressDTO);
                address.setAddressId(addressId);

                address = addressRepo.save(address);
                logger.info("address is updated succesfullly !!!");
                addressDTO = Mapper.addressToAddressDTO(address);
                return addressDTO;
        }
        catch (Exception ex) {
            logger.error("An error occurred while updating address into address with id {}: {}", addressId, ex.getMessage());
            throw ex;
        }
    }

    @Override
    @Transactional
    public AddressDTO updateAddress(String addressId, String state, String country, String pinCode, String city) {
        try {
            Address address = findAddressById(addressId);

            if (city != null) {
                Utility.validateCity(city);
                address.setCity(city);
            }
            if (state != null) {
                Utility.validateState(state);
                address.setState(state);
            }
            if (country != null) {
                Utility.validateCountry(country);
                address.setCountry(country);
            }
            if (pinCode != null) {
                Utility.validatePinCode(pinCode);
                address.setPinCode(pinCode);
            }

             address = addressRepo.save(address);
            logger.info("Address with addressId: " + addressId + " is updated successfully.");
            return Mapper.addressToAddressDTO(address);
        } catch (Exception ex) {
            logger.error("An error occurred while updating the address with id {}: {}", addressId, ex.getMessage());
            throw ex;
        }
    }

    @Override
    public String deleteAddress(String addressId) {
        logger.info("stated deleting address into address with addressId : "+addressId);
       try{
           address = findAddressById(addressId);
           addressRepo.delete(address);
           logger.info("address is deleted successfully !!!");
           return "address deleted successfully !!!";
       }
       catch (Exception ex) {
           logger.error("An error occurred while deleting address into address with id {}: {}", addressId, ex.getMessage());
           throw ex;
       }
    }

    private Address findAddressById(String addressId){
      address = addressRepo.findById(addressId)
                .orElseThrow(() -> new NotFoundException("Address with id " + addressId + " not found"));
        logger.info("Address with addressId: " + addressId + " is found successfully.");
        return address;
    }


}
