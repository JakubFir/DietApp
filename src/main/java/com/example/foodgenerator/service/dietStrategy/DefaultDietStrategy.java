package com.example.foodgenerator.service.dietStrategy;

import com.example.foodgenerator.domain.Diet;
import com.example.foodgenerator.domain.User;
import com.example.foodgenerator.repository.DietRepository;
import com.example.foodgenerator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultDietStrategy implements DietStrategy {

    private final DietRepository dietRepository;
    private final UserRepository userRepository;

    @Override
    public void calculateMacro(User user) {
        Diet defaultDiet = user.getDiet();
        double totalCalories = user.getCaloricDemand();
        double carbs = Math.round((totalCalories * 0.4) / 4);
        double protein = Math.round((totalCalories * 0.3) / 4);
        double fat = Math.round((totalCalories * 0.3) / 9);
        defaultDiet.setName("Default diet 40/30/30");
        defaultDiet.setCarbs(carbs);
        defaultDiet.setFat(fat);
        defaultDiet.setProtein(protein);
        user.setDiet(defaultDiet);

        dietRepository.save(defaultDiet);
        userRepository.save(user);
    }

    @Override
    public DietType dietType() {
        return DietType.DEFAULT;
    }
}
