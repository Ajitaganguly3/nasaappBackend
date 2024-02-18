package com.nasaApp.login.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.nasaApp.login.entity.Authentication;
import com.nasaApp.login.repository.AuthRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private AuthRepository authRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Authentication authentication = authRepository.findByUsername(username);
		if (authentication == null) {
			throw new UsernameNotFoundException("User not found with the name: " + username);
		}

		return User.builder().username(authentication.getUsername()).password(authentication.getPassword()).build();
	}

}
