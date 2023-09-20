package com.example.foodgenerator.service;

import com.example.foodgenerator.domain.MealDiary;
import com.example.foodgenerator.domain.User;
import com.example.foodgenerator.repository.MealDiaryRepository;
import com.example.foodgenerator.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MealDiaryServiceTest {
    @Mock
    private MealDiaryRepository mealDiaryRepository;
    @Mock
    private UserRepository userRepository;

    private MealDiaryService mealDiaryService;
    @BeforeEach
    void setUp() {
        mealDiaryService = new MealDiaryService(mealDiaryRepository,userRepository);
    }

    @Test
    void getUserMealDiary() {
        //Given
        User user = new User();
        MealDiary mealDiary = new MealDiary();
        mealDiary.setId(56L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(mealDiaryRepository.findByUserAndDate(any(),any())).thenReturn(Optional.of(mealDiary));

        //When
        MealDiary result = mealDiaryService.getUserMealDiary(1L, LocalDate.now());

        //Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(mealDiary.getId());
    }

    @Test
    void getUserMealDiaryIfItsNull() {
        //Given
        User user = new User();
        user.setMealDiary(new ArrayList<>());
        user.setCaloricDemand(200);
        MealDiary mealDiary = new MealDiary();


        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(mealDiaryRepository.findByUserAndDate(user, LocalDate.now())).thenReturn(Optional.empty());

        //When
        MealDiary result = mealDiaryService.getUserMealDiary(1L, LocalDate.now());

        //Then
        assertThat(result).isNotNull();
        assertThat(result.getCaloricDemand()).isEqualTo(user.getCaloricDemand());
    }
}