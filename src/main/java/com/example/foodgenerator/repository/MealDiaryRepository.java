package com.example.foodgenerator.repository;

import com.example.foodgenerator.domain.MealDiary;
import com.example.foodgenerator.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MealDiaryRepository extends JpaRepository<MealDiary,Long> {
    Optional<MealDiary> findByUserAndDate(User user, LocalDate date);
}
