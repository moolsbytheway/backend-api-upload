/*
 * Copyright (c) 2020. ALIS.
 * Proprietary source code; any copy or modification is prohibited.
 *
 *
 *
 */

package com.alis.controller;

import com.alis.model.User;
import com.alis.model.dto.Ack;
import com.alis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<User> getAll() {
        return service.getAll();
    }

    @GetMapping("/{page}")
    public Page<User> get(@PathVariable(value = "page") Integer page) {
        return service.get(page);
    }


}
