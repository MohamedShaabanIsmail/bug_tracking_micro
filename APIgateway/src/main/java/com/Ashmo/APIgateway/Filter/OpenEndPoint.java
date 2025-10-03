package com.Ashmo.APIgateway.Filter;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class OpenEndPoint {

    public static final List<String> paths = List.of("/users/login", "/users/register", "/users/refreshtoken", "/user/eureka");

    public Predicate<ServerHttpRequest> needAuth = request -> paths.stream()
            .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
