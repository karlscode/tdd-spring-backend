package com.example.demo.exceptionhandler;

import com.example.demo.service.exception.PersonNotFoundException;
import com.example.demo.service.exception.PhoneNotFoundException;
import com.example.demo.service.exception.UniqueCpfException;
import com.example.demo.service.exception.UniquePhoneException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author KarlsCode.
 */

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler( { PhoneNotFoundException.class} )
    public ResponseEntity<Erro> handlePhoneNotFoundException(PhoneNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Erro(ex.getMessage()));
    }

    @ExceptionHandler( { UniqueCpfException.class} )
    public ResponseEntity<Erro> handleUniqueCpfException(UniqueCpfException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Erro(ex.getMessage()));
    }

    @ExceptionHandler( { UniquePhoneException.class} )
    public ResponseEntity<Erro> handleUniquePhoneException(UniquePhoneException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Erro(ex.getMessage()));
    }

    @ExceptionHandler( { PersonNotFoundException.class} )
    public ResponseEntity<Erro> handlePersonNotFoundException(PersonNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Erro(ex.getMessage()));
    }

    class Erro {
        private final String error;

        public Erro(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }
    }
}
