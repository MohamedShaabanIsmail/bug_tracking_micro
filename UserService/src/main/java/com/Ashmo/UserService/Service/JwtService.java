package com.Ashmo.UserService.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtService {

    private SecretKey seckey ;

    public JwtService() {
        try {
            seckey = KeyGenerator.getInstance("HmacShA256").generateKey();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("cannot generate seckey");
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String username, int time) {
        Map<String,Object> claim = new HashMap<>();
        return Jwts.builder()
            .claims(claim)
            .subject(username)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis()+time))
            .signWith(seckey)
            .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(seckey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

     public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
