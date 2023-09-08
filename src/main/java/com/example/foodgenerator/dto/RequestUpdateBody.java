package com.example.foodgenerator.dto;


import com.example.foodgenerator.domain.Gender;
import lombok.Builder;
import lombok.Data;


public record RequestUpdateBody(String username,
                                String password,
                                String email,
                                int age,
                                double weight,
                                double height,
                                Gender gender,
                                int activityLevel) {

}
