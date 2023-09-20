package com.example.foodgenerator.service;

import com.example.foodgenerator.domain.*;
import com.example.foodgenerator.dto.DietDto;
import com.example.foodgenerator.dto.RegisterRequest;
import com.example.foodgenerator.dto.RequestUpdateBody;
import com.example.foodgenerator.dto.UserDto;
import com.example.foodgenerator.mapper.UserMapper;
import com.example.foodgenerator.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, userMapper);
    }

    @Test
    void getUser() {
        //Given
        User user = new User(
                1L,
                "Adam",
                "qwerty",
                "test@Wp.pl",
                30,
                70.5,
                175.0,
                Gender.MALE,
                3,
                Role.USER,
                0.0,
                new Diet(),
                new ArrayList<>()
        );
        UserDto userDto = new UserDto(
                "Adam",
                "test@Wp.pl",
                Role.USER,
                30,
                70.5,
                175.0,
                Gender.MALE,
                3,
                0.0,
                new DietDto("test", 2, 2, 2),
                new ArrayList<>()
        );
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.mapToUserDto(any(User.class))).thenReturn(userDto);


        //When
        UserDto result = userService.getUser(1L);
        //Then
        assertThat(result.email()).isEqualTo(user.getEmail());
    }


    @Test
    void deleteUser() {
        //Given
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        //When
        userService.deleteUser(userId);

        //Then
        verify(userRepository).deleteById(userId);
    }

    @Test
    void updateUser() {
        //Given
        User user = new User();
        RequestUpdateBody requestUpdateBody = new RequestUpdateBody(
                "test", "test", "test", 1, 1, 1, Gender.MALE, 1);
        UserDto userDto = new UserDto("test", "test", Role.USER, 1, 1, 1, Gender.MALE, 1, 1, null, new ArrayList<>());
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.mapToUserDto(user)).thenReturn(userDto);

        //When
        UserDto result = userService.updateUser(1L, requestUpdateBody);

        //Then
        assertThat(result.email()).isEqualTo(requestUpdateBody.email());
        assertThat(result.username()).isEqualTo(requestUpdateBody.username());

    }
    @Test
    void calculateCaloricDemandForMenWithActivityLevel_1() {
        RegisterRequest request = new RegisterRequest("test","test","test",30,70,175,Gender.MALE,1);

        //When
        double result = userService.calculateCaloricDemand(request);
        double expectedCaloricDemand = Math.round((88.362 + (13.397 * 70.0) + (4.799 * 175.0) - (5.677 * 30)) * ActivityLevel.SEDENTARY.getMultiplier());

        //Then
        assertEquals(expectedCaloricDemand, result, 0.001);
    }
}