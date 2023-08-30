package com.example.foodgenerator.controller;

import com.example.foodgenerator.domain.MealDiary;
import com.example.foodgenerator.dto.MealDto;
import com.example.foodgenerator.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RequestMapping(path = "api/v1/meals")
@RestController
@RequiredArgsConstructor
public class MealController {
    private final MealService mealService;
    @PostMapping(path = "/{userId}")
    public ResponseEntity<Void> addMealToUserMealList(@RequestBody MealDto mealDto, @PathVariable Long userId) {
        mealService.addMealToUserMealDiary(mealDto, userId);
        return ResponseEntity.ok().build();
    }
    @GetMapping(path = "/{userId}")
    public ResponseEntity<List<MealDiary>> getUserMeals(@PathVariable Long userId){
       return ResponseEntity.ok(mealService.getUserMeals(userId));
    }



}
