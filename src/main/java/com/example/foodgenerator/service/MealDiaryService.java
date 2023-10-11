package com.example.foodgenerator.service;

import com.example.foodgenerator.controller.MealDiaryController;
import com.example.foodgenerator.domain.MealDiary;
import com.example.foodgenerator.domain.User;
import com.example.foodgenerator.repository.MealDiaryRepository;
import com.example.foodgenerator.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
@AllArgsConstructor
public class MealDiaryService {
    private final static Logger LOGGER = LoggerFactory.getLogger(MealDiaryService.class);

    private final MealDiaryRepository mealDiaryRepository;
    private final UserRepository userRepository;

    public MealDiary getUserMealDiary(Long userId, LocalDate date) {
        LOGGER.trace("Starting getting user meal diary");
        User userToAddMealTo = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User with given ID doesnt exists"));
        MealDiary mealDiary = mealDiaryRepository.findByUserAndDate(userToAddMealTo, date).orElse(null);
        if (mealDiary == null) {
            LOGGER.trace("User meal diary was null creating new meal diary");
            mealDiary = new MealDiary();
            mealDiary.setUser(userToAddMealTo);
            mealDiary.setDate(date);
            mealDiary.setRemainingCalories(userToAddMealTo.getCaloricDemand());
            mealDiary.setCaloricDemand(userToAddMealTo.getCaloricDemand());
            mealDiaryRepository.save(mealDiary);
            userToAddMealTo.getMealDiary().add(mealDiary);
            LOGGER.info("Succesfully created meal diary for user with id: " + userId);
        }
        return mealDiary;
    }

}
