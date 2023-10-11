package com.example.foodgenerator.service.edamam.client;

import com.example.foodgenerator.controller.MealDiaryController;
import com.example.foodgenerator.domain.Ingredients;
import com.example.foodgenerator.dto.edamamDto.Food;
import com.example.foodgenerator.dto.edamamDto.Nutrients;
import com.example.foodgenerator.dto.edamamDto.ParsedRoot;
import com.example.foodgenerator.exceptions.IngredientNotFoundException;
import com.example.foodgenerator.service.edamam.config.EdamamConfig;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EdamamClient {
    private final static Logger LOGGER = LoggerFactory.getLogger(EdamamClient.class);

    private final WebClient webClient;
    private final EdamamConfig edamamConfig;

    public Mono<Nutrients> getEdamamNutrients(String product) {
        LOGGER.trace("Starting call to edamam client");
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/food-database/v2/parser")
                            .queryParam("app_id", edamamConfig.getEdamamAppId())
                            .queryParam("app_key", edamamConfig.getEdamamAppKey())
                            .queryParam("ingr", product)
                            .build())
                    .retrieve()
                    .bodyToMono(ParsedRoot.class)
                    .flatMap(parsedRoot -> {
                        if (parsedRoot != null && !parsedRoot.parsed().isEmpty()) {
                            Food food = parsedRoot.parsed().get(0).food();
                            if (food != null && food.nutrients() != null) {
                                LOGGER.info("Succesfully retrieved edamam nutrients");
                                return Mono.just(food.nutrients());
                            }
                        }
                        LOGGER.warn("And error occurred during call to edamam client");
                        return Mono.error(new IngredientNotFoundException("Ingridient Not found"));
                    });
    }

}
