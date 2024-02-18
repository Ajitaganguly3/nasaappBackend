package com.nasaApp.login.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Authentication {

	@Id
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

	public Authentication() {

	}

	public Authentication(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

}
