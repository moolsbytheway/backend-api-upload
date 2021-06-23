package com.alis.model.dto;

import com.alis.security.TokenUser;

public class Token {
    public boolean authenticated = false;
    public String authToken;
    public TokenUser user;
    public String refreshToken;
}
