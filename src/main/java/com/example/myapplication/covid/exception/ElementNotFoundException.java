package com.example.myapplication.covid.exception;

public class ElementNotFoundException extends RuntimeException {
    public ElementNotFoundException(String message) {
        super(message);
    }
}
