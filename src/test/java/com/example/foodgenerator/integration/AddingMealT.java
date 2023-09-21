package com.example.foodgenerator.integration;

import com.example.foodgenerator.domain.Gender;
import com.example.foodgenerator.domain.User;
import com.example.foodgenerator.dto.IngredientsDto;
import com.example.foodgenerator.dto.MealDto;
import com.example.foodgenerator.dto.RegisterRequest;
import com.example.foodgenerator.repository.IngredientsRepository;
import com.example.foodgenerator.repository.UserRepository;
import com.example.foodgenerator.service.JwtService;
import com.example.foodgenerator.testcontainer.Testcontainers;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddingMealT extends Testcontainers {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IngredientsRepository ingredientsRepository;


    @Test
     void canAddMeal() {
        RegisterRequest request = new RegisterRequest("test1", "test1", "test1", 12, 40, 120, Gender.MALE, 1);

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

        webTestClient.post()
                .uri("/api/v1/meals/{userId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(mealDto)
                .exchange()
                .expectStatus().isOk();


        Optional<User> user = userRepository.findById(1L);

        assertThat(user).isPresent();
        assertThat(ingredientsRepository.existsByName("Banana")).isTrue();
    }
}
