package com.example.foodgenerator.service;

import com.example.foodgenerator.domain.Ingredients;
import com.example.foodgenerator.dto.IngredientsDto;
import com.example.foodgenerator.dto.edamamDto.Nutrients;
import com.example.foodgenerator.repository.IngredientsRepository;
import com.example.foodgenerator.service.edamam.client.EdamamClient;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientsServiceTest {
    @Mock
    private IngredientsRepository ingredientsRepository;
    @Mock
    private EdamamClient edamamClient;

    private IngredientsService ingredientsService;

    @BeforeEach
    void setUp() {
        ingredientsService = new IngredientsService(ingredientsRepository, edamamClient);
    }

    @Test
    void getAll() {
        //Given
        List<Ingredients> ingredients = new ArrayList<>();
        Ingredients ingredient1 = new Ingredients("test",2,2,2,2);
        ingredients.add(ingredient1);
        when(ingredientsRepository.findAll()).thenReturn(ingredients);

        //When
        List<Ingredients> result = ingredientsService.getAll();

        //Then
        assertThat(result.size()).isEqualTo(1);
    }


    @Test
    void checkIfIngredientsAreInDB() {
        //Given
        List<IngredientsDto> ingredientsDtoList = new ArrayList<>();
        IngredientsDto ingredientsDto = new IngredientsDto("test", 2, 2, 2, 2, 2);
        ingredientsDtoList.add(ingredientsDto);

        when(ingredientsRepository.existsByName(any())).thenReturn(false);
        when(edamamClient.getEdamamNutrients("test")).thenReturn(Mono.just(new Nutrients(100, 10, 20, 5)));

        //When
        ingredientsService.checkIfIngredientsAreInDB(ingredientsDtoList);

        //Then
        verify(ingredientsRepository, times(1)).save(any());
    }
}