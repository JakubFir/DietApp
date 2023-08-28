package com.example.foodgenerator.repository;

import com.example.foodgenerator.domain.Ingredients;
import com.example.foodgenerator.dto.IngredientsDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientsRepository extends JpaRepository<Ingredients, Long> {

    boolean existsByName(String name);

    Ingredients findByName(String name);
}
