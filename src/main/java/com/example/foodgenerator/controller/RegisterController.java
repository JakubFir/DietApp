package com.example.foodgenerator.controller;

import com.example.foodgenerator.dto.RegisterRequest;
import com.example.foodgenerator.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/register")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping
    public ResponseEntity<Void> registerUser(@RequestBody RegisterRequest request) {
            registerService.registerUser(request);
        return ResponseEntity.ok().build();
    }
}
