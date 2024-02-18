package com.nasaApp.login.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nasaApp.login.entity.Authentication;
import com.nasaApp.login.exceptions.LoginException;
import com.nasaApp.login.responses.SuccessResponse;
import com.nasaApp.login.service.AuthService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	private Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Operation(summary = "To perform login")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Login successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))),
			@ApiResponse(responseCode = "400", description = "Username doesn't exist or Password validation failed", content = @Content) })

	@PostMapping("/authenticate")
	public SuccessResponse login(@RequestBody Authentication authentication) throws LoginException {
		logger.info("Login Successful");
		return authService.authenticateUser(authentication);
	}

	@GetMapping("/getUserInfo/{username}")
	@CircuitBreaker(name = "userInfoBreaker", fallbackMethod = "userInfoFallback")
	public ResponseEntity<Authentication> getUserInfo(@PathVariable String username) {

		logger.info("User details retrived successfully");
		Authentication user = authService.getUserInfo(username);
		return ResponseEntity.ok(user);

	}

	@GetMapping("/health")
	public String healthCheck() {
		return "OK";
	}

	public ResponseEntity<Authentication> userInfoFallback(String username, Exception ex) {

		ex.printStackTrace();
		logger.info("Fallback is executed because service is down : ", ex.getMessage());

		Authentication user = new Authentication("dummyUser", "dummyPassword");

		return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
	}

}
