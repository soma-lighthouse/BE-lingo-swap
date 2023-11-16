package com.lighthouse.lingoswap.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationManager authenticationManager;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http
                .authenticationManager(authenticationManager)
                .addFilterAt(jwtAuthenticationFilter, BasicAuthenticationFilter.class)
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/admin/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/auth/**", "/api/v1/user/upload/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/user/form/**", "/actuator/health").permitAll()
                .requestMatchers(HttpMethod.GET, "/.well-known/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .anonymous().disable()
                .logout().disable();

        return http.build();
    }

}
