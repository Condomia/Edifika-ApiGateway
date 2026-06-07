package com.edifika.apigateway.infrastructure.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Global filter that propagates JWT token and request metadata
 * to all downstream microservices.
 */
@Component
public class UserInfoPropagationFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoPropagationFilter.class);
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(AUTHORIZATION_HEADER);

        ServerHttpRequest.Builder requestBuilder = exchange.getRequest().mutate()
                .header("X-Gateway-Request", "true")
                .header("X-Request-Path", path);

        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            requestBuilder.header(AUTHORIZATION_HEADER, authHeader);
            LOGGER.info("[GATEWAY] Propagating token - Path: {}", path);
        } else {
            LOGGER.warn("[GATEWAY] No Authorization header - Path: {}", path);
        }

        return chain.filter(exchange.mutate().request(requestBuilder.build()).build());
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}