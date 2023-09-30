package com.example.foodgenerator.dto;

import com.example.foodgenerator.domain.Gender;
import jakarta.validation.constraints.Email;


public record RegisterRequest(String username,
                              String email,
                              String password,
                              int age,
                              double weight,
                              double height,
                              Gender gender,
                              int activityLevel) {


}
