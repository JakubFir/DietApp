package com.example.foodgenerator.exceptions;

public class DietNotFoundException extends RuntimeException {
    public DietNotFoundException(String msg) {
        super(msg);
    }
}
