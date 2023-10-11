package com.example.foodgenerator.controller;

import com.example.foodgenerator.domain.Ingredients;
import com.example.foodgenerator.service.IngredientsService;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/ingredients")
@RequiredArgsConstructor
public class IngredientsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IngredientsController.class);

    private final IngredientsService ingredientsService;
    @GetMapping()
    public ResponseEntity<List<Ingredients>> getIngredients(){
        LOGGER.info("Starting get Ingredients request");
        return ResponseEntity.ok(ingredientsService.getAll());
    }
}
