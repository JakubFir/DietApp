package com.example.foodgenerator.service;

import com.example.foodgenerator.domain.Ingredients;
import com.example.foodgenerator.domain.Meal;
import com.example.foodgenerator.domain.User;
import com.example.foodgenerator.dto.IngredientsDto;
import com.example.foodgenerator.dto.MealDto;
import com.example.foodgenerator.mapper.IngredientsMapper;
import com.example.foodgenerator.mapper.MealMapper;
import com.example.foodgenerator.repository.IngredientsRepository;
import com.example.foodgenerator.repository.MealRepository;
import com.example.foodgenerator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealService {

    private final UserRepository userRepository;
    private final MealRepository mealRepository;
    private final IngredientsRepository ingredientsRepository;
    private final IngredientsMapper ingredientsMapper;
    private final MealMapper mealMapper;
    public void addMealToUserMealList(MealDto mealDto, Long userId) {
       User userToAddMealTo = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException(" "));
       checkIfIngredientsAreSaved(ingredientsMapper.mapToIngredientList(mealDto.getIngredientsList()));


       Meal mappedMeal = mealMapper.mapToMeal(mealDto);
       userToAddMealTo.getMeals().add(mappedMeal);

       mealRepository.save(mappedMeal);
       userRepository.save(userToAddMealTo);
    }

    private void checkIfIngredientsAreSaved(List<Ingredients> ingredients) {
        for(Ingredients ingredients1 : ingredients){
            if(!ingredientsRepository.existsByName(ingredients1.getName())){
                System.out.println(ingredients1.getName());
                ingredientsRepository.save(ingredients1);
            }
        }
    }
}
