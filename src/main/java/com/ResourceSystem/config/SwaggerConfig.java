package com.ResourceSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

        @Bean
        public OpenAPI resourceBookingOpenAPI() {

                return new OpenAPI()

                                .info(new Info().title("Resource Booking System API")
                                                .description("REST API for Resource Booking System built with Spring Boot, Spring Security, JWT, JPA, and MySQL.")

                                                .version("1.0.0")

                                                .contact(new Contact()

                                                                .name("your User name").email("your email address"))

                                                .license(new License().name("Open Source"))

                                );

        }

}