package com.example.foodgenerator.service;

import com.example.foodgenerator.domain.*;
import com.example.foodgenerator.dto.IngredientsDto;
import com.example.foodgenerator.dto.MealDto;
import com.example.foodgenerator.mapper.MealIngredientMapper;
import com.example.foodgenerator.mapper.MealMapper;
import com.example.foodgenerator.repository.IngredientsRepository;
import com.example.foodgenerator.repository.MealDiaryRepository;
import com.example.foodgenerator.repository.MealRepository;
import com.example.foodgenerator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MealService {

    private final UserRepository userRepository;
    private final MealRepository mealRepository;
    private final MealDiaryService mealDiaryService;
    private final IngredientsRepository ingredientsRepository;
    private final IngredientsService ingredientsService;
    private final MealIngredientMapper mealIngredientMapper;
    private final MealDiaryRepository mealDiaryRepository;
    private final MealMapper mealMapper;

    public void addMealToUserMealDiary(MealDto mealDto, Long userId) {
        User userToAddMealTo = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User with given ID doesn't exists "));
        ingredientsService.checkIfIngredientsAreInDB(mealDto.ingredientsList());
        MealDiary mealDiary = mealDiaryService.getUserMealDiary(userId, mealDto.mealDate());
        Meal meal = calculateMealCalories(mealDto);
        meal.setMealDiary(mealDiary);
        mealDiary.getMeals().add(meal);
        mealDiary.setRemainingCalories(mealDiary.getRemainingCalories() - meal.getCalories());

        mealRepository.save(meal);
        mealDiaryRepository.save(mealDiary);
        userRepository.save(userToAddMealTo);
    }

    private Meal calculateMealCalories(MealDto mealDto) {
        Meal meal = mealMapper.mapToMeal(mealDto);
        List<IngredientsDto>  updatedIngredientsList =  mealDto.ingredientsList().stream()
                .map(ingredientToCount -> {
                    Ingredients ingredients = ingredientsRepository.findByName(ingredientToCount.name());
                    double ingredientCalories = (ingredients.getCalories() / 100) * ingredientToCount.weight();
                    double ingredientFat = (ingredients.getFat() / 100) * ingredientToCount.weight();
                    double ingredientProtein = (ingredients.getProtein() / 100) * ingredientToCount.weight();
                    double ingredientCarbs = (ingredients.getCarbs() / 100) * ingredientToCount.weight();

                    meal.setCalories(meal.getCalories() + ingredientCalories);
                    meal.setFat(meal.getFat() + ingredientFat);
                    meal.setProtein(meal.getProtein() + ingredientProtein);
                    meal.setCarbs(meal.getCarbs() + ingredientCarbs);


                    return new IngredientsDto(
                            ingredientToCount.name(),
                            ingredientCalories,
                            ingredientFat,
                            ingredientProtein,
                            ingredientCarbs,
                            ingredientToCount.weight());

                }).collect(Collectors.toList());

        meal.setIngredients(mealIngredientMapper.mapToMealIngredientList(updatedIngredientsList));

        return meal;
    }

    public List<MealDiary> getUserMeals(Long userId) {
        return userRepository.findById(userId).orElseThrow().getMealDiary();
    }
}
