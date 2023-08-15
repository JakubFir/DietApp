package com.example.foodgenerator.mapper;

import com.example.foodgenerator.domain.Meal;
import com.example.foodgenerator.dto.MealDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MealMapper {

    private final IngredientsMapper ingredientsMapper;
    public Meal mapToMeal(MealDto mealDto) {
        return new Meal(
                mealDto.getMealName(),
                ingredientsMapper.mapToIngredientList(mealDto.getIngredientsList()),
                mealDto.getCalories(),
                mealDto.getFat(),
                mealDto.getProtein(),
                mealDto.getCarbs()
        );
    }
}
