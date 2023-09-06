package com.example.foodgenerator.service;

import com.example.foodgenerator.domain.User;
import com.example.foodgenerator.repository.UserRepository;
import com.example.foodgenerator.service.dietStrategy.DietStrategy;
import com.example.foodgenerator.service.dietStrategy.DietType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DietService {
    private final UserRepository userRepository;
    private final Map<DietType, DietStrategy> setDietByType;

    public void setUserDietToDefault(DietType dietType, Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        DietStrategy dietStrategy = setDietByType.getOrDefault(dietType, null);
        if (Objects.isNull(dietStrategy)) {
            throw new IllegalArgumentException("not foud");
        }
        dietStrategy.calculateMacro(user);
    }
}
