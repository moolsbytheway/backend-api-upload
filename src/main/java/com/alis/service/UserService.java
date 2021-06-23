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


import com.alis.enumeration.RoleEnum;
import com.alis.model.User;
import com.alis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @PreAuthorize("hasAnyAuthority('AGENT', 'ADMIN')")
    public List<User> getAll() {
        User user = getConnectedUser();
        if (RoleEnum.AGENT.equals(user.getRole())) {
            return repository.findAll().stream()
                    .peek(u -> u.setPassword("HIDDEN"))
                    .filter(user1 -> RoleEnum.USER.equals(user1.getRole()))
                    .collect(Collectors.toList());
        } else {
            return repository.findAll();
        }

    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Page<User> get(Integer page) {
        if (page <= 0) {
            throw new IllegalArgumentException("Minimum value for page is 1.");
        }
        Page<User> res = repository.findAll(PageRequest.of(page - 1, 10));
        if (res.getTotalElements() > 0) {
            res.forEach(it -> it.setPassword("HIDDEN"));
        }
        return res;
    }

    public User getConnectedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) throw new IllegalStateException("Couldn't get authentication object");
        Optional<User> result = repository.findByUsername(auth.getName());
        if (!result.isPresent()) throw new IllegalStateException("Connected user couldn't be found in database");
        return result.get();
    }
}
