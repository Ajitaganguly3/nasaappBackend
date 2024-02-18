package com.nasaApp.registration.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;

import com.nasaApp.registration.dto.UserProfileDTO;
import com.nasaApp.registration.entity.UserProfile;
import com.nasaApp.registration.exceptions.InvalidPasswordException;
import com.nasaApp.registration.exceptions.UserNotFoundException;
import com.nasaApp.registration.exceptions.UsernameAlreadyExistException;
import com.nasaApp.registration.repository.UserProfileRepository;
import com.nasaApp.registration.response.MessageResponse;
import com.nasaApp.registration.response.UserCredentialsResponse;
import com.nasaApp.registration.validation.UserProfileValidation;

public class UserProfileServiceImplTest {

	@Mock
	private UserProfileRepository userProfileRepository;

	@Mock
	private KafkaTemplate<String, Object> kafkaTemplate;

	@InjectMocks
	private UserProfileServiceImpl userProfileService;

	private UserProfileDTO sampleUserProfileDTO;

	@BeforeEach
	void setUp() {
		// Initializing any common test data or objects
		MockitoAnnotations.openMocks(this);
		sampleUserProfileDTO = new UserProfileDTO("ajita", "ganguly", "ajita@mail.com", "ajita1", "9914366429",
				"Ajita@123", "Ajita@123");
	}

	@AfterEach
	void tearDown() {
		// Cleaning up or reset any changes made during the test
		sampleUserProfileDTO = null;
	}

	@Test
	void testRegister_SuccessfulRegistration() throws UsernameAlreadyExistException, InvalidPasswordException {

		when(userProfileRepository.findById(sampleUserProfileDTO.getUsername())).thenReturn(Optional.empty());
		when(userProfileRepository.save(any(UserProfile.class))).thenReturn(new UserProfile());

		MessageResponse response = userProfileService.register(sampleUserProfileDTO);

		// Assert
		assertNotNull(response);
		assertEquals("User Registered Successfully!", response.getMessage());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void testRegister_UsernameAlreadyExists() {

		when(userProfileRepository.findById(sampleUserProfileDTO.getUsername()))
				.thenReturn(Optional.of(new UserProfile()));

		// Assert
		assertThrows(UsernameAlreadyExistException.class, () -> userProfileService.register(sampleUserProfileDTO));
	}

	@Test
	void testRegister_InvalidPassword() {

		sampleUserProfileDTO.setPassword("invalidPassword");

		// Assert
		assertThrows(InvalidPasswordException.class, () -> userProfileService.register(sampleUserProfileDTO));
	}

	@Test
	void testRegister_NullPassword() {
		sampleUserProfileDTO.setPassword(null);

		// Assert
		assertFalse(UserProfileValidation.isPasswordValid(sampleUserProfileDTO.getPassword()));
	}

	@Test
	void testRegister_PasswordMismatch() {
		sampleUserProfileDTO.setConfirmPassword("mismatchedPassword");

		// Assert
		assertThrows(InvalidPasswordException.class, () -> userProfileService.register(sampleUserProfileDTO));
	}

	@Test
	void testRegister_PasswordMatch() throws UsernameAlreadyExistException, InvalidPasswordException {
		when(userProfileRepository.findById(sampleUserProfileDTO.getUsername())).thenReturn(Optional.empty());

		when(userProfileRepository.save(any(UserProfile.class))).thenReturn(new UserProfile());

		MessageResponse response = userProfileService.register(sampleUserProfileDTO);

		// Assert
		assertNotNull(response);
		assertEquals("User Registered Successfully!", response.getMessage());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void testGetUserInfo_UserExists() throws UserNotFoundException {

		String username = "testUsername";
		when(userProfileRepository.findByUsername(username)).thenReturn(Optional.of(new UserProfile()));

		UserCredentialsResponse response = userProfileService.getUserInfo(username);

		// Assert
		assertNotNull(response);

	}

	@Test
	void testGetUserInfo_UserNotFound() {

		String username = "nonexistentUser";
		when(userProfileRepository.findByUsername(username)).thenReturn(Optional.empty());

		// Assert
		assertThrows(UserNotFoundException.class, () -> userProfileService.getUserInfo(username));
	}

}
