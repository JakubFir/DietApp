package com.example.foodgenerator.mapper;

import com.example.foodgenerator.domain.MealDiary;
import com.example.foodgenerator.dto.MealDiaryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

@RequiredArgsConstructor
public class MealDiaryMapper {
    private final MealMapper mealMapper;

    public MealDiaryDto mapToMealDiaryDTO(MealDiary mealDiary){
        return new MealDiaryDto(
                mealDiary.getUser().getUsername(),
                mealDiary.getRemainingCalories(),
                mealDiary.getCaloricDemand(),
                mealDiary.getDate(),
                mealDiary.getMeals().stream().map(mealMapper::mapToMealDto).collect(Collectors.toList()));
    }

    public List<MealDiaryDto> mapToListMealDiaryDTO(List<MealDiary> all) {
        return all.stream().map(this::mapToMealDiaryDTO).collect(Collectors.toList());
    }
}
