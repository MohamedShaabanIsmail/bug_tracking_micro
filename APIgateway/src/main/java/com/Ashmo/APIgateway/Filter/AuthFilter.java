package com.Ashmo.APIgateway.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class AuthFilter implements GlobalFilter{

    @Autowired
    private TokenValidation validation;
    @Autowired
    private OpenEndPoint openEndPoint;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        if(openEndPoint.needAuth.test(exchange.getRequest())) {
        
            String token = exchange.getRequest().getHeaders().getFirst("Authorization");
            if(token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                try {
                    validation.validateToken(token);
                } catch (Exception e) {
                    return this.onError(exchange);
                }
            }
            else {
                return this.onError(exchange);
            }
        }
        return chain.filter(exchange);
    }

    public Mono<Void> onError (ServerWebExchange exchange){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

}