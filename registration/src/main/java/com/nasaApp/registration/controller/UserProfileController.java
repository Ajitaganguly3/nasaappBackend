package com.nasaApp.registration.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nasaApp.registration.dto.UserProfileDTO;
import com.nasaApp.registration.exceptions.InvalidPasswordException;
import com.nasaApp.registration.exceptions.UserNotFoundException;
import com.nasaApp.registration.exceptions.UsernameAlreadyExistException;
import com.nasaApp.registration.response.MessageResponse;
import com.nasaApp.registration.response.UserCredentialsResponse;
import com.nasaApp.registration.service.UserProfileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserProfileController {

	UserProfileService userProfileService;

	private Logger logger = LoggerFactory.getLogger(UserProfileController.class);

	@Autowired
	public UserProfileController(UserProfileService userProfileService) {
		this.userProfileService = userProfileService;
	}

	@Operation(summary = "To perform registration")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "400", description = "Username already exist or Password validation failed", content = @Content) })

	@PostMapping("/register")
	public ResponseEntity<MessageResponse> register(@RequestBody UserProfileDTO userProfileDTO)
			throws InvalidPasswordException, UsernameAlreadyExistException {
		MessageResponse messageResponse = userProfileService.register(userProfileDTO);
		if (messageResponse != null) {
			logger.info("Registration successful");
			return ResponseEntity.ok(messageResponse);

		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@Operation(summary = "To view username and password of the registered user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Credentials viewed successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "400", description = "Username doesn't exist", content = @Content) })

	@GetMapping("credentials/{username}")
	public ResponseEntity<UserCredentialsResponse> getUserInfo(@PathVariable String username)
			throws UserNotFoundException {
		UserCredentialsResponse credentials = userProfileService.getUserInfo(username);
		if (credentials != null) {
			logger.info("User details shown");
			return ResponseEntity.ok(credentials);
		} else {
			return ResponseEntity.notFound().build();
		}

	}

}
