package com.example.foodgenerator.mapper;

import com.example.foodgenerator.domain.MealIngredient;
import com.example.foodgenerator.dto.IngredientsDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealIngredientMapper {
    public List<MealIngredient> mapToMealIngredientList(List<IngredientsDto> ingredientsList) {
        return ingredientsList.stream().map(this::mapToMealIngredient).collect(Collectors.toList());
    }

    public MealIngredient mapToMealIngredient(IngredientsDto ingredientsDto) {
        return new MealIngredient(
                ingredientsDto.getName(),
               ingredientsDto.getCalories(),
               ingredientsDto.getFat(),
               ingredientsDto.getProtein(),
               ingredientsDto.getCarbs(),
               ingredientsDto.getWeight()
        );
    }

    public IngredientsDto mapToMealIngredientDto(MealIngredient mealIngredient) {
        return new IngredientsDto(
                mealIngredient.getName(),
                mealIngredient.getCalories(),
                mealIngredient.getFat(),
                mealIngredient.getProtein(),
                mealIngredient.getCarbs(),
                mealIngredient.getWeight()
        );
    }
}
