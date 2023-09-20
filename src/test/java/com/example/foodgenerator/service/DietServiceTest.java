package com.example.foodgenerator.service;

import com.example.foodgenerator.domain.User;
import com.example.foodgenerator.repository.UserRepository;
import com.example.foodgenerator.service.dietStrategy.DefaultDietStrategy;
import com.example.foodgenerator.service.dietStrategy.DietStrategy;
import com.example.foodgenerator.service.dietStrategy.DietType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DietServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private DefaultDietStrategy defaultDietStrategy;

    private Map<DietType, DietStrategy> setDietByType;

    private DietService dietService;

    @BeforeEach
    void setUp() {
        setDietByType = new HashMap<>();
        dietService = new DietService(userRepository, defaultDietStrategy, setDietByType);
    }

    @Test
    void setUserDiet() {
        //Given
        User user = new User();
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        DietStrategy dietStrategy = mock(DietStrategy.class);
        setDietByType.put(DietType.DEFAULT,dietStrategy);

        //When
        dietService.setUserDiet(DietType.DEFAULT, 1L);

        //Then
        verify(dietStrategy).calculateMacro(user);

    }
}