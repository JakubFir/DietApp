package com.example.foodgenerator.service;

import com.example.foodgenerator.controller.AuthenticateController;
import com.example.foodgenerator.domain.Ingredients;
import com.example.foodgenerator.dto.IngredientsDto;
import com.example.foodgenerator.dto.edamamDto.Nutrients;
import com.example.foodgenerator.exceptions.IngredientNotFoundException;
import com.example.foodgenerator.exceptions.InvalidIngredientInput;
import com.example.foodgenerator.repository.IngredientsRepository;
import com.example.foodgenerator.service.edamam.client.EdamamClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IngredientsService.class);

    private final IngredientsRepository ingredientsRepository;
    private final EdamamClient edamamClient;

    public List<Ingredients> getAll() {
        LOGGER.info("Starting getAll ingredients method");
        return ingredientsRepository.findAll();
    }

    public void checkIfIngredientsAreInDB(List<IngredientsDto> ingredientsList) {
        LOGGER.trace("Starting checking if Ingredients are in database for " + ingredientsList);
        if (ingredientsList == null || ingredientsList.isEmpty()) {
            LOGGER.warn("ingredient list is null or empty");
            throw new IngredientNotFoundException("Ingredients list cannot be empty");
        }
        ingredientsList.forEach(ingredientsDto -> {
            validateIngredient(ingredientsDto);
            if (!ingredientsRepository.existsByName(ingredientsDto.name())) {
                LOGGER.trace(ingredientsDto.name() + " doesn't exists in database, moving to EdamamClient");
                Mono<Nutrients> nutrientsMono = edamamClient.getEdamamNutrients(ingredientsDto.name());
                Nutrients nutrients = nutrientsMono.block();
                Ingredients ingredients = new Ingredients(
                        ingredientsDto.name(),
                        nutrients.calories(),
                        nutrients.fat(),
                        nutrients.protein(),
                        nutrients.carbs()
                );
                ingredientsRepository.save(ingredients);
                LOGGER.info(ingredients + " succesfully saved to database");
            }
        });
    }

    private void validateIngredient(IngredientsDto ingredientsDto) {
        LOGGER.trace("Starting validating ingredient: " + ingredientsDto );
        if (ingredientsDto.weight() <= 0) {
            LOGGER.warn("Invalid ingridient request");
            throw new InvalidIngredientInput("Ingredient weight has to be greater than 0");
        }
        LOGGER.info("Ingridient succesfully validated");
    }
}