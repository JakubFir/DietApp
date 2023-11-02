package com.example.foodgenerator.dto;


import java.io.Serializable;

public record IngredientsDto(String name,
                             double calories,
                             double fat,
                             double protein,
                             double carbs,
                             int weight) implements Serializable {

}
