package com.example.foodgenerator.exceptions;


public class MealDiaryNotFoundException extends RuntimeException{

    public MealDiaryNotFoundException(String msg) {
        super(msg);
    }
}
