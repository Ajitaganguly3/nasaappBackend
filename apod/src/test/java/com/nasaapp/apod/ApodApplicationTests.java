package com.nasaapp.apod;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(classes = ApodApplication.class)
class ApodApplicationTests {

	@Autowired
	ApodApplication apodApplication;

	@Autowired
	RestTemplate restTemplate;

	@Test
	void contextLoads() {
		assertNotNull(apodApplication);
	}

	@Test
	void mainMethod() {
		ApodApplication.main(new String[] {});
	}

	@Test
	void restTemplateBeanShouldExist() {
		assertThat(restTemplate).isNotNull();
	}

	@Test
	void nasaAppOpenAPI() {
		assertNotNull(apodApplication.nasaAppOpenAPI());
	}

}
