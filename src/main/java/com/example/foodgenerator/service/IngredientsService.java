package com.example.foodgenerator.service;

import com.example.foodgenerator.domain.Ingredients;
import com.example.foodgenerator.dto.IngredientsDto;
import com.example.foodgenerator.dto.edamamDto.Nutrients;
import com.example.foodgenerator.repository.IngredientsRepository;
import com.example.foodgenerator.service.edamam.client.EdamamClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
        ingredientsList.forEach(ingredientsDto -> {
            if (!ingredientsRepository.existsByName(ingredientsDto.name())) {
                Mono<Nutrients> nutrientsMono = edamamClient.getEdamamNutrients(ingredientsDto.name());
                Nutrients nutrients = nutrientsMono.block();
                Ingredients ingredients = new Ingredients(
                            ingredientsDto.name(),
                            nutrients.calories(),
                            nutrients.fat(),
                            nutrients.protein(),
                            nutrients.carbs()
                    );
                System.out.println(ingredients);
                    ingredientsRepository.save(ingredients);
            }
        });
    }
}