package com.nasaApp.registration.exceptionHandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nasaApp.registration.exceptions.InvalidPasswordException;
import com.nasaApp.registration.exceptions.UserNotFoundException;
import com.nasaApp.registration.exceptions.UsernameAlreadyExistException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<String> invalidPasswordException(InvalidPasswordException ex){
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	
	@ExceptionHandler(UsernameAlreadyExistException.class)
	public ResponseEntity<String> usernameAlreadyExistException(UsernameAlreadyExistException ex){
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> userNotFoundException(UserNotFoundException ex){
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
}
