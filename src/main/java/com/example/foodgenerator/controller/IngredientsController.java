package com.example.foodgenerator.controller;

import com.example.foodgenerator.domain.Ingredients;
import com.example.foodgenerator.service.IngredientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/ingredients")
@RequiredArgsConstructor
public class IngredientsController {

    private final IngredientsService ingredientsService;


    @GetMapping()
    public List<Ingredients> getIngredients(){
        return ingredientsService.getAll();
    }
}
