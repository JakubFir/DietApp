package com.example.foodgenerator.mapper;

import com.example.foodgenerator.domain.User;
import com.example.foodgenerator.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserDto mapToUserDto(User user){
        return UserDto.builder()
                .email(user.getEmail())
                .role(user.getRole())
                .username(user.getUsername())
                .mealList(user.getMeals().stream().toList())
                .build();
    }
}
