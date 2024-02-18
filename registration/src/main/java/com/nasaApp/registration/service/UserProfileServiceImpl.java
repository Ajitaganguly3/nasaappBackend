package com.nasaApp.registration.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.nasaApp.registration.config.AppConstants;
import com.nasaApp.registration.dto.UserProfileDTO;
import com.nasaApp.registration.entity.UserProfile;
import com.nasaApp.registration.exceptions.InvalidPasswordException;
import com.nasaApp.registration.exceptions.UserNotFoundException;
import com.nasaApp.registration.exceptions.UsernameAlreadyExistException;
import com.nasaApp.registration.repository.UserProfileRepository;
import com.nasaApp.registration.response.MessageResponse;
import com.nasaApp.registration.response.UserCredentialsResponse;
import com.nasaApp.registration.validation.UserProfileValidation;

import jakarta.validation.Valid;

@Service
public class UserProfileServiceImpl implements UserProfileService {

	private final UserProfileRepository userProfileRepository;

private final KafkaTemplate<String, Object> kafkaTemplate;

	public UserProfileServiceImpl(UserProfileRepository userProfileRepository, KafkaTemplate<String, Object> kafkaTemplate
			) {
		this.userProfileRepository = userProfileRepository;
		this.kafkaTemplate = kafkaTemplate;
	}

	@Override
	public MessageResponse register(@Valid UserProfileDTO userProfileDto)
			throws UsernameAlreadyExistException, InvalidPasswordException {
		// TODO Auto-generated method stub

		Optional<UserProfile> existingUser = userProfileRepository.findById(userProfileDto.getUsername());
		if (existingUser.isPresent()) {
			throw new UsernameAlreadyExistException("Username already exists");
		}
		if (!UserProfileValidation.isPasswordValid(userProfileDto.getPassword())) {
			throw new InvalidPasswordException(
					"Password should have atleast 1 lowercase, uppercase, special character");
		}
		if (!UserProfileValidation.compareConfirmPasswordAndPasswordFields(userProfileDto.getPassword(),
				userProfileDto.getConfirmPassword())) {
			throw new InvalidPasswordException(
					"Password should have atleast 1 lowercase, uppercase, special character");
		}

		UserProfile userProfile = new UserProfile();
		userProfile.setUsername(userProfileDto.getUsername());
		userProfile.setFirstName(userProfileDto.getFirstName());
		userProfile.setLastName(userProfileDto.getLastName());
		userProfile.setPassword(userProfileDto.getPassword());
		userProfile.setConfirmPassword(userProfileDto.getConfirmPassword());
		userProfile.setEmail(userProfileDto.getEmail());
		userProfile.setContactNumber(userProfileDto.getContactNumber());
		userProfileRepository.save(userProfile);

		String message = "Registration successful for user: " + userProfile.getUsername();
		kafkaTemplate.send(AppConstants.NASAAPP_REGISTRATION_TOPIC_NAME, message);
		return new MessageResponse("User Registered Successfully!", HttpStatus.OK);
	}

	@Override
	public UserCredentialsResponse getUserInfo(String username) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Optional<UserProfile> optionalUser = userProfileRepository.findByUsername(username);
		if (optionalUser.isPresent()) {
			UserProfile user = optionalUser.get();
			UserCredentialsResponse userCredentialsResponse = new UserCredentialsResponse();
			userCredentialsResponse.setUsername(user.getUsername());
			userCredentialsResponse.setPassword(user.getPassword());
			return userCredentialsResponse;

		} else {
			throw new UserNotFoundException("Username doesn't exist!");
		}
	}

}
