package com.example.foodgenerator.service;

import com.example.foodgenerator.domain.ActivityLevel;
import com.example.foodgenerator.domain.Gender;
import com.example.foodgenerator.domain.User;
import com.example.foodgenerator.dto.RegisterRequest;
import com.example.foodgenerator.dto.RequestUpdateBody;
import com.example.foodgenerator.dto.UserDto;
import com.example.foodgenerator.mapper.UserMapper;
import com.example.foodgenerator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    public UserDto getUser(Long id) {
        User user  = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User with given id dosn't exists"));
        return userMapper.mapToUserDto(user);
    }
    public void deleteUser(Long id) {
        if(userExists(id)){
            userRepository.deleteById(id);
        }
    }
    public UserDto updateUser(Long id, RequestUpdateBody request) {
        if(userExists(id)){
            User userToUpdate = userRepository.findById(id).orElseThrow();
            userToUpdate.setEmail(request.email());
            userToUpdate.setUsername(request.username());
            userToUpdate.setPassword(request.password());

            userRepository.save(userToUpdate);
            return userMapper.mapToUserDto(userToUpdate);
        }else{
            throw new UsernameNotFoundException("user not found");
        }
    }
    public double calculateCaloricDemand(RegisterRequest calculateCaloricDemandRequest){
        double bmr = calculateBmr(calculateCaloricDemandRequest);
        ActivityLevel activityLevel = ActivityLevel.fromLevel(calculateCaloricDemandRequest.activityLevel());
        double activityMultiplier = activityLevel.getMultiplier();
        return  Math.round(bmr * activityMultiplier);
    }

    private double calculateBmr(RegisterRequest request) {
        if (request.gender() == Gender.MALE) {
            return  Math.round(88.362 + (13.397 * request.weight())
                    + (4.799 * request.height())
                    - (5.677 * request.age()));
        } else {
            return  Math.round(447.593 + (9.247 * request.weight())
                    + (3.098 * request.height())
                    - (4.330 * request.age()));
        }
    }

    private boolean userExists(Long id) {
        return userRepository.existsById(id);
    }
}
