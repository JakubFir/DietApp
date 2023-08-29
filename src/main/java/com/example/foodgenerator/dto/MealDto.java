package com.example.foodgenerator.dto;

import com.example.foodgenerator.domain.Ingredients;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealDto {
    private String mealName;
    private double calories;
    private double fat;
    private double protein;
    private double carbs;
    private LocalDate mealDate;
    private List<IngredientsDto> ingredientsList;
}
