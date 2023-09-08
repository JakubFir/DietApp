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
    private final DietMapper dietMapper;

    public UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getAge(),
                user.getWeight(),
                user.getHeight(),
                user.getGender(),
                user.getActivityLevel(),
                user.getCaloricDemand(),
                dietMapper.mapToDietDto(user.getDiet()),
                user.getMealDiary().stream().map(mealDiaryMapper::mapToMealDiaryDTO).collect(Collectors.toList())
        );
    }

}
