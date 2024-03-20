package com.example.steamportfolio.config;

import com.example.steamportfolio.config.exceptions.DuplicateNameException;
import com.example.steamportfolio.config.exceptions.ItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateNameException.class)
    public ResponseEntity<String> handleDuplicateNameException(DuplicateNameException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("An error occurred: " + e.getMessage());
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<String> handleItemNotFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("An error occurred: " + e.getMessage());
    }
}