package com.example.foodgenerator.service.edamam.client;

import com.example.foodgenerator.dto.edamamDto.Food;
import com.example.foodgenerator.dto.edamamDto.Nutrients;
import com.example.foodgenerator.dto.edamamDto.ParsedRoot;
import com.example.foodgenerator.service.edamam.config.EdamamConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EdamamClient {
    private final WebClient webClient;
    private final EdamamConfig edamamConfig;

    public Mono<Nutrients> getEdamamNutrients(String product) {
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
                                return Mono.just(food.nutrients());
                            }
                        }
                        return Mono.just(new Nutrients(1, 0, 0, 0));
                    });
    }

}
