package com.example.foodgenerator.service.dietStrategy;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Configuration
@AllArgsConstructor
public class StrategyConfig {

    private final List<DietStrategy> dietStrategies;

    @Bean
    public Map<DietType, DietStrategy> setDietByType() {
        Map<DietType, DietStrategy> dietByType = new EnumMap<>(DietType.class);
        dietStrategies.forEach(dietStrategy -> dietByType.put(dietStrategy.dietType(), dietStrategy));
        return dietByType;
    }
}

