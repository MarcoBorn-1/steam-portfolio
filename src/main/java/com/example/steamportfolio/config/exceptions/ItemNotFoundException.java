package com.example.steamportfolio.config.exceptions;

import java.util.NoSuchElementException;

public class ItemNotFoundException extends NoSuchElementException {
    public ItemNotFoundException(String text) {
        super(text);
    }

    public ItemNotFoundException() {
        super("The given item does not exist!");
    }
}
