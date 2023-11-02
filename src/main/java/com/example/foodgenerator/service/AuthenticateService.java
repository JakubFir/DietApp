package com.example.foodgenerator.service;

import com.example.foodgenerator.dto.authenticate.AuthenticateRequest;
import com.example.foodgenerator.dto.authenticate.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticateService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticateService.class);
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticateRequest request) {
        logger.trace("Starting authentication request for user " + request.username());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(),
                            request.password()
                    ));
            String token = jwtService.generateToken(request.username());
            logger.info("Authentication success for user " + request.username());
            return new AuthenticationResponse(token);
        } catch (Exception e) {
            logger.warn("Authentication failed for user " + request.username() + ": bad credentials");
            throw new BadCredentialsException("msg");
        }

    }

}
