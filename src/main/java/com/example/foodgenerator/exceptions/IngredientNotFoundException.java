package com.example.foodgenerator.exceptions;

public class IngredientNotFoundException extends RuntimeException{

    public IngredientNotFoundException(String msg){
        super(msg);

    }
}
