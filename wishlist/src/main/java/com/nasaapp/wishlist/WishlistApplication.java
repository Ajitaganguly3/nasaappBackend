package com.nasaapp.wishlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCaching
@EnableWebMvc
@ComponentScan
public class WishlistApplication {

	public static void main(String[] args) {
		SpringApplication.run(WishlistApplication.class, args);
	}

	@Bean
	public OpenAPI nasaAppOpenAPI() {
		String description = "This application performs viewing all images in wishlist, saving an image to the wishlist, and deleting an image from wishlist";
		String email = "ajita.ganguly@cognizant.com";
		return new OpenAPI().info(new Info().title("Nasa App").description(description)
				.contact(new Contact().email(email).name("Ajita Ganguly").url("ajita.com")));
	}

}
