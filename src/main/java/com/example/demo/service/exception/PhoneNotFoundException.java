package com.example.demo.service.exception;

/**
 * @author KarlsCode.
 */
public class PhoneNotFoundException extends RuntimeException {

    public PhoneNotFoundException(String message) {
        super(message);
    }
}
