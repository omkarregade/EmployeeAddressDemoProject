package com.employee.Employee.Util;

import com.employee.Employee.entities.Address;
import com.employee.Employee.exception.NoResourceFoundException;


import java.util.UUID;

public class Utility {
    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    public static boolean isValidName(String name) {
        return name != null && name.length() <= 20 && name.matches("[a-zA-Z]+") && !name.trim().isEmpty();
    }

    public static boolean isValidDesignation(String designation) {
        return designation != null && designation.length() <= 20 && designation.matches("[a-zA-Z ]+") && !designation.trim().isEmpty();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("\\d{10}");
    }

    public static void validateCity(String city) {
        if (city == null || city.length() < 2 || city.length() > 30 || !city.matches("[a-zA-Z ]*") || city.trim().isEmpty()) {
            throw new NoResourceFoundException("city", "Invalid city provided: " + city);
        }
    }

    public static void validateState(String state) {
        if (state == null || state.length() < 2 || state.length() > 30 || !state.matches("[a-zA-Z ]+") || state.trim().isEmpty()) {
            throw new NoResourceFoundException("state", "Invalid state provided: " + state);
        }
    }

    public static void validateCountry(String country) {
        if (country == null || country.length() < 2 || country.length() > 30 || !country.matches("[a-zA-Z ]+") || country.trim().isEmpty()) {
            throw new NoResourceFoundException("country", "Invalid country provided: " + country);
        }
    }

    public static void validatePinCode(String pinCode) {
        if (pinCode == null || pinCode.length() != 6) {
            throw new NoResourceFoundException("pinCode", "pinCode size must be 6 digits: " + pinCode);
        }
    }
}
