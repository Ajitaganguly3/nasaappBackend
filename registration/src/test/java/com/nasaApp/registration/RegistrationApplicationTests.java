package com.nasaApp.registration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = RegistrationApplication.class)
class RegistrationApplicationTests {

	@Autowired
	private RegistrationApplication registrationApplication;

	@Test
	void contextLoads() {
		assertNotNull(registrationApplication);
	}

	@Test
	void mainMethod() {
		RegistrationApplication.main(new String[] {});
	}

	@Test
	void nasaAppOpenAPI() {
		assertNotNull(registrationApplication.nasaAppOpenAPI());
	}

}
