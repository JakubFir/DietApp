package com.example.foodgenerator.service;

import com.example.foodgenerator.domain.*;
import com.example.foodgenerator.dto.IngredientsDto;
import com.example.foodgenerator.dto.MealDto;
import com.example.foodgenerator.mapper.MealIngredientMapper;
import com.example.foodgenerator.mapper.MealMapper;
import com.example.foodgenerator.repository.IngredientsRepository;
import com.example.foodgenerator.repository.MealDiaryRepository;
import com.example.foodgenerator.repository.MealRepository;
import com.example.foodgenerator.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.in;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MealServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private MealRepository mealRepository;
    @Mock
    private MealDiaryService mealDiaryService;
    @Mock
    private IngredientsRepository ingredientsRepository;
    @Mock
    private IngredientsService ingredientsService;
    @Mock
    private MealIngredientMapper mealIngredientMapper;
    @Mock
    private MealDiaryRepository mealDiaryRepository;
    @Mock
    private MealMapper mealMapper;

    private MealService mealService;

    @BeforeEach
    void setUp() {
        mealService = new MealService(
                userRepository,
                mealRepository,
                mealDiaryService,
                ingredientsRepository,
                ingredientsService,
                mealIngredientMapper,
                mealDiaryRepository,
                mealMapper
        );
    }

    @Test
    void addMealToUserMealDiary() {
        //Given
        Long userId = 1L;
        User user = new User();
        MealDiary mealDiary = new MealDiary();
        List<MealIngredient> mealIngredientList = List.of(new MealIngredient("test", 2, 2, 2, 2, 2));
        Meal meal = new Meal("test", mealIngredientList, 2, 2, 2, 2);
        MealDto mealDto = new MealDto("test", 2, 2, 2, 2, LocalDate.now(), new ArrayList<>());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(mealDiaryService.getUserMealDiary(any(), any())).thenReturn(mealDiary);
        when(mealMapper.mapToMeal(any())).thenReturn(meal);
        when(mealIngredientMapper.mapToMealIngredientList(anyList()))
                .thenReturn(Collections.singletonList(new MealIngredient("test", 2, 2, 2, 2, 2)));
        //When
        mealService.addMealToUserMealDiary(mealDto, userId);

        //Then
        ArgumentCaptor<Meal> argumentCaptor = ArgumentCaptor.forClass(Meal.class);

        verify(mealRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getCalories()).isEqualTo(mealDto.calories());
        assertThat(argumentCaptor.getValue().getMealName()).isEqualTo(mealDto.mealName());
        assertThat(argumentCaptor.getValue().getProtein()).isEqualTo(mealDto.protein());

    }

    @Test
    void getUserMeals() {
        //Given
        MealDiary mealDiary = new MealDiary();
        User user = new User();
        mealDiary.setId(6L);
        user.setMealDiary(List.of(mealDiary));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        //When
        List<MealDiary> result = mealService.getUserMeals(1L);

        //Then
        assertThat(result).isNotNull();
        assertThat(result.get(0).getId()).isEqualTo(mealDiary.getId());
    }

    @Test
    void calculateMealcalories() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //Given
        Method calculateCalories = MealService.class.getDeclaredMethod("calculateMealCalories",MealDto.class);
        List<MealIngredient> mealIngredientList = List.of(new MealIngredient("test", 100, 10, 10, 10, 10));
        Meal meal = new Meal("test", mealIngredientList, 0, 0, 0, 0);
        MealDto mealDto = new MealDto("test",0,0,0,0,LocalDate.now(),new ArrayList<>());
        IngredientsDto ingredientsDto = new IngredientsDto("test",100,10,10,10,10);
        mealDto.ingredientsList().add(ingredientsDto);
        Ingredients ingredients = new Ingredients("test",100,10,10,10);
        calculateCalories.setAccessible(true);

        when(mealMapper.mapToMeal(mealDto)).thenReturn(meal);
        when(ingredientsRepository.findByName(any())).thenReturn(ingredients);


        //When
        Meal result = (Meal) calculateCalories.invoke(mealService, mealDto);
        List<MealIngredient> updatedIngredientsList = result.getIngredients();
        System.out.println(updatedIngredientsList);

        //Then
        double expectedProtein = (ingredientsDto.protein() / 100) * ingredientsDto.weight();
        double expectedFat = (ingredientsDto.fat() / 100) * ingredientsDto.weight();
        double expectedCarbs = (ingredientsDto.carbs() / 100) * ingredientsDto.weight();

        assertThat(result.getProtein()).isEqualTo(expectedProtein);
        assertThat(result.getFat()).isEqualTo(expectedFat);
        assertThat(result.getCarbs()).isEqualTo(expectedCarbs);
    }
}