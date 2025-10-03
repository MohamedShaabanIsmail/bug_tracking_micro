package com.Ashmo.APIgateway.Filter;

import java.util.Base64;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenValidation {
   
    private SecretKey seckey;

    public TokenValidation() {
        seckey = Keys.hmacShaKeyFor(Base64.getDecoder().decode("MicroserviceSecureApplicationAshmoSecretByJwtandRefreshT")); 
    }

    // private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
    //     final Claims claims = extractAllClaims(token);
    //     return claimResolver.apply(claims);
    // }

    // private Claims extractAllClaims(String token) {
    //     return Jwts.parser()
    //             .verifyWith(seckey)
    //             .build()
    //             .parseSignedClaims(token)
    //             .getPayload();
    // }

    public void validateToken(String token) {
        Jwts.parser()
            .verifyWith(seckey)
            .build()
            .parseSignedClaims(token);
    }
    

    // public boolean validateToken(String token) {
    //     return !isTokenExpired(token);
    // }

    // private boolean isTokenExpired(String token) {
    //     return extractExpiration(token).before(new Date());
    // }

    // private Date extractExpiration(String token) {
    //     return extractClaim(token, Claims::getExpiration);
    // }

}
