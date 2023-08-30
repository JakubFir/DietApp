package com.example.foodgenerator.dto;
import com.example.foodgenerator.domain.Gender;
import lombok.Data;

@Data
public class RegisterRequest {

    private String username;
    private String email;
    private String password;
    private int age;
    private double weight;
    private double height;
    private Gender gender;
    private int activityLevel;
}
