package com.example.demo.service.exception;

/**
 * @author KarlsCode.
 */

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException(String message) {
        super(message);
    }
}
