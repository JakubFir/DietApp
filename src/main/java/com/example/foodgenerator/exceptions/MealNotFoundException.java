package com.example.foodgenerator.exceptions;


public class MealNotFoundException extends RuntimeException {
    public MealNotFoundException(String msg) {
        super(msg);
    }
}
