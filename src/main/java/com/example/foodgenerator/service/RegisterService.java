package com.example.foodgenerator.service;

import com.example.foodgenerator.domain.Diet;
import com.example.foodgenerator.domain.MealDiary;
import com.example.foodgenerator.domain.Role;
import com.example.foodgenerator.domain.User;
import com.example.foodgenerator.dto.RegisterRequest;
import com.example.foodgenerator.repository.DietRepository;
import com.example.foodgenerator.repository.UserRepository;
import com.example.foodgenerator.service.dietStrategy.DietType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private final UserRepository userRepository;
    private final RequestValidator requestValidator;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final DietRepository dietRepository;
    private final DietService dietService;
    public void registerUser(RegisterRequest request) {
        if(requestValidator.validateRegisterRequest(request)){
            Diet diet = new Diet();
            User user = new User();
                user.setEmail(request.email());
                user.setUsername(request.username());
                user.setPassword(passwordEncoder.encode(request.password()));
                user.setRole(Role.USER);
                user.setAge(request.age());
                user.setGender(request.gender());
                user.setHeight(request.height());
                user.setWeight(request.weight());
                user.setDiet(diet);
                user.setActivityLevel(request.activityLevel());
                user.setCaloricDemand(userService.calculateCaloricDemand(request));
                dietRepository.save(diet);
                userRepository.save(user);
                setStartingDietToDefualt(user);
        }

    }

    private void setStartingDietToDefualt(User user) {
        dietService.setUserDiet(DietType.DEFAULT, user.getUserId());
    }


}
