package com.example.foodgenerator.service;

import com.example.foodgenerator.controller.MealDiaryController;
import com.example.foodgenerator.dto.RegisterRequest;
import com.example.foodgenerator.exceptions.BadPasswordException;
import com.example.foodgenerator.exceptions.EmailTakenException;
import com.example.foodgenerator.exceptions.InvalidEmailException;
import com.example.foodgenerator.exceptions.UsernameTakenException;
import com.example.foodgenerator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestValidator {
    private final static Logger LOGGER = LoggerFactory.getLogger(RequestValidator.class);

    private final UserRepository userRepository;

    public boolean validateRegisterRequest(RegisterRequest request) {
        LOGGER.trace("Starting validation for register request");
        String password = request.password();
        String email = request.email();
        String username = request.username();
        if (password == null || password.length() < 5) {
            LOGGER.warn("User provided bad password");
            throw new BadPasswordException("Password has to be at least 5 characters long");
        }
        validateEmail(email.trim());
        validateUsername(username.trim());
        LOGGER.info(request.username() + " succesfully registered");
        return true;
    }


    private boolean validateUsername(String username) {
        LOGGER.trace("Starting validating username: " + username);
        if (userRepository.existsByUsername(username)) {
            LOGGER.warn("Username taken");
            throw new UsernameTakenException("Username allready taken");
        }
        LOGGER.info("Username succesfully validated");
        return true;
    }

    private boolean validateEmail(String email) {
        LOGGER.trace("Starting validating user email: " + email );
        if (!email.matches("^(?!.*\\.{2})[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$")) {
            LOGGER.warn("User provided bad email");
            throw new InvalidEmailException("Provide a valid email");
        }
        if (userRepository.existsByEmail(email)) {
            LOGGER.warn("User provided email that was allready taken");
            throw new EmailTakenException("Email allready taken");
        }
        LOGGER.info("Email succesfully validated");
        return true;
    }
}
