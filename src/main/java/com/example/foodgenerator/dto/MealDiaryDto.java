package com.example.foodgenerator.dto;


import java.time.LocalDate;
import java.util.List;
public record MealDiaryDto( String username,
                           double remainingCalories,
                           double caloricDemand,
                           LocalDate date,
                           List<MealDto> list) {

}
