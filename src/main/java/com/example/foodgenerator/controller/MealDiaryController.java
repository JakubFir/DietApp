package com.example.foodgenerator.controller;

import com.example.foodgenerator.domain.MealDiary;
import com.example.foodgenerator.dto.MealDiaryDto;
import com.example.foodgenerator.mapper.MealDiaryMapper;
import com.example.foodgenerator.repository.MealDiaryRepository;
import com.example.foodgenerator.service.MealDiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/dairy")
public class MealDiaryController {

    private final MealDiaryService mealDiaryService;
    private final MealDiaryMapper mealDiaryMapper;

    @GetMapping(path = "/{userId}/{date}")
    public ResponseEntity<MealDiaryDto> getUserMealDiary(@PathVariable Long userId, @PathVariable LocalDate date){
        return ResponseEntity.ok(mealDiaryMapper.mapToMealDiaryDTO(mealDiaryService.getUserMealDiary(userId,date)));
    }
}
