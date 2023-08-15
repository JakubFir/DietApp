package com.example.foodgenerator.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mealName;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Ingredients> ingredients;
    private Long calories;
    private Long fat;
    private Long protein;
    private Long carbs;

    public Meal(String mealName, List<Ingredients> ingredients, Long calories, Long fat, Long protein, Long carbs) {
        this.mealName = mealName;
        this.ingredients = ingredients;
        this.calories = calories;
        this.fat = fat;
        this.protein = protein;
        this.carbs = carbs;
    }
}
