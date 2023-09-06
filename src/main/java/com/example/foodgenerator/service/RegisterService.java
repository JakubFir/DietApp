package com.example.foodgenerator.service;

import com.example.foodgenerator.domain.Diet;
import com.example.foodgenerator.domain.Role;
import com.example.foodgenerator.domain.User;
import com.example.foodgenerator.dto.RegisterRequest;
import com.example.foodgenerator.repository.DietRepository;
import com.example.foodgenerator.repository.UserRepository;
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
    public void registerUser(RegisterRequest request) {
        if(requestValidator.validateRegisterRequest(request)){
            User user = new User();
            Diet diet = new Diet();
                user.setEmail(request.getEmail());
                user.setUsername(request.getUsername());
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                user.setRole(Role.USER);
                user.setAge(request.getAge());
                user.setGender(request.getGender());
                user.setHeight(request.getHeight());
                user.setWeight(request.getWeight());
                user.setActivityLevel(request.getActivityLevel());
                user.setDiet(diet);
                user.setCaloricDemand(userService.calculateCaloricDemand(request));
                dietRepository.save(diet);
                userRepository.save(user);

        }

    }
}
