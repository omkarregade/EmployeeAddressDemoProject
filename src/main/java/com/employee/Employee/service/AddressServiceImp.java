package com.employee.Employee.service;

import com.employee.Employee.dto.AddressDto;
import com.employee.Employee.dto.EmployeeDto;
import com.employee.Employee.entities.Address;
import com.employee.Employee.entities.Employee;
import com.employee.Employee.exception.NoResourceFoundException;
import com.employee.Employee.exception.NotFoundException;
import com.employee.Employee.repository.AddressRepo;
import com.employee.Employee.repository.EmployeeRepo;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AddressServiceImp implements AddressService{

    private static final Logger logger = LogManager.getLogger(AddressServiceImp.class);


    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AddressDto createAddress(AddressDto addressDTO) {
        logger.info("stated creating address into address");
        Address address = modelMapper.map(addressDTO, Address.class);
        String addressId = UUID.randomUUID().toString();
        address.setAddressId(addressId);

        addressRepo.save(address);
        logger.info("addess is saved into the Data base with addressId : "+addressId);
        return modelMapper.map(address,AddressDto.class);
    }

    @Override
    public AddressDto getAddressById(String addressId) {
       try{
           Address address = addressRepo.findById(addressId).orElseThrow(()-> new NotFoundException("Address with id "+addressId+" is Not Found "));
           logger.info("Address fetch successfully by id :"+addressId);
           return modelMapper.map(address,AddressDto.class);
       }
       catch (Exception ex) {
           logger.error("An error occurred while getting address into address with id {}: {}", addressId, ex.getMessage());
           throw ex;
       }
    }

    @Override
    public List<AddressDto> getAllAddresses() {
        List<AddressDto> addressDtos = addressRepo.findAll().stream().map(address -> modelMapper.map(address,AddressDto.class)).collect(Collectors.toList());
        if (addressDtos.isEmpty()) {
            logger.error("No address found in the database");
            throw new NotFoundException("No address found in the database");
        }
        logger.info("address list is fetched successfully !!!!");
        return addressDtos;
    }

    @Override
    public AddressDto updateAddress(String addressId, AddressDto addressDTO) {
        try{
            Address address = addressRepo.findById(addressId)
                    .orElseThrow(() -> new NotFoundException("Address with id " + addressId + " is not found"));
            logger.info("address with addressId : "+addressId+" is found successfully !!!");
            modelMapper.map(addressDTO, address);
            address.setAddressId(addressId);

            Address updatedAddress = addressRepo.save(address);
            logger.info("address is updated succesfullly !!!");
            return modelMapper.map(updatedAddress, AddressDto.class);
        }
        catch (Exception ex) {
            logger.error("An error occurred while updating address into address with id {}: {}", addressId, ex.getMessage());
            throw ex;
        }
    }

    @Override
    public String deleteAddress(String addressId) {
        logger.info("stated deleting address into address with addressId : "+addressId);
       try{
           Address address = addressRepo.findById(addressId).orElseThrow(()-> new NotFoundException("Address with id "+addressId+" is Not Found "));
           addressRepo.delete(address);
           logger.info("address is deleted successfully !!!");
           return "address deleted successfully !!!";
       }
       catch (Exception ex) {
           logger.error("An error occurred while deleting address into address with id {}: {}", addressId, ex.getMessage());
           throw ex;
       }
    }

    @Override
    @Transactional
    public String deleteAddressByCity(String city) {
        logger.info("stated deleting addresses by city into address");
        int deletedCount = addressRepo.deleteByCity(city);
        if (deletedCount == 0) {
            logger.error("No addresses found in city " + city);
            throw new NotFoundException("No addresses found in city " + city);
        }
        logger.info("Deleted " + deletedCount + " addresses from city " + city);
        return "Deleted " + deletedCount + " addresses from city " + city;
    }

    @Override
    @Transactional
    public String deleteAddressByState(String state) {
        logger.info("stated deleting addresses by state into address");
        int deletedCount = addressRepo.deleteByState(state);
        if (deletedCount == 0) {
            logger.error("No addresses found in state " + state);
            throw new NotFoundException("No addresses found in state " + state);
        }
        logger.info("Deleted " + deletedCount + " addresses from state " + state);
        return "Deleted " + deletedCount + " addresses from state " + state;
    }

    @Override
    @Transactional
    public AddressDto updateAddressByCity(String addressId, String city) {
        logger.info("stated updating city into address with addressId : "+addressId);
       try {
           if (!isValidCity(city)) {
               logger.error("city", "Invalid city provided: " + city);
               throw new NoResourceFoundException("city", "Invalid city provided: " + city);
           }
           Address address = addressRepo.findById(addressId)
                   .orElseThrow(() -> new NotFoundException("Address with id " + addressId + " not found"));
           logger.info("address with addressId : "+addressId+" is found successfully !!!");
           address.setCity(city);
           Address updatedEmployee = addressRepo.save(address);
           logger.info("city is updated into address of addressId :"+addressId);
           return modelMapper.map(address,AddressDto.class);
       }
       catch (Exception ex) {
           logger.error("An error occurred while updating city into address with id {}: {}", addressId, ex.getMessage());
           throw ex;
       }
    }

    @Override
    @Transactional
    public AddressDto updateAddressByState(String addressId, String state) {
        logger.info("stated updating state into address with addressId : "+addressId);
       try {
           if (!isValidState(state)) {
               logger.error("state", "Invalid state provided: " + state);
               throw new NoResourceFoundException("state", "Invalid state provided: " + state);
           }
           Address address = addressRepo.findById(addressId)
                   .orElseThrow(() -> new NotFoundException("Address with id " + addressId + " not found"));
           logger.info("address with addressId : "+addressId+" is found successfully !!!");
           address.setState(state);
           Address updatedEmployee = addressRepo.save(address);
           logger.info("state is updated into address of addressId :"+addressId);

           return modelMapper.map(address,AddressDto.class);
       }
       catch (Exception ex) {
           logger.error("An error occurred while updating state into address with id {}: {}", addressId, ex.getMessage());
           throw ex;
       }
    }

    @Override
    @Transactional
    public AddressDto updateAddressByCountry(String addressId, String country) {
        logger.info("stated updating country into address with addressId : "+addressId);
        try {
            if (!isValidCountry(country)) {
                logger.error("country", "Invalid country provided: " + country);
                throw new NoResourceFoundException("country", "Invalid country provided: " + country);
            }
            Address address = addressRepo.findById(addressId)
                    .orElseThrow(() -> new NotFoundException("Address with id " + addressId + " not found"));
            logger.info("address with addressId : "+addressId+" is found successfully !!!");
            address.setCountry(country);
            Address updatedEmployee = addressRepo.save(address);
            logger.info("country is updated into address of addressId :"+addressId);
            return modelMapper.map(address,AddressDto.class);
        }
        catch (Exception ex) {
            logger.error("An error occurred while updating country into address with id {}: {}", addressId, ex.getMessage());
            throw ex;
        }
    }

    @Override
    @Transactional
    public AddressDto updateAddressByPinCode(String addressId, String pinCode) {
        logger.info("updating pinCode into the address with addressId : "+addressId);
        try {
            if (!isValidPinCode(pinCode)) {
                throw new NoResourceFoundException("pinCode", "pinCode size must be 6 digit: " + pinCode);
            }
            Address address = addressRepo.findById(addressId)
                    .orElseThrow(() -> new NotFoundException("Address with id " + addressId + " not found"));
            logger.info("address with addressId : "+addressId+" is found successfully !!!");
            address.setPinCode(pinCode);
            Address updatedEmployee = addressRepo.save(address);
            logger.info("pin code is updated into address of addressId :"+addressId);
            return modelMapper.map(address,AddressDto.class);
        }
        catch (Exception ex) {
            logger.error("An error occurred while updating pinCode into address with id {}: {}", addressId, ex.getMessage());
            throw ex;
        }
    }


    @Transactional
    @Override
    public AddressDto addEmployeeToAddress(String addressId, EmployeeDto employeeDto) {
        logger.info("starting to add the employee into the address with addressId : "+addressId);
        try {
            Address address = addressRepo.findById(addressId)
                    .orElseThrow(() -> new NotFoundException("Address with id " + addressId + " is not found"));
            logger.info("address with addressId : "+addressId+" is found successfully !!!");
            Employee employee = modelMapper.map(employeeDto,Employee.class);
            if(employee.getEmployeeId() == null || employee.getEmployeeId().isEmpty()){
                employee.setEmployeeId(UUID.randomUUID().toString());
            }
            employee = employeeRepo.save(employee);
            logger.info("employee is saved succesfully while adding the employee into address with addressId : "+addressId);
            address.setEmployee(employee);
            addressRepo.save(address);
            logger.info("employee will added to address successfully !!!");
            return modelMapper.map(address,AddressDto.class);
        }
        catch (Exception ex) {
            logger.error("An error occurred while adding employee to address with id {}: {}", addressId, ex.getMessage());
            throw ex;
        }
    }

    @Transactional
    @Override
    public EmployeeDto addAddressToEmployee(String employeeId, List<AddressDto> addressDtos) {
        logger.info("Starting to adding address into employee with employeeId : "+employeeId);
        try {
            Employee employee = employeeRepo.findById(employeeId)
                    .orElseThrow(() -> new NotFoundException("Employee with id " + employeeId + " is not found"));
            logger.info("employee with employeeId : "+employeeId+" found Successfully !!! ");
            List<Address> addresses = addressDtos.stream().map(addressDto -> {
                Address address = modelMapper.map(addressDto, Address.class);
                address.setEmployee(employee);
                address.setAddressId(UUID.randomUUID().toString()); // Generate unique addressId if necessary
                return address;
            }).collect(Collectors.toList());
            logger.info("addresses from dto are converted into the addresses of entity successfully !!! ");
            employee.getAddresses().addAll(addresses);

            Employee updatedEmployee = employeeRepo.save(employee);
            logger.info("address is added sucessfully into employee !!!");
            return modelMapper.map(updatedEmployee,EmployeeDto.class);
        }
        catch (Exception ex) {
            logger.error("An error occurred while adding addresses to employee with id {}: {}", employeeId, ex.getMessage());
            throw ex;
        }
    }

    private boolean isValidCity(String city) {
        return city != null && city.length() >= 2 && city.length() <= 30 && city.matches("[a-zA-Z ]*") && !city.trim().isEmpty();
    }

    private boolean isValidState(String state) {
        return state != null && state.length() >= 2 && state.length() <= 30  && state.matches("[a-zA-Z ]+") && !state.trim().isEmpty();
    }

    private boolean isValidCountry(String country) {
        return country != null && country.length() >= 2 && country.length() <= 30 && country.matches("[a-zA-Z ]+") && !country.trim().isEmpty();
    }

    private boolean isValidPinCode(String pinCode) {
        return pinCode != null && pinCode.length() == 6;
    }

}
