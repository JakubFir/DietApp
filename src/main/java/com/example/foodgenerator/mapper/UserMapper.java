package com.example.foodgenerator.mapper;

import com.example.foodgenerator.domain.User;
import com.example.foodgenerator.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final MealDiaryMapper mealDiaryMapper;

    public UserDto mapToUserDto(User user){
        return UserDto.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .username(user.getUsername())
                .age(user.getAge())
                .gender(user.getGender())
                .height(user.getHeight())
                .weight(user.getWeight())
                .activityLevel(user.getActivityLevel())
                .caloricDemand(user.getCaloricDemand())
                .mealDiary(user.getMealDiary().stream().map(mealDiaryMapper::mapToMealDiaryDTO).collect(Collectors.toList()))
                .build();
    }
}
