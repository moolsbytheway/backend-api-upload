/*
 * Copyright (c) 2020. ALIS.
 * Proprietary source code; any copy or modification is prohibited.
 *
 *
 *
 */

package com.alis.repository;

import com.alis.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User>  findByEmail(String email);
}
