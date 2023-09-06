package com.example.foodgenerator.dto;

import com.example.foodgenerator.domain.User;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DietDto {
    private String name;
    private double protein;
    private double fat;
    private double carbs;

}
