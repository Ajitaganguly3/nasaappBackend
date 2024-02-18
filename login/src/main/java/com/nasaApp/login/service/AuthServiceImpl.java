package com.nasaApp.login.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nasaApp.login.config.AppConstants;
import com.nasaApp.login.entity.Authentication;
import com.nasaApp.login.exceptions.LoginException;
import com.nasaApp.login.repository.AuthRepository;
import com.nasaApp.login.responses.RegistrationResponse;
import com.nasaApp.login.responses.SuccessResponse;
import com.nasaApp.login.util.JWTUtil;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private AuthRepository authRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	private Logger logger = LoggerFactory.getLogger(AuthService.class);

	@Override
	public SuccessResponse authenticateUser(Authentication authentication) throws LoginException {
		// TODO Auto-generated method stub

		RegistrationResponse registrationResponse = restTemplate.getForObject(
				"http://localhost:8083/user/credentials/" + authentication.getUsername(), RegistrationResponse.class,
				authentication);
		logger.info(registrationResponse.getUsername());
		logger.info(registrationResponse.getPassword());
		// Validating password and generating JWT token
		if (registrationResponse != null && registrationResponse.getPassword().equals(authentication.getPassword())) {
			String token = jwtUtil.generateToken(registrationResponse.getUsername());
			authRepository.save(authentication);
			String message = "Login successful for user: " + authentication.getUsername()
					+ " and the token generated is: " + token;
			kafkaTemplate.send(AppConstants.NASAAPP_LOGIN_TOPIC_NAME, message);

			return new SuccessResponse("Login Successful!", authentication.getUsername(), token);
		} else {
			// Handling invalid credentials
			throw new LoginException("Invalid Credentials");
		}
	}

	public Authentication getUserInfo(String username) {

		RegistrationResponse registrationResponse = restTemplate
				.getForObject("http://localhost:8083/user/credentials/" + username, RegistrationResponse.class);
		Authentication user = new Authentication();
		user.setUsername(username);
		user.setPassword(registrationResponse.getPassword());

		return user;

	}

	@KafkaListener(topics = AppConstants.NASAAPP_REGISTRATION_TOPIC_NAME, groupId = AppConstants.GROUP_ID)
	public void consumeRegistrationMessage(String message) {
		System.out.println("Received registation message: " + message);
	}

}
