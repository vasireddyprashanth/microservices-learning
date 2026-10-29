package com.prash.ecommerce.api_gateway.filters;

import com.prash.ecommerce.api_gateway.security.JwtConfig;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    private JwtConfig jwtConfig;
    public AuthenticationGatewayFilterFactory(JwtConfig jwtConfig) {
        super(Config.class);
        this.jwtConfig= jwtConfig;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            String authorizationHeader = exchange.getRequest().getHeaders().get("Authorization").getFirst();

            if(authorizationHeader==null){
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            String token = authorizationHeader.substring(7);

            Long userId = jwtConfig.getUserIdFromToken(token);

            ServerHttpRequest build = exchange.getRequest().mutate().headers(
                    httpHeaders -> {
                        httpHeaders.remove("X-User-Id");
                        httpHeaders.remove("X-User-Roles");
                    })
                    .header("X-User-Id", userId.toString())
                    .build();

            ServerWebExchange exchange1 = exchange.mutate().request(build).build();

            return chain.filter(exchange1);
        };
    }


    public static class Config{
        private boolean isEnabled;
    }
}


