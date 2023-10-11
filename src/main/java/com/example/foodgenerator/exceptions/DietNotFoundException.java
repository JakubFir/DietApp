package com.example.foodgenerator.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DietNotFoundException extends RuntimeException {
    private final static Logger LOGGER = LoggerFactory.getLogger(DietNotFoundException.class);

    public DietNotFoundException(String msg) {
        super(msg);
        LOGGER.warn("Diet not found");
    }
}
