package com.decagon.OakLandv1be.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private ApiInfo apiInfo() {
        return new ApiInfo("Oakland Furniture",
                "An api for purchase and payment for furniture",
                "1.0",
                "Free to use and authenticate for protected endpoints",
                new Contact("Oakland", "www.oaklandfurniture.com", "funitureoakland@gmail.com"),
                "License of API",
                "www.oaklandfurniture.com",
                Collections.emptyList());
    }

}