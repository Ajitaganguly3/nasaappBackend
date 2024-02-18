package com.nasaApp.login.service;

import com.nasaApp.login.entity.Authentication;
import com.nasaApp.login.exceptions.LoginException;
import com.nasaApp.login.responses.SuccessResponse;

public interface AuthService {

	SuccessResponse authenticateUser(Authentication authentication) throws LoginException;

	Authentication getUserInfo(String username);

}
