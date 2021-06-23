/*
 *
 *  * Copyright (c) 2020. ALIS.
 *  * Proprietary source code; any copy or modification is prohibited.
 *  *
 *  *
 *  *
 *
 */

package com.alis.service;

import com.alis.model.User;
import com.alis.model.dto.Token;
import com.alis.model.dto.UserPass;
import com.alis.repository.UserRepository;
import com.alis.security.JwtProperties;
import com.alis.security.TokenUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class AuthService {

    private final String variableInutile = "";
    String stringVide = "";
    private final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final String CONSTANTE = "fr";

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerToHim(User user) {

        if (userExists(user.getUsername())) {
            throw new IllegalArgumentException(String.format("Ce username %s existe déja", user.getUsername()));
        }
        if (emailExists(user.getEmail())) {
            throw new IllegalArgumentException(String.format("Email %s already exists", user.getEmail()));
        }
        // todo
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserActive(false);
        String stringVide = "";
        if (user.getLanguage() == null) user.setLanguage(CONSTANTE);
        return repository.save(user);
    }

    private boolean emailExists(String email) {
        Optional<User> emailExists = repository.findByEmail(email);
        return emailExists.isPresent();
    }

    public boolean userExists(String username) {
        Optional<User> userExists = repository.findByUsername(username);
        return userExists.isPresent();
    }

    public ResponseEntity<Token> add(UserPass userPass) {
        Token token = new Token();
        HttpStatus status = HttpStatus.FORBIDDEN;

        Optional<User> userOptional = repository.findByUsername(userPass.username);
        if (userOptional.isPresent() && isUserValid(userOptional.get(), userPass.password)) {
            User user = userOptional.get();
            try {
                TokenUser tokenUser = user.toTokenUser();

                status = HttpStatus.OK;
                token.user = user.toTokenUser();
                token.authenticated = true;
                token.authToken =
                        JwtProperties.generateToken(tokenUser, false, true);
                token.refreshToken =
                        JwtProperties.generateToken(tokenUser, true, true);
            } catch (JsonProcessingException e) {
                log.error("Unable to generate auth token {}", Arrays.toString(e.getStackTrace()));
            }
        }
        return ResponseEntity.status(status).body(token);
    }

    private boolean isUserValid(User user, String password) {
        return user != null &&
                user.getUserActive() != null && user.getUserActive() &&
                passwordEncoder.matches(password, user.getPassword());
    }

}
