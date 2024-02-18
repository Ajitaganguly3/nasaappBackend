package com.nasaApp.registration.service;

import com.nasaApp.registration.dto.UserProfileDTO;
import com.nasaApp.registration.exceptions.InvalidPasswordException;
import com.nasaApp.registration.exceptions.UserNotFoundException;
import com.nasaApp.registration.exceptions.UsernameAlreadyExistException;
import com.nasaApp.registration.response.MessageResponse;
import com.nasaApp.registration.response.UserCredentialsResponse;

public interface UserProfileService {

	public MessageResponse register(UserProfileDTO userProfileDto)
			throws UsernameAlreadyExistException, InvalidPasswordException;

	public UserCredentialsResponse getUserInfo(String username) throws UserNotFoundException;

}
