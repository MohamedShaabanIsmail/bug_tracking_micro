package com.Ashmo.APIgateway.Filter;

import java.util.Base64;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenValidation {
   
    private SecretKey seckey;

    public TokenValidation() {
        seckey = Keys.hmacShaKeyFor(Base64.getDecoder().decode("MicroserviceSecureApplicationAshmoSecretByJwtandRefreshT")); 
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(seckey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role",String.class);
    }

    public void validateToken(String token) {
        Jwts.parser()
            .verifyWith(seckey)
            .build()
            .parseSignedClaims(token);
    }

}
