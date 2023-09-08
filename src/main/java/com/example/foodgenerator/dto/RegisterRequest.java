package com.example.foodgenerator.dto;

import com.example.foodgenerator.domain.Gender;
import lombok.Data;


public record RegisterRequest(String username,
                              String email,
                              String password,
                              int age,
                              double weight,
                              double height,
                              Gender gender,
                              int activityLevel) {


}
