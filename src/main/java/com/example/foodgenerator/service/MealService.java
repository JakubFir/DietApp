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
        User userToAddMealTo = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException(" "));
        ingredientsService.checkIfIngredientsAreInDB(mealDto.ingredientsList());
        MealDiary mealDiary = mealDiaryService.getUserMealDiary(userId,mealDto.mealDate());
        Meal meal = calculateMealCalories(mealDto);
        meal.setMealDiary(mealDiary);
        mealDiary.getMeals().add(meal);
        mealDiary.setRemainingCalories(mealDiary.getRemainingCalories() - meal.getCalories());

        mealRepository.save(meal);
        mealDiaryRepository.save(mealDiary);
        userRepository.save(userToAddMealTo);
    }




    private Meal calculateMealCalories(MealDto mealDto) {
        double calories = 0;
        double fat = 0;
        double protein = 0;
        double carbs = 0;

        List<IngredientsDto> updatedIngredientsList = new ArrayList<>();

        for (IngredientsDto ingredientToCount : mealDto.ingredientsList()) {
            Ingredients ingredients = ingredientsRepository.findByName(ingredientToCount.name());
            double ingredientCalories = (ingredients.getCalories() / 100) * ingredientToCount.weight();
            double ingredientFat = (ingredients.getFat() / 100) * ingredientToCount.weight();
            double ingredientProtein = (ingredients.getProtein() / 100) * ingredientToCount.weight();
            double ingredientCarbs = (ingredients.getCarbs() / 100) * ingredientToCount.weight();

            calories += ingredientCalories;
            fat += ingredientFat;
            protein += ingredientProtein;
            carbs += ingredientCarbs;

            IngredientsDto updatedIngredient = new IngredientsDto(
                    ingredientToCount.name(),
                    ingredientCalories,
                    ingredientFat,
                    ingredientProtein,
                    ingredientCarbs,
                    ingredientToCount.weight()
            );

            updatedIngredientsList.add(updatedIngredient);
        }

        List<MealIngredient> mealIngredients = mealIngredientMapper.mapToMealIngredientList(updatedIngredientsList);
        Meal meal = mealMapper.mapToMeal(mealDto);
        meal.setIngredients(mealIngredients);
        meal.setCalories(calories);
        meal.setFat(fat);
        meal.setProtein(protein);
        meal.setCarbs(carbs);

        return meal;
    }

    public List<MealDiary> getUserMeals(Long userId) {
        return userRepository.findById(userId).orElseThrow().getMealDiary();
    }
}
