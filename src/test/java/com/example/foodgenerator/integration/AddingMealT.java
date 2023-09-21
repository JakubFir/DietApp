package com.example.foodgenerator.integration;

import com.example.foodgenerator.domain.Gender;
import com.example.foodgenerator.domain.User;
import com.example.foodgenerator.dto.IngredientsDto;
import com.example.foodgenerator.dto.MealDto;
import com.example.foodgenerator.dto.RegisterRequest;
import com.example.foodgenerator.repository.UserRepository;
import com.example.foodgenerator.service.JwtService;
import com.example.foodgenerator.testcontainer.Testcontainers;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;
import wiremock.org.checkerframework.checker.units.qual.A;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddingMealT extends Testcontainers {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();
    @DynamicPropertySource
    static void configureProteries(DynamicPropertyRegistry registry){
        registry.add("https://api.edamam.com", wireMockServer::baseUrl);
    }

    @Test
     void canAddMeal() {
        RegisterRequest request = new RegisterRequest("test1", "test1", "test1", 12, 40, 120, Gender.MALE, 1);

        wireMockServer.stubFor(WireMock.get(urlMatching("/api/food-database/v2/parser/.*"))
                .willReturn(aResponse()
                        .withStatus(500)));



        webTestClient.post()
                .uri("/api/v1/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk();


        List<IngredientsDto> list = new ArrayList<>();
        IngredientsDto ingredients = new IngredientsDto("Banana", 10, 10, 10, 10, 100);
        list.add(ingredients);
        MealDto mealDto = new MealDto("Dinner", 0, 0, 0, 0, LocalDate.now(),list);
        System.out.println(mealDto);
        webTestClient.post()
                .uri("/api/v1/meals/{userId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(mealDto)
                .exchange()
                .expectStatus().isOk();
        System.out.println(wireMockServer.baseUrl());
        System.out.println(wireMockServer.getPort());



    }
}
