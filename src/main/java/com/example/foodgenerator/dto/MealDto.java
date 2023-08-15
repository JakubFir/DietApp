package com.example.foodgenerator.dto;

import com.example.foodgenerator.domain.Ingredients;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealDto {
    private String mealName;
    private Long calories;
    private Long fat;
    private Long protein;
    private Long carbs;
    private List<IngredientsDto> ingredientsList;
}
