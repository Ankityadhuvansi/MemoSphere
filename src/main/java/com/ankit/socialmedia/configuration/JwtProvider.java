package com.ankit.socialmedia.configuration;

import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.net.Authenticator;

public class JwtFilter {
    private SecretKey key = Keys.hmacShaKeyFor(JwtConst.SECRET_KEY.getBytes());

    public String generateToken(Authentication authentication){

    }
}
