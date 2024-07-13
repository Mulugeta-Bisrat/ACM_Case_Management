package com.acm.casemanagement.dto;
import com.acm.casemanagement.exception.UserException;
import org.apache.commons.lang3.StringUtils;

public class UserValidator {
    public static void validateEmail(String email) {
        if (StringUtils.isBlank(email) || !email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new UserException.ValidationException("Invalid email format");
        }
    }

    public static void validatePassword(String password) {
        if (StringUtils.isBlank(password) || password.length() < 8 || !password.matches("^(?=.*[0-9])(?=.*[a-zA-Z]).+$")) {
            throw new UserException.ValidationException("Password must be at least 8 characters long, contain at least one digit and one letter");
        }
    }

    public static void validateUsername(String username) {
        if (StringUtils.isBlank(username) || username.length() < 3 || username.length() > 20 || !username.matches("^[a-zA-Z0-9]*$")) {
            throw new UserException.ValidationException("Username must be between 3 and 20 characters and contain only alphanumeric characters");
        }
    }
    public static void validateFirstname(String firstname) {
        if (StringUtils.isBlank(firstname) || firstname.length() < 2 || firstname.length() > 50) {
            throw new UserException.ValidationException("Firstname must be between 2 and 50 characters");
        }
    }

    public static void validateLastname(String lastname) {
        if (StringUtils.isBlank(lastname) || lastname.length() < 2 || lastname.length() > 50) {
            throw new UserException.ValidationException("Lastname must be between 2 and 50 characters");
        }
    }
}
