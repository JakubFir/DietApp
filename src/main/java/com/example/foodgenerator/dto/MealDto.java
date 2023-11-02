package com.example.foodgenerator.dto;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public record MealDto(
        Long mealId,
        String mealName,
        double calories,
        double fat,
        double protein,
        double carbs,
        LocalDate mealDate,
        List<IngredientsDto> ingredientsList)implements Serializable {

}
