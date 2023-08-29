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
    @OneToMany(cascade = CascadeType.ALL)
    private List<MealIngredient> ingredients;
    private double calories;
    private double fat;
    private double protein;
    private double carbs;
    @ManyToOne
    @JoinColumn(name = "meal_diary_id")
    private MealDiary mealDiary;

    public Meal(String mealName, List<MealIngredient> ingredients, double calories, double fat, double protein, double carbs) {
        this.mealName = mealName;
        this.ingredients = ingredients;
        this.calories = calories;
        this.fat = fat;
        this.protein = protein;
        this.carbs = carbs;
    }
}
