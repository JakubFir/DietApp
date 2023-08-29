package com.example.foodgenerator.repository;

import com.example.foodgenerator.domain.MealIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealIngredientRepository extends JpaRepository<MealIngredient, Long> {
}