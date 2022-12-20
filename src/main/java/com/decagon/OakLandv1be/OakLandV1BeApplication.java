package com.decagon.OakLandv1be;

import com.decagon.OakLandv1be.config.jwt.RSAKeyProperties;
import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.repositries.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableConfigurationProperties(RSAKeyProperties.class)
@EnableSwagger2
public class OakLandV1BeApplication {

	public static void main(String[] args) {
		SpringApplication.run(OakLandV1BeApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ProductRepository productRepository) {
		return args -> {
			Product product= Product.builder()
					.name("Oppola")
					.price(40000.00)
					.imageUrl("www.google.com")
					.color("yellow")
					.description("lovely fur")
					.build();
			productRepository.save(product);
		};
	}
}
