package com.Ashmo.BugService.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USERSERVICE", configuration = FeignClientConfig.class)
public interface UsersInterface {

    @GetMapping("/users/username/{id}")
    public String getUsername(@PathVariable int id) ;

}
