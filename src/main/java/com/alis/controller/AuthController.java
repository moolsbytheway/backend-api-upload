/*
 * Copyright (c) 2020. ALIS.
 * Proprietary source code; any copy or modification is prohibited.
 *
 *
 *
 */

package com.alis.controller;

import com.alis.model.User;
import com.alis.model.dto.Token;
import com.alis.model.dto.UserPass;
import com.alis.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return service.registerToHim(user);
    }

    @PostMapping("/token")
    public ResponseEntity<Token> add(@RequestBody UserPass userPass) {
        return service.add(userPass);
    }
}
