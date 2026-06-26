package com.asycoo.library.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    @Profile("!test")
    SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthFilter jwtAuthFilter,
            SecurityProblemHandler problemHandler) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**", "/api/hello").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/members").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/books", "/api/books/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/members/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/h2-console/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/books").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/books/**").hasRole("ADMIN")
                        .anyRequest().permitAll())
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(problemHandler)
                        .accessDeniedHandler(problemHandler))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));
        return http.build();
    }

    @Bean
    @Profile("test")
    SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .build();
    }
}
