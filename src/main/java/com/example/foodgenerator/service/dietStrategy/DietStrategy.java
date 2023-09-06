package com.example.foodgenerator.service.dietStrategy;

import com.example.foodgenerator.domain.User;

public interface DietStrategy {
    void calculateMacro(User user);
    DietType dietType();
}
