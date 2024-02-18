package com.nasaApp.registration.response;

public class UserCredentialsResponse {
	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserCredentialsResponse(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public UserCredentialsResponse() {
		// TODO Auto-generated constructor stub
	}

}
