package com.example.foodgenerator.repository;

import com.example.foodgenerator.domain.Ingredients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientsRepository extends JpaRepository<Ingredients, Long> {

    boolean existsByName(String name);
}