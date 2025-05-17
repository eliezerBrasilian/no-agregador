package com.cryxie.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("service_a", r -> r
                        .path("/api/service-a/**")
                        .uri("lb://SERVICE-A"))
                .route("service_b", r -> r
                        .path("/api/service-b/**")
                        .uri("lb://SERVICE-B"))
                .build();
    }
}