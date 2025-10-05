package com.Ashmo.UserService.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.Ashmo.UserService.Model.Users;
import com.Ashmo.UserService.Service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Users addUser(@RequestBody Users user) {
        return userService.addUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user, HttpServletResponse response) {
        Map<String, String> tokens = userService.verify(user);

        ResponseCookie cookie = ResponseCookie.from("refresh_token", tokens.get("refresh_token"))
            .httpOnly(true)
            // .secure(false)
            // .sameSite("strict")
            .path("/")
            .maxAge(7 * 24 * 60 * 60) // 7 days
            .build();

        response.setHeader("Set-Cookie", cookie.toString());
        return tokens.get("access_token");
    }
    
    @GetMapping("/refreshtoken")
    public ResponseEntity<String> getNewToken(@CookieValue("refresh_token") String refreshToken, HttpServletResponse response) {

        if(refreshToken != null){
            Map<String, String> tokens = userService.newAccessToken(refreshToken);
            if(tokens != null){
                ResponseCookie cookie = ResponseCookie.from("refresh_token", tokens.get("refresh_token"))
                    .httpOnly(true)
                    // .secure(false)
                    // .sameSite("strict")
                    .path("/")
                    .maxAge(7 * 24 * 60 * 60) // 7 days
                    .build();

                response.setHeader("Set-Cookie", cookie.toString());
                return ResponseEntity.ok(tokens.get("access_token"));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        // String authorizationHeader = request.getHeader("Authorization");
        // if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
        //     throw new IllegalArgumentException("Invalid or missing Authorization header");
        // }
        // String token = authorizationHeader.substring(7);
        // return userService.newAccessToken(token);
    }
    
    @GetMapping("/username/{id}")
    public String getUsername(@PathVariable int id) {
        return userService.getUsername(id);
    }

    @GetMapping("/user/{username}")
    public Users getUser(@PathVariable String username) {
        return userService.getUser(username);
    }
}
