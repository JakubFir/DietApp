package com.example.foodgenerator.controller;

import com.example.foodgenerator.dto.authenticate.AuthenticateRequest;
import com.example.foodgenerator.dto.authenticate.AuthenticationResponse;
import com.example.foodgenerator.service.AuthenticateService;
import com.example.foodgenerator.service.UserService;
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
@RequestMapping("api/v1/login")
@RequiredArgsConstructor
public class AuthenticateController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticateController.class);
    private final AuthenticateService authenticateService;

    @PostMapping
    public ResponseEntity<?> authenticationResponse(@RequestBody AuthenticateRequest request){
        logger.info("Starting authentication request for user " + request.username());
        AuthenticationResponse authenticationResponse = authenticateService.authenticate(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, authenticationResponse.token())
                .body(authenticationResponse);
    }

}
