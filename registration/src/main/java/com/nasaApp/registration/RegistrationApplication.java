package com.nasaApp.registration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
@EnableDiscoveryClient
@EnableWebMvc
@ComponentScan
public class RegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistrationApplication.class, args);
	}

	@Bean
	public OpenAPI nasaAppOpenAPI() {
		String description = "This application performs registration services";
		String email = "ajita.ganguly@cognizant.com";
		return new OpenAPI().info(new Info().title("Nasa App").description(description)
				.contact(new Contact().email(email).name("Ajita Ganguly").url("ajita.com")));
	}

}
