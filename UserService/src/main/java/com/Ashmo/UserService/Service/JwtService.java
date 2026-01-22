package com.Ashmo.UserService.Service;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.Ashmo.UserService.Model.MyUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private SecretKey seckey ;

    public JwtService() {
        seckey = Keys.hmacShaKeyFor(Base64.getDecoder().decode("MicroserviceSecureApplicationAshmoSecretByJwtandRefreshT")); 
    }

    public String generateToken(String email, int time, String role) {
        Map<String,Object> claim = new HashMap<>();
        claim.put("role", role);
        return Jwts.builder()
            .claims(claim)
            .subject(email)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis()+time))
            .signWith(seckey)
            .compact();
    }

    public String extractEmail(String email) {
        return extractClaim(email,Claims::getSubject);
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role",String.class);
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

    public boolean validateToken(String token, MyUserDetails userDetails) {
        final String userName = extractEmail(token);
        return (userName.equals(userDetails.getEmail()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
