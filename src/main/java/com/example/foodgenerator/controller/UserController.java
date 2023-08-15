package com.example.foodgenerator.controller;

import com.example.foodgenerator.dto.RequestUpdateBody;
import com.example.foodgenerator.dto.UserDto;

import com.example.foodgenerator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/{id}")
    public UserDto getUser(@PathVariable Long id){
        return userService.getUser(id);
    }
    @DeleteMapping(path = "/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
    @PutMapping(path = "/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody RequestUpdateBody request){
        return userService.updateUser(id, request);
    }
}
