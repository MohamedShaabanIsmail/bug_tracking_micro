package com.Ashmo.UserService.Reposatory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Ashmo.UserService.Model.RefreshToken;

@Repository
public interface TokenRepo extends JpaRepository<RefreshToken, Integer> {

    RefreshToken findByToken(String token);
    void deleteByToken(String token);
    
}
