package com.example.foodgenerator.controller;


import com.example.foodgenerator.dto.MealDiaryDto;
import com.example.foodgenerator.mapper.MealDiaryMapper;
import com.example.foodgenerator.service.MealDiaryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/dairy")
public class MealDiaryController {
    private final static Logger LOGGER = LoggerFactory.getLogger(MealDiaryController.class);
    private final MealDiaryService mealDiaryService;
    private final MealDiaryMapper mealDiaryMapper;

    @GetMapping(path = "/{userId}/{date}")
    @Cacheable(value = "mealDiary", key = "#date")
    public MealDiaryDto getUserMealDiary(@PathVariable Long userId, @PathVariable LocalDate date){
        LOGGER.info("Starting request to get user meal diary for user with id: " + userId);
        return mealDiaryMapper.mapToMealDiaryDTO(mealDiaryService.getUserMealDiary(userId,date));
    }
}
