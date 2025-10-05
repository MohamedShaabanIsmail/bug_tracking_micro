package com.Ashmo.APIgateway.Filter;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class EndPoint {

    private static final List<String> openPaths = List.of("/users/login", "/users/register", "/users/refreshtoken", "/user/eureka");

    public static final List<String> adminPaths = List.of("/bugs/delete");

    public static final List<String> testerPaths = List.of("/bugs/create");


    public Predicate<ServerHttpRequest> needAuth = request -> openPaths.stream()
            .noneMatch(uri -> request.getURI().getPath().contains(uri));

    public boolean needRole (ServerHttpRequest request, List<String> paths){
        return paths.stream().anyMatch(path -> request.getURI().getPath().contains(path));
    }
}
