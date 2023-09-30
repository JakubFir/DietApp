package com.example.foodgenerator.mapper;

import com.example.foodgenerator.domain.Meal;
import com.example.foodgenerator.dto.MealDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MealMapper {

    private final MealIngredientMapper mealIngredientMapper;
    public Meal mapToMeal(MealDto mealDto) {
        return new Meal(
                mealDto.mealName(),
                mealIngredientMapper.mapToMealIngredientList(mealDto.ingredientsList()),
                mealDto.calories(),
                mealDto.fat(),
                mealDto.protein(),
                mealDto.carbs()
        );
    }

    public MealDto mapToMealDto(Meal meal) {
        return new MealDto(
                meal.getId(),
                meal.getMealName(),
                meal.getCalories(),
                meal.getFat(),
                meal.getProtein(),
                meal.getCarbs(),
                meal.getMealDiary().getDate(),
                meal.getIngredients().stream().map(mealIngredientMapper::mapToMealIngredientDto).collect(Collectors.toList()));
    }
}
