package com.ankit.socialmedia.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtProvider {
    private static SecretKey key = Keys.hmacShaKeyFor(JwtConst.SECRET_KEY.getBytes());

    public static String generateToken(Authentication auth){
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .claim("email",auth.getName())
                .signWith(key)
                .compact();
    }
    public static String extractEmailByToken(String jwt) {
        try {
            jwt = jwt.substring(7);
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            return String.valueOf(claims.get("email"));
        } catch (ExpiredJwtException e) {
            // Handle token expiration
            throw new RuntimeException("Token expired", e);
        } catch (JwtException | IllegalArgumentException e) {
            // Handle invalid or malformed token
            throw new RuntimeException("Invalid token", e);
        }
    }
}
