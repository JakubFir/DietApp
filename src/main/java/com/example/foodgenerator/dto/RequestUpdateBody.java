package com.example.foodgenerator.dto;


import com.example.foodgenerator.domain.Gender;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RequestUpdateBody {
    private String username;
    private String password;
    private String email;
    private int age;
    private double weight;
    private double height;
    private Gender gender;
    private int activityLevel;
}
