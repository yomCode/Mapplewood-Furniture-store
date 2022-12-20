package com.decagon.OakLandv1be;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.decagon.OakLandv1be.config.jwt.RSAKeyProperties;
import com.decagon.OakLandv1be.entities.Customer;
import com.decagon.OakLandv1be.entities.Person;
import com.decagon.OakLandv1be.entities.Product;
import com.decagon.OakLandv1be.entities.SubCategory;
import com.decagon.OakLandv1be.enums.Gender;
import com.decagon.OakLandv1be.enums.Role;
import com.decagon.OakLandv1be.repositries.PersonRepository;
import com.decagon.OakLandv1be.repositries.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableConfigurationProperties(RSAKeyProperties.class)
@EnableSwagger2
@RequiredArgsConstructor
public class OakLandV1BeApplication {
	private final PersonRepository personRepository;
	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(OakLandV1BeApplication.class, args);
	}
	@Bean
	public Docket productApi(){
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.decagon.OakLandv1be")).build();
	}

	@Bean
	public CommandLineRunner commandLineRunner(ProductRepository productRepository) {
		return args -> {
			Product product = Product.builder()
					.name("center table")
					.price(20.0d)
					.imageUrl("")
					.availableQty(10)
					.subCategory(new SubCategory())
					.customer(new Customer())
					.color("blue")
					.description("new products")
					.build();

			product.setId(23L);

			Person person = Person.builder()
					.firstName("Benson")
					.lastName("Malik")
					.email("benson@gmail.com")
					.gender(Gender.MALE)
					.date_of_birth("13-08-1990")
					.phone("9859595959")
					.verificationStatus(true)
					.password(passwordEncoder.encode("password123"))
					.address("No Address")
					.role(Role.ADMIN)
					.build();

			personRepository.save(person);
			productRepository.save(product);
		};
	}


}
