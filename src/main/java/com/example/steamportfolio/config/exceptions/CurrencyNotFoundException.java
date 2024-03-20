package com.example.steamportfolio.config.exceptions;

import java.util.NoSuchElementException;

public class CurrencyNotFoundException extends NoSuchElementException {
    public CurrencyNotFoundException(String text) {
        super(text);
    }

    public CurrencyNotFoundException() {
        super("The given currency does not exist!");
    }
}