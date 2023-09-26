package com.example.foodgenerator.exceptions;

public class InvalidIngredientInput extends RuntimeException {
    public InvalidIngredientInput(String msg) {
        super(msg);
    }
}
