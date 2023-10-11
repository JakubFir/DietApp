package com.example.foodgenerator.controller;

import com.example.foodgenerator.dto.RegisterRequest;
import com.example.foodgenerator.service.JwtService;
import com.example.foodgenerator.service.RegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/register")
@RequiredArgsConstructor
public class RegisterController {
    private final static Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    private final RegisterService registerService;
    private final JwtService jwtService;


    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request) {
        LOGGER.info("Starting register request for user " + request.username());
        registerService.registerUser(request);
        String token = jwtService.generateToken(request.username());
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token)
                .build();
    }
}
