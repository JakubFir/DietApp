package com.example.foodgenerator.controller;

import com.example.foodgenerator.dto.MealDto;
import com.example.foodgenerator.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "api/v1/meals")
@RestController
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    @PostMapping(path = "/{userId}")
    public void addMealToUserMealList(@RequestBody MealDto mealDto, @PathVariable Long userId) {
        mealService.addMealToUserMealList(mealDto, userId);
    }

}
