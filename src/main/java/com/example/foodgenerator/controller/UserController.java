package com.example.foodgenerator.controller;

import com.example.foodgenerator.dto.RequestUpdateBody;
import com.example.foodgenerator.dto.UserDto;

import com.example.foodgenerator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/{id}")
    @Cacheable(value = "user", key = "#id")
    public UserDto getUser(@PathVariable Long id) {
        System.out.println("test");
        UserDto user = userService.getUser(id);
        return user;
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping(path = "/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody RequestUpdateBody request){
        return ResponseEntity.ok(userService.updateUser(id, request));
    }
}
