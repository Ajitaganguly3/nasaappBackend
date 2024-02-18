package com.nasaApp.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nasaApp.login.entity.Authentication;

public interface AuthRepository extends JpaRepository<Authentication, String> {

	Authentication findByUsername(String username);

}
