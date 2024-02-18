package com.nasaApp.login.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nasaApp.login.entity.Authentication;
import com.nasaApp.login.exceptions.LoginException;
import com.nasaApp.login.responses.SuccessResponse;
import com.nasaApp.login.service.AuthService;

public class AuthControllerTest {
	@Mock
	private AuthService authService;

	@InjectMocks
	private AuthController authController;

	private Logger logger = LoggerFactory.getLogger(AuthController.class);

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testLoginSuccess() throws LoginException {
		Authentication authentication = new Authentication("username", "password");
		SuccessResponse expectedResponse = new SuccessResponse("Login Successful", "username", "token");

		when(authService.authenticateUser(authentication)).thenReturn(expectedResponse);

		SuccessResponse actualResponse = authController.login(authentication);

		assertEquals(expectedResponse, actualResponse);
		verify(authService, times(1)).authenticateUser(authentication);
	}

	@Test
	void testGetUserInfoSuccess() {
		String username = "testUser";
		Authentication expectedUser = new Authentication(username, "password");
		ResponseEntity<Authentication> expectedResponse = ResponseEntity.ok(expectedUser);

		when(authService.getUserInfo(username)).thenReturn(expectedUser);

		ResponseEntity<Authentication> actualResponse = authController.getUserInfo(username);

		assertEquals(expectedResponse, actualResponse);
		verify(authService, times(1)).getUserInfo(username);
	}

	@Test
	void testHealthCheck() {
		assertEquals("OK", authController.healthCheck());
	}

	@Test
	void testUserInfoFallback() {
		String username = "dummyUser";
		Exception testException = new RuntimeException("Service is down");

		ResponseEntity<Authentication> fallbackResponse = authController.userInfoFallback(username, testException);

		assertNotNull(fallbackResponse);
		assertEquals(HttpStatus.BAD_REQUEST, fallbackResponse.getStatusCode());

		Authentication fallbackUser = fallbackResponse.getBody();
		assertNotNull(fallbackUser);
		assertEquals("dummyUser", fallbackUser.getUsername());
		assertEquals("dummyPassword", fallbackUser.getPassword());
	}

}
