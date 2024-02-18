package com.nasaApp.nasaapigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.ServletException;
import reactor.core.publisher.Mono;

@Component
public class Filter implements GatewayFilter {

	@Value("${jwt.secret.key}")
	private String secretKey;

	Logger logger = LoggerFactory.getLogger(Filter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// TODO Auto-generated method stub
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();

		if (!request.getHeaders().containsKey("Authorization")) {
			response.setStatusCode(HttpStatus.UNAUTHORIZED);
			// System.out.println("Does not contain Authorization header");
			return onError(response, "Authorization header is missing", HttpStatus.UNAUTHORIZED);
		}

		String token = request.getHeaders().getOrEmpty("Authorization").get(0);

		if (token == null) {
			response.setStatusCode(HttpStatus.UNAUTHORIZED);
			// System.out.println("Does not contain Bearer");
			return onError(response, "Invalid Authorization header", HttpStatus.UNAUTHORIZED);
		}

		Claims claims = null;
		logger.info("token: {}", token);
		try {
			claims = getClaims(token);
			logger.info("claims");

			String username = claims.getSubject(); // this will return you the current username (emailid)
			request.mutate().header("username", username).header("Authorization", token);
			// current logged in user

		} catch (Exception e) {
			response.setStatusCode(HttpStatus.UNAUTHORIZED);
			// System.out.println("Does not contain Valid token");
			logger.error("Error obtaining claims: {}", e.getMessage());
			logger.info("token");
			return onError(response, "Invalid token", HttpStatus.UNAUTHORIZED);
		}
		return chain.filter(exchange);
	}

	private Mono<Void> onError(ServerHttpResponse response, String errorMessage, HttpStatus status) {
		response.setStatusCode(status);
		response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
		return response.writeWith(Mono.just(response.bufferFactory().wrap(errorMessage.getBytes())));
	}

	private Claims getClaims(String jwtToken) throws ServletException {
		try {
			logger.info("jwttoken: {}", jwtToken);
			// Jws<Claims> claimJws =
			// Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
			// logger.info("claimsJWS: ", claimJws.getBody());
			// Claims claim = claimJws.getBody();
			System.out.println(secretKey);
			return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();

		} catch (MalformedJwtException me) {
			logger.info("malformed jwtexception in claims");
			throw new ServletException("Token has been modified by unauthorized user");
		} catch (ExpiredJwtException ee) {
			logger.info("expired jwt exception in claims");
			throw new ServletException("Token expired");
		} catch (IllegalArgumentException le) {
			logger.info("Illegal argument exception in claims");
			throw new ServletException("Check and relogin");
		}

	}

}
