package com.nasaApp.login.responses;

import org.springframework.http.HttpStatus;

public class SuccessResponse {

	private String message;

	private String username;

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public SuccessResponse(String message, String username, String token) {
		super();
		this.message = message;
		this.username = username;
		this.token = token;
	}

	public SuccessResponse(String message, HttpStatus ok) {
		// TODO Auto-generated constructor stub
		super();

	}
}
