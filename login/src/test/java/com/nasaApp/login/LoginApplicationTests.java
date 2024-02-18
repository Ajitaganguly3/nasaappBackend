package com.nasaApp.login;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(classes = LoginApplication.class)
class LoginApplicationTests {

	@Autowired
	LoginApplication loginApplication;

	@Autowired
	RestTemplate restTemplate;

	@Test
	public void contextLoads() {
		assertNotNull(loginApplication);
	}

	@Test
	void mainMethod() {
		LoginApplication.main(new String[] {});
	}

	@Test
	void nasaAppOpenAPI() {
		assertNotNull(loginApplication.nasaAppOpenAPI());
	}

	@Test
	void restTemplateBeanShouldExist() {
		assertThat(restTemplate).isNotNull();
	}

}
