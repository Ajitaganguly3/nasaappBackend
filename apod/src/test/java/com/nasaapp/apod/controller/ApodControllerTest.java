package com.nasaapp.apod.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nasaapp.apod.entity.Apod;
import com.nasaapp.apod.service.ApodService;

public class ApodControllerTest {

	@Mock
	private ApodService apodService;

	@InjectMocks
	private ApodController apodController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetApods() {
		// Mocking the behavior of apodService.getApods()
		when(apodService.getApods()).thenReturn(new Apod("Ajita Ganguly", "2024-01-19", "testExplanation",
				"https://apod.nasa.gov/apod/image/2401/testHdUrl.jpg", "image", "test_v1", "testImage",
				"https://apod.nasa.gov/apod/image/2401/testUrl.jpg"));

		// Calling the method from ApodController
		ResponseEntity<?> responseEntity = apodController.getApods();

		// Verifying that apodService.getApods() was called once
		verify(apodService, times(1)).getApods();

		// Verifying the response status code
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void testGetPictureByDate() {
		String testDate = "2024-01-19"; // Replace with your test date
		Apod expectedApod = new Apod("Ajita Ganguly", "2024-01-19", "testExplanation",
				"https://apod.nasa.gov/apod/image/2401/testHdUrl.jpg", "image", "test_v1", "testImage",
				"https://apod.nasa.gov/apod/image/2401/testUrl.jpg");

		// Mocking the behavior of apodService.getApodByDate()
		when(apodService.getApodByDate(testDate)).thenReturn(expectedApod);

		// Calling the method from ApodController
		Apod result = apodController.getPictureByDate(testDate);

		// Verifying that apodService.getApodByDate() was called once with the specified
		// date
		verify(apodService, times(1)).getApodByDate(testDate);

		// Verifying the result
		assertEquals(expectedApod, result);
	}
}
