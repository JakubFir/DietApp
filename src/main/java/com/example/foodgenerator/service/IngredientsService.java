package com.example.foodgenerator.service;

import com.example.foodgenerator.domain.Ingredients;
import com.example.foodgenerator.dto.IngredientsDto;
import com.example.foodgenerator.dto.edamamDto.Nutrients;
import com.example.foodgenerator.repository.IngredientsRepository;
import com.example.foodgenerator.service.edamam.client.EdamamClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientsService {

    private final IngredientsRepository ingredientsRepository;
    private final EdamamClient edamamClient;

    public List<Ingredients> getAll() {
        return ingredientsRepository.findAll();
    }

    public void checkIfIngredientsAreInDB(List<IngredientsDto> ingredientsList) {
        ingredientsList.stream()
                .filter(ingredientsDto -> !ingredientsRepository.existsByName(ingredientsDto.name()))
                .forEach(ingredientsDto -> {
                    Nutrients nutrients = edamamClient.getEdamamNutrients(ingredientsDto.name()).block();
                    Ingredients ingredientToSave = new Ingredients(
                            ingredientsDto.name(),
                            nutrients.calories(),
                            nutrients.fat(),
                            nutrients.protein(),
                            nutrients.carbs());
                    ingredientsRepository.save(ingredientToSave);
                });
    }
}
