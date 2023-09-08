package com.example.foodgenerator.service.edamam.client;

import com.example.foodgenerator.dto.edamamDto.Food;
import com.example.foodgenerator.dto.edamamDto.Nutrients;
import com.example.foodgenerator.dto.edamamDto.ParsedRoot;
import com.example.foodgenerator.service.edamam.config.EdamamConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
@Service
@RequiredArgsConstructor
public class EdamamClient {
    private final RestTemplate restTemplate;
    private final EdamamConfig edamamConfig;

    public Nutrients getEdamamNutrients(String product) {
        URI uri = UriComponentsBuilder.fromHttpUrl(edamamConfig.getEdamamUrl())
                .queryParam("app_id", edamamConfig.getEdamamAppId())
                .queryParam("app_key", edamamConfig.getEdamamAppKey())
                .queryParam("ingr", product)
                .build()
                .encode()
                .toUri();
        System.out.println(uri);
        try {
            ResponseEntity<ParsedRoot> edamamNutritionsResponseEntity =
                    restTemplate.exchange(uri, HttpMethod.GET, null, ParsedRoot.class);
            ParsedRoot parsedRoot = edamamNutritionsResponseEntity.getBody();
            if (parsedRoot != null && parsedRoot.parsed() != null && !parsedRoot.parsed().isEmpty()) {
                Food food = parsedRoot.parsed().get(0).food();
                if (food != null && food.nutrients() != null) {
                    return food.nutrients();
                }
            }
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return new Nutrients(0,0,0,0);
    }
}

