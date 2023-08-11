package com.example.foodgenerator.domain;

import com.example.foodgenerator.domain.dto.Ingredients;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Meal {
    @Id
    private Long id;
    private String mealName;
    @OneToMany
    private List<Ingredients> ingredients;
    private Long calories;
    private Long fat;
    private Long protein;
    private Long carbs;


}
