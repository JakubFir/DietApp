package com.example.foodgenerator.mapper;

import com.example.foodgenerator.domain.Meal;
import com.example.foodgenerator.dto.MealDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MealMapper {

    private final MealIngredientMapper mealIngredientMapper;
    public Meal mapToMeal(MealDto mealDto) {
        return new Meal(
                mealDto.getMealName(),
                mealIngredientMapper.mapToMealIngredientList(mealDto.getIngredientsList()),
                mealDto.getCalories(),
                mealDto.getFat(),
                mealDto.getProtein(),
                mealDto.getCarbs()
        );
    }
}
