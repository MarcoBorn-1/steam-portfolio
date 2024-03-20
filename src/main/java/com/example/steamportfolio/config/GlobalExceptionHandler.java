package com.example.steamportfolio.config;

import com.example.steamportfolio.config.exceptions.CurrencyNotFoundException;
import com.example.steamportfolio.config.exceptions.DuplicateNameException;
import com.example.steamportfolio.config.exceptions.ItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateNameException.class)
    public ResponseEntity<String> handleDuplicateNameException(DuplicateNameException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("An error occurred: " + e.getMessage());
    }

    @ExceptionHandler({NoSuchElementException.class, CurrencyNotFoundException.class})
    public ResponseEntity<String> handleElementNotFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("An error occurred: " + e.getMessage());
    }
}