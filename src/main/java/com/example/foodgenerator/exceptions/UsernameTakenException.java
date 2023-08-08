package com.example.foodgenerator.exceptions;

public class UsernameTakenException extends RuntimeException {
    public UsernameTakenException(String msg) {
        super(msg);
    }
}
