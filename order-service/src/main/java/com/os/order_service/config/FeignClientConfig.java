package com.os.order_service.config;

import com.turkcell.tcell.core.security.BaseJwtService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Map;

@Configuration
public class FeignClientConfig {

    private final BaseJwtService jwtService;

    public FeignClientConfig(BaseJwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                String username = SecurityContextHolder.getContext().getAuthentication().getName();
                String token = jwtService.generateToken(username, Map.of("roles", List.of("USER")));
                requestTemplate.header("Authorization", "Bearer " + token);
            }
        };
    }
}