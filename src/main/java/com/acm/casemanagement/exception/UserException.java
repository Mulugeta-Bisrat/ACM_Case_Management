package com.acm.casemanagement.exception;

public class UserException extends RuntimeException {

    public UserException(String message) {
        super(message);
    }

    public static class UserAlreadyExistsException extends UserException {
        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class InvalidCredentialsException extends UserException {
        public InvalidCredentialsException(String message) {
            super(message);
        }
    }

    public static class ResourceNotFoundException extends UserException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

    // You can add more exceptions as needed
}

