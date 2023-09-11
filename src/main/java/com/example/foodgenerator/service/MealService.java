package com.example.foodgenerator.service;

import com.example.foodgenerator.domain.*;
import com.example.foodgenerator.dto.IngredientsDto;
import com.example.foodgenerator.dto.MealDto;
import com.example.foodgenerator.dto.edamamDto.Nutrients;
import com.example.foodgenerator.mapper.MealIngredientMapper;
import com.example.foodgenerator.mapper.MealMapper;
import com.example.foodgenerator.repository.IngredientsRepository;
import com.example.foodgenerator.repository.MealDiaryRepository;
import com.example.foodgenerator.repository.MealRepository;
import com.example.foodgenerator.repository.UserRepository;
import com.example.foodgenerator.service.edamam.client.EdamamClient;
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
    private final EdamamClient edamamClient;
    private final IngredientsRepository ingredientsRepository;
    private final MealIngredientMapper mealIngredientMapper;
    private final MealDiaryRepository mealDiaryRepository;
    private final MealMapper mealMapper;

    public void addMealToUserMealDiary(MealDto mealDto, Long userId) {
        User userToAddMealTo = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException(" "));
        checkIfIngredientsAreInDB(mealDto.ingredientsList());
        MealDiary mealDiary = getUserMealDiary(userToAddMealTo,mealDto);
        Meal meal = calculateMealCalories(mealDto);
        meal.setMealDiary(mealDiary);
        mealDiary.getMeals().add(meal);
        mealDiary.setRemainingCalories(mealDiary.getRemainingCalories() - meal.getCalories());
        System.out.println(mealDiary.getRemainingCalories());
        mealRepository.save(meal);
        mealDiaryRepository.save(mealDiary);
        userRepository.save(userToAddMealTo);
    }

    private MealDiary getUserMealDiary(User userToAddMealTo, MealDto mealDto) {
        MealDiary mealDiary = mealDiaryRepository.findByUserAndDate(userToAddMealTo, mealDto.mealDate()).orElse(null);
        if (mealDiary == null) {
            mealDiary = new MealDiary();
            mealDiary.setUser(userToAddMealTo);
            mealDiary.setDate(mealDto.mealDate());
            mealDiary.setRemainingCalories(userToAddMealTo.getCaloricDemand());
            mealDiary.setCaloricDemand(userToAddMealTo.getCaloricDemand());
            mealDiaryRepository.save(mealDiary);
            userToAddMealTo.getMealDiary().add(mealDiary);
        }
        return mealDiary;
    }

    private void checkIfIngredientsAreInDB(List<IngredientsDto> ingredientsList) {
        for (IngredientsDto ingredientDto : ingredientsList) {
            if (!ingredientsRepository.existsByName(ingredientDto.name())) {
                Nutrients nutrients = edamamClient.getEdamamNutrients(ingredientDto.name()).block();
                Ingredients ingredientToSave = new Ingredients(
                        ingredientDto.name(),
                        nutrients.calories(),
                        nutrients.fat(),
                        nutrients.protein(),
                        nutrients.carbs()
                );
                ingredientsRepository.save(ingredientToSave);
            }
        }
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

            // Create a new IngredientsDto with updated values
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

        // Create MealIngredient objects and update Meal
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
