package com.ResourceSystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor

public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(
                                (request, response, exception) -> {
                                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                    response.getWriter()
                                            .write("Access Denied");
                                }))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**").permitAll()

                        // User and Admin get all resourses
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/resources/**")
                        .hasAnyRole("ADMIN", "USER")

                        // only admin can add resources
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/resources/**")
                        .hasRole("ADMIN")

                        // Only ADMIN can update resource
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/resources/**")
                        .hasRole("ADMIN")

                        // Only ADMIN can delete resource
                        .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/resources/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/reservations/**")
                        .hasAnyRole("ADMIN", "USER").anyRequest().authenticated());

        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

}