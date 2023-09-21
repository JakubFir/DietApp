package com.example.foodgenerator.service;

import com.example.foodgenerator.domain.MealDiary;
import com.example.foodgenerator.domain.User;
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
        User userToAddMealTo = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User with given ID doesn't exists"));
        return mealDiaryRepository.findByUserAndDate(userToAddMealTo, date).orElse(createMealDiary(userToAddMealTo, date));
    }

    private MealDiary createMealDiary(User userToAddMealTo, LocalDate date) {
        MealDiary mealDiary;
        mealDiary = new MealDiary();
        mealDiary.setUser(userToAddMealTo);
        mealDiary.setDate(date);
        mealDiary.setRemainingCalories(userToAddMealTo.getCaloricDemand());
        mealDiary.setCaloricDemand(userToAddMealTo.getCaloricDemand());
        mealDiaryRepository.save(mealDiary);
        userToAddMealTo.getMealDiary().add(mealDiary);
        return mealDiary;
    }

}
