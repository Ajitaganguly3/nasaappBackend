package com.nasaapp.apod.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.nasaapp.apod.entity.Apod;

public class ApodServiceImplTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private ApodServiceImpl apodService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		apodService.lastReceivedMessage = "Login successful for user: ajita2 and the token generated is: mockedToken\"";
	}

	@Test
	void testGetApods() {
		// Arrange
		String baseUrl = "https://api.nasa.gov/planetary/";
		String apiUrl = "/apod?api_key=zhjoGExY6FeyM8buMcsnGj2fazcyfBeOzeH4dLBZ";
		String fullUrl = baseUrl + apiUrl;

		Apod expectedApod = new Apod(); // Provide expected Apod object

		when(restTemplate.exchange(eq(fullUrl), eq(HttpMethod.GET), any(HttpEntity.class), eq(Apod.class)))
				.thenReturn(new ResponseEntity<>(expectedApod, null, 200));

		// Act
		Apod result = apodService.getApods();

		// Assert
		assertEquals(expectedApod, result);
		verify(restTemplate, times(1)).exchange(eq(fullUrl), eq(HttpMethod.GET), any(HttpEntity.class), eq(Apod.class));
	}

	@Test
	void testGetApodByDate() {
		// Arrange
		String baseUrl = "https://api.nasa.gov/planetary/";
		String dateUrl = "/apod?api_key=zhjoGExY6FeyM8buMcsnGj2fazcyfBeOzeH4dLBZ&date=";
		String date = "2022-01-01";
		String fullUrl = baseUrl + dateUrl + date;

		Apod expectedApod = new Apod(); // Provide expected Apod object

		when(restTemplate.exchange(eq(fullUrl), eq(HttpMethod.GET), any(HttpEntity.class), eq(Apod.class)))
				.thenReturn(new ResponseEntity<>(expectedApod, null, 200));

		// Act
		Apod result = apodService.getApodByDate(date);

		// Assert
		assertEquals(expectedApod, result);
		verify(restTemplate, times(1)).exchange(eq(fullUrl), eq(HttpMethod.GET), any(HttpEntity.class), eq(Apod.class));
	}

	@Test
	void gethttpHeaders() {
		// Arrange
		HttpHeaders expectedHeaders = new HttpHeaders();
		expectedHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		expectedHeaders.setContentType(MediaType.APPLICATION_JSON);

		String token = apodService.getJWTTokenfromKafka();
		expectedHeaders.set("Authorization", token);

	}

	@Test
	void testConsumeLoginMessage() {
		// Arrange
		String message = "Login message";

		// Act
		apodService.consumeLoginMessage(message);

		// Assert
		// Add assertions based on the expected behavior of consumeLoginMessage method
	}

	@Test
	void testGetJWTTokenfromKafka() {
		// Arrange
		String message = "and the token generated is: mockedToken\"";
		apodService.lastReceivedMessage = message;

		// Act
		String result = apodService.getJWTTokenfromKafka();

		// Assert
		assertEquals("mockedToken", result);
	}

	@Test
	void testGetJWTTokenfromKafka_NoToken() {
		// Arrange
		String message = "Login successful for user: ajita2 and the token generated is: mockedToken\"";
		apodService.lastReceivedMessage = message;

		// Act
		String result = apodService.getJWTTokenfromKafka();

		// Assert
		assertEquals("mockedToken", result);
	}

}
