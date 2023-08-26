package com.example.foodgenerator.edamam.config.client;

import com.example.foodgenerator.dto.edamamDto.Food;
import com.example.foodgenerator.dto.edamamDto.Nutrients;
import com.example.foodgenerator.dto.edamamDto.ParsedRoot;
import com.example.foodgenerator.edamam.config.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@Service
public class EdamamClient {
    private final RestTemplate restTemplate;
    private final EdamamConfig edamamConfig;

    public Nutrients getEdamamNutrients() {
        URI uri = UriComponentsBuilder.fromHttpUrl(edamamConfig.getEdamamUrl())
                .queryParam("app_id", edamamConfig.getEdamamAppId())
                .queryParam("app_key", edamamConfig.getEdamamAppKey())
                .queryParam("ingr", "apple")
                .build()
                .encode()
                .toUri();

        try {
            ResponseEntity<ParsedRoot> edamamNutritionsResponseEntity =
                    restTemplate.exchange(uri, HttpMethod.GET, null, ParsedRoot.class);

            ParsedRoot parsedRoot = edamamNutritionsResponseEntity.getBody();
            if (parsedRoot != null && parsedRoot.getParsed() != null && !parsedRoot.getParsed().isEmpty()) {
                Food food = parsedRoot.getParsed().get(0).getFood();
                if (food != null && food.getNutrients() != null) {
                    return food.getNutrients();
                }
            }
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return new Nutrients();
    }
}

