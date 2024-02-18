package com.nasaApp.nasaapigateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.nasaApp.nasaapigateway.filter.Filter;

@SpringBootApplication
@EnableDiscoveryClient
@CrossOrigin(origins = "*")
public class NasaApiGatewayApplication {

	@Autowired
	private Filter apiFilter;

	public static void main(String[] args) {
		SpringApplication.run(NasaApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator apiRoutes(RouteLocatorBuilder builder) {

		return builder.routes()

				.route("apod-service",
						route -> route.path("/apod/**").filters(f -> f.filter(apiFilter)).uri("lb://APOD-SERVICE"))
				.route("wishlist-service",
						route -> route.path("/wishlist/**").filters(f -> f.filter(apiFilter))
								.uri("lb://WISHLIST-SERVICE"))
				.route("registration-service", route -> route.path("/user/**").uri("lb://REGISTRATION-SERVICE"))
				.route("auth-service", route -> route.path("/auth/**").uri("lb://AUTH-SERVICE")).build();

	}

}
