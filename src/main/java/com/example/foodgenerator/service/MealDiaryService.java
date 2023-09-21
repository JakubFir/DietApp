package com.example.foodgenerator.service;

import com.example.foodgenerator.domain.MealDiary;
import com.example.foodgenerator.domain.User;
import com.example.foodgenerator.exceptions.MealDiaryNotFoundException;
import com.example.foodgenerator.repository.MealDiaryRepository;
import com.example.foodgenerator.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
@AllArgsConstructor
public class MealDiaryService {
    private final MealDiaryRepository mealDiaryRepository;
    private final UserRepository userRepository;

    public MealDiary getUserMealDiary(Long userId, LocalDate date) {
        User userToAddMealTo = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User with given ID doesnt exists"));
        MealDiary mealDiary = mealDiaryRepository.findByUserAndDate(userToAddMealTo, date).orElse(null);
        if (mealDiary == null) {
            mealDiary = new MealDiary();
            mealDiary.setUser(userToAddMealTo);
            mealDiary.setDate(date);
            mealDiary.setRemainingCalories(userToAddMealTo.getCaloricDemand());
            mealDiary.setCaloricDemand(userToAddMealTo.getCaloricDemand());
            mealDiaryRepository.save(mealDiary);
            userToAddMealTo.getMealDiary().add(mealDiary);
        }
        return mealDiary;
    }

}
