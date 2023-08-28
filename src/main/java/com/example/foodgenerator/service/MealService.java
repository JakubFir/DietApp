package com.example.foodgenerator.service;

import com.example.foodgenerator.domain.Ingredients;
import com.example.foodgenerator.domain.Meal;
import com.example.foodgenerator.domain.MealIngredient;
import com.example.foodgenerator.domain.User;
import com.example.foodgenerator.dto.IngredientsDto;
import com.example.foodgenerator.dto.MealDto;
import com.example.foodgenerator.dto.edamamDto.Nutrients;
import com.example.foodgenerator.mapper.MealIngredientMapper;
import com.example.foodgenerator.mapper.MealMapper;
import com.example.foodgenerator.repository.IngredientsRepository;
import com.example.foodgenerator.repository.MealRepository;
import com.example.foodgenerator.repository.UserRepository;
import com.example.foodgenerator.service.edamam.client.EdamamClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealService {

    private final UserRepository userRepository;
    private final MealRepository mealRepository;
    private final EdamamClient edamamClient;
    private final IngredientsRepository ingredientsRepository;
    private final MealIngredientMapper mealIngredientMapper;
    private final MealMapper mealMapper;

    public void addMealToUserMealList(MealDto mealDto, Long userId) {
        User userToAddMealTo = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException(" "));
        checkIfIngredientsAreInDB(mealDto.getIngredientsList());
        Meal meal = calculateMealCalories(mealDto);

        userToAddMealTo.getMeals().add(meal);
        System.out.println(meal);
        mealRepository.save(meal);
        userRepository.save(userToAddMealTo);
    }

    private void checkIfIngredientsAreInDB(List<IngredientsDto> ingredientsList) {
        for (IngredientsDto ingredientDto : ingredientsList) {
            if (!ingredientsRepository.existsByName(ingredientDto.getName())) {
                Nutrients nutrients = edamamClient.getEdamamNutrients(ingredientDto.getName());
                Ingredients ingredientToSave = new Ingredients(
                        ingredientDto.getName(),
                        nutrients.getCalories(),
                        nutrients.getFat(),
                        nutrients.getProtein(),
                        nutrients.getCarbs()
                );
                System.out.println(ingredientToSave);
                ingredientsRepository.save(ingredientToSave);
            }
        }
    }


    private Meal calculateMealCalories(MealDto mealDto) {
        double calories = 0;
        double fat = 0;
        double protein = 0;
        double carbs = 0;

        for (IngredientsDto ingredientToCount : mealDto.getIngredientsList()) {
            Ingredients ingredients = ingredientsRepository.findByName(ingredientToCount.getName());
            double ingredientCalories = (ingredients.getCalories() / 100) * ingredientToCount.getWeight();
            double ingredientFat = (ingredients.getFat() / 100) * ingredientToCount.getWeight();
            double ingredientProtein = (ingredients.getProtein() / 100) * ingredientToCount.getWeight();
            double ingredientCarbs = (ingredients.getCarbs() / 100) * ingredientToCount.getWeight();

            calories += ingredientCalories;
            fat += ingredientFat;
            protein += ingredientProtein;
            carbs += ingredientCarbs;

            ingredientToCount.setCalories(ingredientCalories);
            ingredientToCount.setFat(ingredientFat);
            ingredientToCount.setProtein(ingredientProtein);
            ingredientToCount.setCarbs(ingredientCarbs);
        }

        List<MealIngredient> mealIngredients = mealIngredientMapper.mapToMealIngredientList(mealDto.getIngredientsList());
        Meal meal = mealMapper.mapToMeal(mealDto);
        meal.setIngredients(mealIngredients);
        meal.setCalories(calories);
        meal.setFat(fat);
        meal.setProtein(protein);
        meal.setCarbs(carbs);

        return meal;
    }

    public List<Meal> getUserMeals(Long userId) {
        return userRepository.findById(userId).orElseThrow().getMeals();
    }
}
