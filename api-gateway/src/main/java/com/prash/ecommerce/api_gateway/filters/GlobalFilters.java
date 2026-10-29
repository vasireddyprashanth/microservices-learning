package com.prash.ecommerce.api_gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

import java.io.IOException;

@Component
@Slf4j
public class GlobalFilters implements GlobalFilter, Ordered {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //Pre-filter
        log.info("the request is made {}", exchange.getRequest().getURI());


        return chain.filter(exchange).then( Mono.fromRunnable(()->{
            //Post-phase filter
            log.info("the request is made {}", exchange.getResponse().getStatusCode());
        }));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
