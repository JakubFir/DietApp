package com.example.foodgenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientsDto {
    private String name;
    private double calories;
    private double fat;
    private double protein;
    private double carbs;
    private int weight;
}
