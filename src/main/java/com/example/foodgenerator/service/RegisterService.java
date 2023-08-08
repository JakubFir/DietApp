package com.example.foodgenerator.service;

import com.example.foodgenerator.domain.Role;
import com.example.foodgenerator.domain.User;
import com.example.foodgenerator.dto.RegisterRequest;
import com.example.foodgenerator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private final UserRepository userRepository;
    private final RequestValidator requestValidator;
    private final PasswordEncoder passwordEncoder;
    public void registerUser(RegisterRequest request) {
        if(requestValidator.validateRegisterRequest(request)){
            User user = new User();
                user.setEmail(request.getEmail());
                user.setUsername(request.getUsername());
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                user.setRole(Role.USER);
                userRepository.save(user);
        }

    }
}
