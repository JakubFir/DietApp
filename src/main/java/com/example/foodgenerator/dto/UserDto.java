package com.example.foodgenerator.dto;

import com.example.foodgenerator.domain.Meal;
import com.example.foodgenerator.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private String email;
    private Role role;
    private List<Meal> mealList;
}