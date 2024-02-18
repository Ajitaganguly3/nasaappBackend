package com.nasaApp.registration.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nasaApp.registration.dto.UserProfileDTO;
import com.nasaApp.registration.exceptions.InvalidPasswordException;
import com.nasaApp.registration.exceptions.UserNotFoundException;
import com.nasaApp.registration.exceptions.UsernameAlreadyExistException;
import com.nasaApp.registration.response.MessageResponse;
import com.nasaApp.registration.response.UserCredentialsResponse;
import com.nasaApp.registration.service.UserProfileService;

public class UserProfileControllerTest {

	private final UserProfileService userProfileService = Mockito.mock(UserProfileService.class);

	private UserProfileController userProfileController = new UserProfileController(userProfileService);

	private final ObjectMapper objectMapper = new ObjectMapper();

	private UserProfileDTO sampleUserProfileDTO;

	@BeforeEach
	void setUp() {
		// Initializing any common test data or objects
		sampleUserProfileDTO = new UserProfileDTO("ajita", "ganguly", "ajita@mail.com", "ajita1", "9914366429",
				"Ajita@123", "Ajita@123");
	}

	@AfterEach
	void tearDown() {
		// Cleaning up or reset any changes made during the test
		sampleUserProfileDTO = null;
	}

	@Test
	void testRegistrationSuccess() throws InvalidPasswordException, UsernameAlreadyExistException {

		MessageResponse expectedResponse = new MessageResponse("User registered successfully", HttpStatus.OK);

		// Mocking the service method
		when(userProfileService.register(any(UserProfileDTO.class))).thenReturn(expectedResponse);

		ResponseEntity<MessageResponse> responseEntity = userProfileController.register(sampleUserProfileDTO);

		assertEquals(expectedResponse, responseEntity.getBody());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void testRegistrationFailure() throws InvalidPasswordException, UsernameAlreadyExistException {

		when(userProfileService.register(any(UserProfileDTO.class))).thenReturn(null);

		ResponseEntity<MessageResponse> responseEntity = userProfileController.register(sampleUserProfileDTO);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

	@Test
	void testGetUserInfoSuccess() throws UserNotFoundException {

		String username = "testUsername";
		UserCredentialsResponse expectedCredentials = new UserCredentialsResponse("ajita", "Ajita@123");

		// Mocking the service method
		when(userProfileService.getUserInfo(username)).thenReturn(expectedCredentials);

		ResponseEntity<UserCredentialsResponse> responseEntity = userProfileController.getUserInfo(username);

		assertEquals(expectedCredentials, responseEntity.getBody());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void testGetUserInfoFailure() throws UserNotFoundException {
		String username = "nonExistingUser";

		when(userProfileService.getUserInfo(username)).thenReturn(null);

		ResponseEntity<UserCredentialsResponse> responseEntity = userProfileController.getUserInfo(username);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}

}
