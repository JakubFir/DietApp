package com.example.foodgenerator.mapper;

import com.example.foodgenerator.domain.Ingredients;
import com.example.foodgenerator.dto.IngredientsDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class IngredientsMapper {
    public List<Ingredients> mapToIngredientList(List<IngredientsDto> ingredientsList) {
        return ingredientsList.stream().map(this::mapToIngredient).collect(Collectors.toList());
    }

    public Ingredients mapToIngredient(IngredientsDto ingredientsDto) {
        return new Ingredients(
                ingredientsDto.getName()
        );
    }
}
