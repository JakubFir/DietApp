package com.example.foodgenerator.controller;

import com.example.foodgenerator.service.DietService;
import com.example.foodgenerator.service.JwtService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wiremock.org.checkerframework.checker.units.qual.A;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebMvcTest(DietController.class)
@AutoConfigureMockMvc
class DietControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DietService dietService;
    @MockBean
    private JwtService jwtService;
    @Test
    @WithMockUser
    void setDefaultDiet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/diets/{userId}/default", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(dietService, times(1)).setUserDiet(any(), any());

    }
}