package com.example.foodgenerator.controller;


import com.example.foodgenerator.service.DietService;

import com.example.foodgenerator.service.dietStrategy.DietType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/diets")
public class DietController {

    private final DietService dietService;

    @GetMapping("{userId}/default")
    public void setDefaultDiet(@PathVariable Long userId){
        dietService.setUserDiet(DietType.DEFAULT, userId);
    }
}
