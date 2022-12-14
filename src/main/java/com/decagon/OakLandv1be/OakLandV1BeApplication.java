package com.decagon.OakLandv1be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class OakLandV1BeApplication {

	public static void main(String[] args) {
		SpringApplication.run(OakLandV1BeApplication.class, args);
	}
	@Bean
	public Docket productApi(){
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.decagon.OakLandv1be")).build();
	}
}
