package com.nasaapp.apod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
@EnableDiscoveryClient
@EnableWebMvc
@ComponentScan
public class ApodApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApodApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public OpenAPI nasaAppOpenAPI() {
		String description = "This application List out the details of Astronomy Picture of the Day using Third Party API";
		String email = "ajita.ganguly@cognizant.com";
		return new OpenAPI().info(new Info().title("Nasa App").description(description)
				.contact(new Contact().email(email).name("Ajita Ganguly").url("ajita.com")));
	}

}
