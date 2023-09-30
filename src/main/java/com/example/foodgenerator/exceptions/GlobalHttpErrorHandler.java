package com.example.foodgenerator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHttpErrorHandler {

    @ExceptionHandler(BadPasswordException.class)
    public ResponseEntity<Object> handleBadPasswordException(BadPasswordException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(DietNotFoundException.class)
    public ResponseEntity<Object> handleDietNotFoundException(DietNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(EmailTakenException.class)
    public ResponseEntity<Object> handleEmailTakenException(EmailTakenException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(UsernameTakenException.class)
    public ResponseEntity<Object> handleUsernameTakenException(UsernameTakenException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(IngredientNotFoundException.class)
    public ResponseEntity<Object> handleIngredientNotFound(IngredientNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception) {
        String exc = "Invalid request format. Please provide valid input.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exc);
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<Object> handleInvalidEmailException(InvalidEmailException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(InvalidIngredientInput.class)
    public ResponseEntity<Object> handleInvalidIngredientInput(InvalidIngredientInput exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception);
    }
}
