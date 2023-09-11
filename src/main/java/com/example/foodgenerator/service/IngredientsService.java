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

        for (IngredientsDto ingredientDto : ingredientsList) {
            if (!ingredientsRepository.existsByName(ingredientDto.name())) {
                Nutrients nutrients = edamamClient.getEdamamNutrients(ingredientDto.name()).block();
                Ingredients ingredientToSave = new Ingredients(
                        ingredientDto.name(),
                        nutrients.calories(),
                        nutrients.fat(),
                        nutrients.protein(),
                        nutrients.carbs()
                );
                ingredientsRepository.save(ingredientToSave);

            }
        }
    }
}
