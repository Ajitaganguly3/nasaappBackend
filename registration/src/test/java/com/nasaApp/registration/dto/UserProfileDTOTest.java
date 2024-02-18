package com.nasaApp.registration.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UserProfileDTOTest {

	@Test
	void testGetters() {
		// Creating a UserProfileDTO object with sample data
		UserProfileDTO userProfileDTO = new UserProfileDTO("ajita", "ganguly", "ajita@mail.com", "ajita1", "9914366429",
				"Ajita@123", "Ajita@123");

		userProfileDTO.setFirstName("ajita");
		userProfileDTO.setLastName("ganguly");
		userProfileDTO.setUsername("ajita1");
		userProfileDTO.setEmail("ajita@mail.com");
		userProfileDTO.setContactNumber("9914366429");
		userProfileDTO.setPassword("Ajita@123");
		userProfileDTO.setConfirmPassword("Ajita@123");

		// Testing the getters
		assertEquals("ajita1", userProfileDTO.getUsername());
		assertEquals("ganguly", userProfileDTO.getLastName());
		assertEquals("ajita@mail.com", userProfileDTO.getEmail());
		assertEquals("ajita", userProfileDTO.getFirstName());
		assertEquals("9914366429", userProfileDTO.getContactNumber());
		assertEquals("Ajita@123", userProfileDTO.getPassword());
		assertEquals("Ajita@123", userProfileDTO.getConfirmPassword());
	}

}
