package com.example.foodgenerator.service;

import com.example.foodgenerator.domain.MealDiary;
import com.example.foodgenerator.domain.User;
import com.example.foodgenerator.repository.MealDiaryRepository;
import com.example.foodgenerator.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
@AllArgsConstructor
public class MealDiaryService {
    private final MealDiaryRepository mealDiaryRepository;
    private final UserRepository userRepository;

    public MealDiary getUserMealDiary(Long userId, LocalDate date) {
        User user = userRepository.findById(userId).orElseThrow();
        MealDiary mealDiary = mealDiaryRepository.findByIdAndDate(userId,date).orElse(new MealDiary());
        mealDiary.setCaloricDemandForGivenDay(user.getCaloricDemand());
        mealDiary.setUser(user);
        mealDiary.setDate(date);
        return mealDiary;
    }
}
