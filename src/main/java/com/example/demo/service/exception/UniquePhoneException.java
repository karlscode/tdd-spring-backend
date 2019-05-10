package com.example.demo.service.exception;

/**
 * @author KarlsCode.
 */

public class UniquePhoneException extends RuntimeException {

    public UniquePhoneException(String message) {
        super(message);
    }
}
