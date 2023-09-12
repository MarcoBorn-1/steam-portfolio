package com.example.steamportfolio.config.exceptions;

public class DuplicateNameException extends Exception {
    public DuplicateNameException(String text) {
        super(text);
    }

    public DuplicateNameException() {
        super("The name you've used is already in use.");
    }
}
