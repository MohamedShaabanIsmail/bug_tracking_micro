package com.Ashmo.UserService.Service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Ashmo.UserService.Model.RefreshToken;
import com.Ashmo.UserService.Model.Users;
import com.Ashmo.UserService.Reposatory.TokenRepo;
import com.Ashmo.UserService.Reposatory.UserRepo;

@Service
public class UserService {

    @Autowired
    @Lazy
    private MyUserDetailsService userDetailsService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TokenRepo tokenRepo;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users addUser(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public Map<String, String> verify(Users user) {
        Authentication authen = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(authen.isAuthenticated()){

            String role = EncryptDecryptRole.decrypt(getUser(user.getUsername()).getRole());
            Map<String,String> map = new HashMap<>();
            map.put("access_token", jwtService.generateToken(user.getUsername(), 1000*60 , role));
            map.put("refresh_token", jwtService.generateToken(user.getUsername(), 1000*60*60*24 , role));

            RefreshToken refreshToken = new RefreshToken();
            refreshToken.setToken(map.get("refresh_token"));
            refreshToken.setUsername(user.getUsername());
            tokenRepo.save(refreshToken);

            return map;
        }
        return new HashMap<>();
    }

    public Map<String, String> newAccessToken(String token) {
        RefreshToken refreshToken = tokenRepo.findByToken(token);
        
        if(refreshToken == null){
            return null;
        }
        try{
            if(!jwtService.validateToken(token, userDetailsService.loadUserByUsername(refreshToken.getUsername()))){
                tokenRepo.deleteByToken(token);
                return null;
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", jwtService.generateToken(refreshToken.getUsername(), 1000 * 60 ,jwtService.extractRole(token)));
        tokens.put("refresh_token", token);
        return tokens;
    }

    public String getUsername(int id) {
        return userRepo.getUsername(id);
    }

    public Users getUser(String username) {
        return userRepo.getByUsername(username);
    }
    
}
