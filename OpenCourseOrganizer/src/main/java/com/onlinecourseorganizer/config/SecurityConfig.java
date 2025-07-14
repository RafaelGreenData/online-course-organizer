package com.onlinecourseorganizer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Allow all pages
            )
            .csrf(csrf -> csrf.disable()) // Disable CSRF for testing
            .formLogin(Customizer.withDefaults()); // Optional: shows default login if you visit /login
        return http.build();
    }
}
