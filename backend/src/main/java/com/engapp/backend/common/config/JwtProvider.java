package com.engapp.backend.common.config;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.engapp.backend.domain.user.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

    private static final String SECRET_KEY =
            "your-secret-key-your-secret-key-1";

    private static final long EXPIRATION_TIME =
            1000L * 60 * 60 * 24;

    public String generateToken(User user) {

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("userName", user.getUserName())
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + EXPIRATION_TIME
                        )
                )
                .signWith(
                        Keys.hmacShaKeyFor(
                                SECRET_KEY.getBytes()
                        )
                )
                .compact();
    }

    public boolean validateToken(String token) {

        try {

            Jwts.parserBuilder()
                    .setSigningKey(
                            Keys.hmacShaKeyFor(
                                    SECRET_KEY.getBytes()
                            )
                    )
                    .build()
                    .parseClaimsJws(token);

            return true;

        } catch (JwtException | IllegalArgumentException e) {

            return false;
        }
    }

    public Long getUserId(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(
                        Keys.hmacShaKeyFor(
                                SECRET_KEY.getBytes()
                        )
                )
                .build()
                .parseClaimsJws(token)
                .getBody();

        return Long.valueOf(claims.getSubject());
    }
}