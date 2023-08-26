package com.example.foodgenerator.service;

import com.example.foodgenerator.domain.Role;
import com.example.foodgenerator.domain.User;
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
        userService = new UserService(userRepository,userMapper);
    }

    @Test
    void getUser() {
        //Given
        User user = new User(1L,"Adam","qwerty","test@Wp.pl", Role.USER,new ArrayList<>());
        UserDto userDto = new UserDto("Adam","test@Wp.pl",Role.USER,new ArrayList<>());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.mapToUserDto(any(User.class))).thenReturn(userDto);


        //When
        UserDto result = userService.getUser(1L);
        System.out.println(result);

        //Then
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
    }


    @Test
    void deleteUser() {
    }

    @Test
    void updateUser() {
    }
}