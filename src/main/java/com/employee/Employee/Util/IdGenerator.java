package com.employee.Employee.Util;

import java.security.SecureRandom;

public class IdGenerator {

    private static final SecureRandom random = new SecureRandom();

    public static String generateCustomId(String username) {
        // Padding the username if it has less than three letters
        String paddedUsername = String.format("%-3s", username).replace(' ', 'X');

        // Getting 1st three letters
        String idPrefix = paddedUsername.substring(0, 3);

        // Generate a random three-digit number
        int randomNumber = random.nextInt(900) + 100;

        // Concatenates
        return idPrefix + randomNumber;
    }
}

