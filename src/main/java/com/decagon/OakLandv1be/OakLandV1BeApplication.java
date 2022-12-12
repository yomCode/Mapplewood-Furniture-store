package com.decagon.OakLandv1be;

import com.decagon.OakLandv1be.config.jwt.RSAKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RSAKeyProperties.class)
public class OakLandV1BeApplication {

	public static void main(String[] args) {
		SpringApplication.run(OakLandV1BeApplication.class, args);
	}

}
