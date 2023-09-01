package com.example.foodgenerator.service;

import com.example.foodgenerator.dto.authenticate.AuthenticateRequest;
import com.example.foodgenerator.dto.authenticate.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticateService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticateRequest request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            ));
        String token = jwtService.generateToken(request.getUsername());
        return new AuthenticationResponse(token);
    }

}
