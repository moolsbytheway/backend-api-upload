/*
 * Copyright (c) 2020. ALIS.
 * Proprietary source code; any copy or modification is prohibited.
 *
 *
 *
 */

package com.alis.model;


import com.alis.enumeration.RoleEnum;
import com.alis.security.TokenUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String resetToken;
    private String language;
    private String birthPlace;
    private LocalDate birthDate;
    @Enumerated(EnumType.ORDINAL)
    private RoleEnum role;

    private Boolean userActive;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public TokenUser toTokenUser() {
        TokenUser tokenUser = new TokenUser();
        tokenUser.setId(getId());
        tokenUser.setPermissions(new String[]{getRole().name()});
        tokenUser.setUsername(getUsername());
        tokenUser.setPassword(null);
        tokenUser.setLang(getLanguage());
        tokenUser.setRememberMe(false);
        return tokenUser;
    }


}
