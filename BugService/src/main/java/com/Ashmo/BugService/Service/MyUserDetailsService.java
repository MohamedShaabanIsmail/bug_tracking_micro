package com.Ashmo.BugService.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Ashmo.BugService.Feign.UsersInterface;
import com.Ashmo.BugService.Model.MyUserDetails;
import com.Ashmo.BugService.Model.Users;


@Service
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    private UsersInterface usersInterface;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersInterface.getUser(username);
        if(user == null){
            System.out.println("user not found");
            throw new UsernameNotFoundException("user not found in class MyUserDetailsService");
        }
        return new MyUserDetails(user);
    }
    
}
