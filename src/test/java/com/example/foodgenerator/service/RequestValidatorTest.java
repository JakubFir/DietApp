package com.example.foodgenerator.service;

import com.example.foodgenerator.domain.Diet;
import com.example.foodgenerator.domain.Gender;
import com.example.foodgenerator.domain.Role;
import com.example.foodgenerator.domain.User;
import com.example.foodgenerator.dto.RegisterRequest;
import com.example.foodgenerator.exceptions.BadPasswordException;
import com.example.foodgenerator.exceptions.UsernameTakenException;
import com.example.foodgenerator.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestValidatorTest {
    @Mock
    private UserRepository userRepository;
    private RequestValidator requestValidator;

    @BeforeEach
    void setUp() {
        requestValidator = new RequestValidator(userRepository);
    }

    @Test
    void validatorShouldReturnTrue() {
        RegisterRequest request = new RegisterRequest(
                "Test1",
                "test@wp.pl",
                "test1",
                12,
                12,
                12,
                Gender.MALE,
                2);

        boolean result = requestValidator.validateRegisterRequest(request);

        assertThat(result).isTrue();
    }

    @Test
    void validatorShouldThrowBadPasswordException() {
        RegisterRequest request = new RegisterRequest(
                "Test1",
                "test@wp.pl",
                "test",
                12,
                12,
                12,
                Gender.MALE,
                2);

        BadPasswordException exception
                = assertThrows(BadPasswordException.class, () ->
                requestValidator.validateRegisterRequest(request));

        assertThat("Password has to be at least 5 characters long").isEqualTo(exception.getMessage());
    }
}