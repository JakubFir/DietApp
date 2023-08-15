package com.example.foodgenerator.repository;

import com.example.foodgenerator.domain.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Long> {
}
