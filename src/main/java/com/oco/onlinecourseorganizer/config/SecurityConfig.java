package com.oco.onlinecourseorganizer.config;

import com.oco.onlinecourseorganizer.service.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AppUserDetailsService userDetailsService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;


    // This bean links Spring Security to our custom AppUserDetailsService and password encoder
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    // This method sets up which pages require login and how login/logout is handled
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/register", "/login", "/css/**").permitAll() // public pages*** add"/error",** !!!!
                .requestMatchers("/admin/**").hasRole("ADMIN") // admin-only URLs
                .requestMatchers("/student/**").hasRole("STUDENT") // student-only URLs
                .anyRequest().authenticated() // everything else requires login
                )
                .formLogin(form -> form
                .loginPage("/login") // custom login page
                .defaultSuccessUrl("/redirect", true) // redirect to dashboard after login
                .permitAll()
                )
                .logout(logout -> logout
                .logoutSuccessUrl("/") // go to home page on logout
                .permitAll()
                );

        return http.build();
    }
}
