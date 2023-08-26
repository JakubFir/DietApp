package com.example.foodgenerator.controller;

import com.example.foodgenerator.dto.MealDto;
import com.example.foodgenerator.dto.edamamDto.Nutrients;
import com.example.foodgenerator.edamam.config.client.EdamamClient;
import com.example.foodgenerator.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "api/v1/meals")
@RestController
@RequiredArgsConstructor
public class MealController {
     private final EdamamClient edamamClient;
    private final MealService mealService;
    @PostMapping(path = "/{userId}")
    public void addMealToUserMealList(@RequestBody MealDto mealDto, @PathVariable Long userId) {

        mealService.addMealToUserMealList(mealDto, userId);
    }
    @GetMapping(path = "/test")
    public Nutrients edamamNutritions(){
       return edamamClient.getEdamamNutrients();
    }

}
