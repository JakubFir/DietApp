package com.example.foodgenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealDiaryDto {
    private String username;
    private double remainingCalories;
    private double caloricDemand;
    private LocalDate date;
    private List<MealDto> list;
}
