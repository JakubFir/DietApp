package com.example.foodgenerator.controller;

import com.example.foodgenerator.domain.MealDiary;
import com.example.foodgenerator.dto.MealDiaryDto;
import com.example.foodgenerator.dto.MealDto;
import com.example.foodgenerator.service.MealService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;

@RequestMapping(path = "api/v1/meals")
@RestController
@RequiredArgsConstructor
public class MealController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MealController.class);

    private final MealService mealService;
    @PostMapping(path = "/{userId}")
    public ResponseEntity<Void> addMealToUserMealList(@RequestBody @NotNull @NotEmpty MealDto mealDto, @PathVariable Long userId) {
        LOGGER.info("Starting request for adding meal to user meal list for user with Id: " + userId + " meal: " + mealDto);
        mealService.addMealToUserMealDiary(mealDto, userId);
        return ResponseEntity.ok().build();
    }
    @PutMapping(path =  "/{userId}")
    public ResponseEntity<Void> updateUserMeal(@RequestBody MealDto mealDto, @PathVariable Long userId){
        LOGGER.info("Starting request for updating meal for user with id: " + userId);
        mealService.updateUserMeal(mealDto,userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{userId}/{date}")
    public ResponseEntity <Void> deleteMeal(@PathVariable Long userId, @PathVariable LocalDate date, @RequestBody MealDto mealDto){
        LOGGER.info("Starting request for deleting meal for user with id: " + userId);
        mealService.deleteMeal(userId,date,mealDto);
       return ResponseEntity.ok().build();
    }



}
