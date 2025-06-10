package com.miguel.UTILS;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Bean
    public YourJwtUtil jwtUtil() {
        return new YourJwtUtil(jwtSecret);
    }
}
