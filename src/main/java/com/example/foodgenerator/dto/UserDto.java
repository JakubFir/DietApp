package com.example.foodgenerator.dto;

import com.example.foodgenerator.domain.Diet;
import com.example.foodgenerator.domain.Gender;
import com.example.foodgenerator.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

public record UserDto(String username,
                      String email,
                      Role role,
                      int age,
                      double weight,
                      double height,
                      Gender gender,
                      int activityLevel,
                      double caloricDemand,
                      DietDto diet,
                      List<MealDiaryDto> mealDiary) {

}
