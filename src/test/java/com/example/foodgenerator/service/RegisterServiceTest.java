package com.example.foodgenerator.service;

import com.example.foodgenerator.domain.Gender;
import com.example.foodgenerator.domain.User;
import com.example.foodgenerator.dto.RegisterRequest;
import com.example.foodgenerator.repository.DietRepository;
import com.example.foodgenerator.repository.UserRepository;
import com.example.foodgenerator.service.dietStrategy.DietType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterServiceTest {
    @Mock
    private  UserRepository userRepository;
    @Mock
    private  RequestValidator requestValidator;
    @Mock
    private  PasswordEncoder passwordEncoder;
    @Mock
    private  UserService userService;
    @Mock
    private  DietRepository dietRepository;
    @Mock
    private  DietService dietService;

    private  RegisterService registerService;
    @BeforeEach
    void setUp() {
        registerService = new RegisterService(userRepository,
                requestValidator,
                passwordEncoder,
                userService,
                dietRepository,
                dietService);
    }

    @Test
    void registerUser() {
        RegisterRequest request = new RegisterRequest(
                "Test1",
                "test@wp.pl",
                "test",
                12,
                12,
                12,
                Gender.MALE,
                2);

        when(requestValidator.validateRegisterRequest(request)).thenReturn(true);
        when(userService.calculateCaloricDemand(request)).thenReturn(2000.0);

        //When
        registerService.registerUser(request);

        //Then
        ArgumentCaptor<User> argumentCaptor =ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(argumentCaptor.capture());

        User savedUser = argumentCaptor.getValue();

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo(request.email());

        verify(dietService).setUserDiet(eq(DietType.DEFAULT), eq(savedUser.getUserId()));
    }
}