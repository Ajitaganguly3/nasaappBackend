package com.nasaApp.login.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

import com.nasaApp.login.entity.Authentication;
import com.nasaApp.login.exceptions.LoginException;
import com.nasaApp.login.repository.AuthRepository;
import com.nasaApp.login.responses.RegistrationResponse;
import com.nasaApp.login.responses.SuccessResponse;
import com.nasaApp.login.util.JWTUtil;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

	@InjectMocks
	private AuthServiceImpl authService;

	@Mock
	private JWTUtil jwtUtil;

	@Mock
	private AuthRepository authRepository;

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private KafkaTemplate<String, Object> kafkaTemplate;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testAuthenticateUser_SuccessfulLogin() throws LoginException {
		// Mocking external service response
		RegistrationResponse registrationResponse = new RegistrationResponse("testUser", "Password@123");
		when(restTemplate.getForObject(eq("http://localhost:8083/user/credentials/testUser"),
				eq(RegistrationResponse.class), any(Authentication.class))).thenReturn(registrationResponse);

		// Mocking JWT token generation
		when(jwtUtil.generateToken("testUser")).thenReturn("mockedToken");

		// Mocking AuthRepository save
		when(authRepository.save(any(Authentication.class))).thenReturn(null);

		// Test the actual authentication method
		Authentication authentication = new Authentication("testUser", "Password@123");
		SuccessResponse successResponse = authService.authenticateUser(authentication);

		assertEquals("Login Successful!", successResponse.getMessage());
		assertEquals("testUser", successResponse.getUsername());
		assertEquals("mockedToken", successResponse.getToken());
	}

	@Test
	void testAuthenticateUser_InvalidCredentials() {
		// Mocking external service response
		RegistrationResponse registrationResponse = new RegistrationResponse("testUser", "Password123");
		when(restTemplate.getForObject(eq("http://localhost:8083/user/credentials/testUser"),
				eq(RegistrationResponse.class), any(Authentication.class))).thenReturn(registrationResponse);

		// Test the case where credentials are invalid
		Authentication authentication = new Authentication("testUser", "wrongPassword");
		LoginException loginException = assertThrows(LoginException.class,
				() -> authService.authenticateUser(authentication));
		assertEquals("Invalid Credentials", loginException.getMessage());
	}

	@Test
	void testGetUserInfo() {
		// Mocking external service response
		RegistrationResponse registrationResponse = new RegistrationResponse("testUser", "Password@123");
		Mockito.when(restTemplate.getForObject(eq("http://localhost:8083/user/credentials/testUser"),
				eq(RegistrationResponse.class))).thenReturn(registrationResponse);

		// Test the method to get user info
		Authentication userInfo = authService.getUserInfo("testUser");

		assertEquals("testUser", userInfo.getUsername());
		assertEquals("Password@123", userInfo.getPassword());
	}

}
