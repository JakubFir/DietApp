package com.example.foodgenerator.exceptions;

public class EmailTakenException extends RuntimeException {
    public EmailTakenException(String msg) {
        super(msg);
    }
}
