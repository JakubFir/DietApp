package com.example.foodgenerator.repository;

import com.example.foodgenerator.domain.MealDiary;
import com.example.foodgenerator.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface MealDiaryRepository extends JpaRepository<MealDiary,Long> {
    MealDiary findByUserAndDate(User userId, LocalDate mealDate);
}
