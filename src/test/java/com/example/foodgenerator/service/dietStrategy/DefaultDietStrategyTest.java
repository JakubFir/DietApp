package com.example.foodgenerator.service.dietStrategy;

import com.example.foodgenerator.domain.Diet;
import com.example.foodgenerator.domain.User;
import com.example.foodgenerator.repository.DietRepository;
import com.example.foodgenerator.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DefaultDietStrategyTest {
    @Mock
    private  DietRepository dietRepository;
    @Mock
    private  UserRepository userRepository;

    private DefaultDietStrategy defaultDietStrategy;

    @BeforeEach
    void setUp() {
        defaultDietStrategy = new DefaultDietStrategy(dietRepository,userRepository);
    }

    @Test
    void calculateMacro() {
        User user = new User();
        Diet diet = new Diet();
        diet.setName("Defualt");
        user.setCaloricDemand(100);
        user.setDiet(diet);


        //When
        defaultDietStrategy.calculateMacro(user);

        //Then
        double exTotalCalories = user.getCaloricDemand();
        double exCarbs = Math.round((exTotalCalories * 0.4) / 4);
        double exProtein = Math.round((exTotalCalories * 0.3) / 4);
        double exFat = Math.round((exTotalCalories * 0.3) / 9);

        assertThat(user.getDiet().getProtein()).isEqualTo(exProtein);
        assertThat(user.getDiet().getCarbs()).isEqualTo(exCarbs);
        assertThat(user.getDiet().getFat()).isEqualTo(exFat);

        verify(dietRepository).save(diet);
        verify(userRepository).save(user);
    }

    @Test
    void dietType() {
    }
}