package com.nasaApp.registration.exceptionHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.nasaApp.registration.exceptions.InvalidPasswordException;
import com.nasaApp.registration.exceptions.UserNotFoundException;
import com.nasaApp.registration.exceptions.UsernameAlreadyExistException;

public class GlobalExceptionHandlerTest {

	@Test
	void testInvalidPasswordException() {
		InvalidPasswordException ex = new InvalidPasswordException(
				"Password should have atleast 1 lowercase, uppercase, special character");

		GlobalExceptionHandler handler = new GlobalExceptionHandler();

		ResponseEntity<String> response = handler.invalidPasswordException(ex);

		assertEquals(ResponseEntity.badRequest()
				.body("Password should have atleast 1 lowercase, uppercase, special character"), response);
	}

	@Test
	void testUsernameAlreadyExistsException() {
		UsernameAlreadyExistException ex = new UsernameAlreadyExistException("Username already exists");

		GlobalExceptionHandler handler = new GlobalExceptionHandler();

		ResponseEntity<String> response = handler.usernameAlreadyExistException(ex);

		assertEquals(ResponseEntity.badRequest().body("Username already exists"), response);

	}

	@Test
	void testUserNotFoundException() {
		UserNotFoundException ex = new UserNotFoundException("Username doesn't exist!");

		GlobalExceptionHandler handler = new GlobalExceptionHandler();

		ResponseEntity<String> response = handler.userNotFoundException(ex);

		assertEquals(ResponseEntity.badRequest().body("Username doesn't exist!"), response);

	}
}
