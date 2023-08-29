package com.example.foodgenerator.controller;

import com.example.foodgenerator.dto.MealDiaryDto;
import com.example.foodgenerator.mapper.MealDiaryMapper;
import com.example.foodgenerator.repository.MealDiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/dairy")
public class MealDiaryController {

    private final MealDiaryRepository mealDiaryRepository;
    private final MealDiaryMapper mealDiaryMapper;

    @GetMapping()
    public List<MealDiaryDto> mealDiaryDTO(){
        return mealDiaryMapper.mapToListMealDiaryDTO(mealDiaryRepository.findAll());
    }
}
