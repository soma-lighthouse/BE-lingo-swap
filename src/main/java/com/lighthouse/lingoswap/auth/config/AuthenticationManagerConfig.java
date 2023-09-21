package com.lighthouse.lingoswap.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;

@RequiredArgsConstructor
@Configuration
public class AuthenticationManagerConfig {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Bean
    AuthenticationManager authenticationManager() {
        return new ProviderManager(jwtAuthenticationProvider);
    }
}
