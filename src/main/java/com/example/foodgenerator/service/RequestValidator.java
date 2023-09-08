package com.example.foodgenerator.service;

import com.example.foodgenerator.dto.RegisterRequest;
import com.example.foodgenerator.exceptions.BadPasswordException;
import com.example.foodgenerator.exceptions.EmailTakenException;
import com.example.foodgenerator.exceptions.UsernameTakenException;
import com.example.foodgenerator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestValidator {

    private final UserRepository userRepository;

    public boolean validateRegisterRequest(RegisterRequest request) {
        String password = request.password();
        String email = request.email();
        String username = request.username();
        if (password == null || password.length() < 5) {
            throw new BadPasswordException("Password has to be at least 5 characters long");
        }
        validateEmail(email.trim());
        validateUsername(username.trim());

        return true;
    }

    private boolean validateUsername(String username) {
        if (userRepository.existsByUsername(username) || username.length() < 3) {
            throw new UsernameTakenException("Username allready taken");
        }
        return true;
    }

    private boolean validateEmail(String email) {
        if (userRepository.existsByEmail(email) || email.length() < 3) {
            throw new EmailTakenException("Email allready taken");
        }
        return true;
    }
}
