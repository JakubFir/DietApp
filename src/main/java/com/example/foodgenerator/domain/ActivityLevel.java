package com.example.foodgenerator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ActivityLevel {
    SEDENTARY(1, 1.2),
    LIGHTLY_ACTIVE(2, 1.375),
    MODERATELY_ACTIVE(3, 1.55),
    VERY_ACTIVE(4, 1.725),
    SUPER_ACTIVE(5, 1.9);

    private final int level;
    private final double multiplier;

    public static ActivityLevel fromLevel(int level) {
        for (ActivityLevel activityLevel : ActivityLevel.values()) {
            if (activityLevel.getLevel() == level) {
                return activityLevel;
            }
        }
        throw new IllegalArgumentException("Invalid activity level");
    }
}
