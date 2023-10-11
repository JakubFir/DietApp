package com.example.foodgenerator.service;

import com.example.foodgenerator.controller.AuthenticateController;
import com.example.foodgenerator.domain.*;
import com.example.foodgenerator.dto.IngredientsDto;
import com.example.foodgenerator.dto.MealDto;
import com.example.foodgenerator.exceptions.MealDiaryNotFoundException;
import com.example.foodgenerator.exceptions.MealNotFoundException;
import com.example.foodgenerator.exceptions.UserNotFoundException;
import com.example.foodgenerator.exceptions.UsernameTakenException;
import com.example.foodgenerator.mapper.MealIngredientMapper;
import com.example.foodgenerator.mapper.MealMapper;
import com.example.foodgenerator.repository.IngredientsRepository;
import com.example.foodgenerator.repository.MealDiaryRepository;
import com.example.foodgenerator.repository.MealRepository;
import com.example.foodgenerator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MealService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MealService.class);

    private final UserRepository userRepository;
    private final MealRepository mealRepository;
    private final MealDiaryService mealDiaryService;
    private final IngredientsRepository ingredientsRepository;
    private final IngredientsService ingredientsService;
    private final MealIngredientMapper mealIngredientMapper;
    private final MealDiaryRepository mealDiaryRepository;
    private final MealMapper mealMapper;

    public void addMealToUserMealDiary(MealDto mealDto, Long userId) {
        LOGGER.trace("Starting adding meal for user with id: " + userId + " meal: " + mealDto);
        try {
            User userToAddMealTo = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with given ID doesn't exists "));
            ingredientsService.checkIfIngredientsAreInDB(mealDto.ingredientsList());
            MealDiary mealDiary = mealDiaryService.getUserMealDiary(userId, mealDto.mealDate());
            Meal meal = calculateMealCalories(mealDto);

            updateMealDiaryAndUser(userToAddMealTo, mealDiary, meal);
        } catch (UserNotFoundException e) {
            LOGGER.warn("Failed to add meal. User not found: " + e.getMessage());
            throw e;
        }
        LOGGER.info("Succesfully added meal to user meal diary");
    }

    private void updateMealDiaryAndUser(User userToAddMealTo, MealDiary mealDiary, Meal meal) {
        LOGGER.trace("Starting updating meal diary and user ");
        meal.setMealDiary(mealDiary);
        mealDiary.getMeals().add(meal);
        mealDiary.setRemainingCalories(mealDiary.getRemainingCalories() - meal.getCalories());

        mealRepository.save(meal);
        mealDiaryRepository.save(mealDiary);
        userRepository.save(userToAddMealTo);
        LOGGER.info("Meal diary and user succesfully updated");
    }

    private Meal calculateMealCalories(MealDto mealDto) {
        LOGGER.trace("Starting calculating calories for meal: " + mealDto);
        Meal meal = mealMapper.mapToMeal(mealDto);
        List<IngredientsDto> updatedIngredientsList = mealDto.ingredientsList().stream()
                .map(ingredientToCount -> {
                    LOGGER.trace("Starting calculating calories for ingredient: " + ingredientToCount);
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
        LOGGER.trace("Calculating calories ended succesfully, setting meal ingredients: " + updatedIngredientsList);
        meal.setIngredients(mealIngredientMapper.mapToMealIngredientList(updatedIngredientsList));

        return meal;
    }

    public void updateUserMeal(MealDto mealDto, Long userId) {
        LOGGER.trace("Starting updating user meal for user with id: " + userId);
        try {
            User userToAddMealTo = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with given ID doesn't exists "));

            ingredientsService.checkIfIngredientsAreInDB(mealDto.ingredientsList());

            MealDiary mealDiary = mealDiaryService.getUserMealDiary(userId, mealDto.mealDate());
            Meal mealToUpdate = findMealByIdInDiary(mealDiary, mealDto.mealId());

            Meal updatedMeal = calculateMealCalories(mealDto);
            var oldMealCalories = mealToUpdate.getCalories();

            Meal mealToSave = setNewMealMacro(mealToUpdate, updatedMeal);


            mealDiary.setRemainingCalories((mealDiary.getRemainingCalories() + oldMealCalories) - updatedMeal.getCalories());

            mealRepository.save(mealToSave);
            mealDiaryRepository.save(mealDiary);
            userRepository.save(userToAddMealTo);
        } catch (UserNotFoundException e) {
            LOGGER.warn("Failed to add meal. User not found: " + e.getMessage());
            throw e;
        }
        LOGGER.info("Meal updated succesfully");

    }

    private Meal findMealByIdInDiary(MealDiary mealDiary, Long mealId) {
        LOGGER.trace("Trying to find meal by id in meal diary");
        return mealDiary.getMeals()
                .stream()
                .filter(meal -> meal.getId().equals(mealId))
                .findFirst()
                .orElseThrow(() -> new MealNotFoundException("Meal with given ID doesn't exist"));
    }


    private Meal setNewMealMacro(Meal mealToUpdate, Meal updatedMeal) {
        LOGGER.trace("Starting setting new meal macro");
        try {
            mealToUpdate.setMealName(updatedMeal.getMealName());
            mealToUpdate.setCalories(updatedMeal.getCalories());
            mealToUpdate.setFat(updatedMeal.getFat());
            mealToUpdate.setProtein(updatedMeal.getProtein());
            mealToUpdate.setCarbs(updatedMeal.getCarbs());
            mealToUpdate.setIngredients(updatedMeal.getIngredients());
            LOGGER.info("Succesfully set new macro");
            return mealToUpdate;
        } catch (Exception e) {
            LOGGER.warn("Exception during setting new macro for meal: " + mealToUpdate);
            throw e;
        }

    }

    public void deleteMeal(Long userId, LocalDate date, MealDto mealDto) {
        LOGGER.trace("Starting delete meal method for user with id: " + userId);
        User user = userRepository.findById(userId).orElseThrow();
        MealDiary mealDiary = mealDiaryRepository.findByUserAndDate(user, date).orElseThrow();
        try {
            Meal mealToRemove = mealDiary.getMeals().stream().filter(meal -> meal.getMealName().equals(mealDto.mealName()))
                    .findFirst().orElseThrow(() -> new MealNotFoundException("Meal with given name doesnt exists"));
            mealDiary.getMeals().remove(mealToRemove);
            mealDiary.setRemainingCalories(mealDiary.getRemainingCalories() + mealToRemove.getCalories());
            mealDiaryRepository.save(mealDiary);
            LOGGER.info("Meal succesfully deleted");
        } catch (MealNotFoundException e) {
            LOGGER.warn("Exception during deleting meal");
            throw e;
        }


    }


}
