package com.example.foodgenerator.controller;

import com.example.foodgenerator.domain.Ingredients;
import com.example.foodgenerator.service.DietService;
import com.example.foodgenerator.service.IngredientsService;
import com.example.foodgenerator.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(IngredientsController.class)
@AutoConfigureMockMvc
class IngredientsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IngredientsService ingredientsService;
    @MockBean
    private JwtService jwtService;

    @Test
    @WithMockUser
    void getIngredients() throws Exception {
        List<Ingredients> res = new ArrayList<>();
        res.add(new Ingredients("test",1,1,1,1));

        when(ingredientsService.getAll()).thenReturn(res);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/ingredients"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("test"));

    }
}