package com.nasaapp.apod.service;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nasaapp.apod.config.AppConstants;
import com.nasaapp.apod.entity.Apod;

@Service
public class ApodServiceImpl implements ApodService {

	@Autowired
	private RestTemplate restTemplate;

	private final Logger logger = LoggerFactory.getLogger(ApodServiceImpl.class);

	String lastReceivedMessage;

	String baseUrl = "https://api.nasa.gov/planetary/";

	StringBuilder stringBuilder = new StringBuilder(baseUrl);
	String apiUrl = "/apod?api_key=zhjoGExY6FeyM8buMcsnGj2fazcyfBeOzeH4dLBZ";
	String dateUrl = apiUrl + "&date=";

	public ApodServiceImpl(RestTemplate restTemplate) {
		// TODO Auto-generated constructor stub
		this.restTemplate = restTemplate;

	}

	@Override
	public Apod getApods() {

		System.out.println("Calling getApods method");
		String fullUrl = baseUrl + apiUrl;

		ResponseEntity<Apod> responseEntity = restTemplate.exchange(fullUrl, HttpMethod.GET,
				new HttpEntity<>(gethttpHeaders()), Apod.class);

		HttpHeaders headers = gethttpHeaders();
		System.out.println("Received headers: " + headers);

		return responseEntity.getBody();

	}

	@Override
	public Apod getApodByDate(String date) {
		// TODO Auto-generated method stub
		String fullUrl = baseUrl + dateUrl + date;

		ResponseEntity<Apod> responseEntity = restTemplate.exchange(fullUrl, HttpMethod.GET,
				new HttpEntity<>(gethttpHeaders()), Apod.class);

		HttpHeaders headers = gethttpHeaders();
		System.out.println("Received headers: " + headers);
		return responseEntity.getBody();

	}

	HttpHeaders gethttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		String token = getJWTTokenfromKafka();
		logger.info(token);
		System.out.println(token);
		headers.set("Authorization", token);
		return headers;
	}

	@KafkaListener(topics = AppConstants.NASAAPP_LOGIN_TOPIC_NAME, groupId = AppConstants.GROUP_ID)
	public void consumeLoginMessage(String message) {
		System.out.println("Received login message: " + message);
		lastReceivedMessage = message;
		System.out.println(lastReceivedMessage);
		System.out.println("Calling jwttoken method from consumer method: ");
		getJWTTokenfromKafka();

	}

	public String getJWTTokenfromKafka() {

		String prefix = "and the token generated is: ";
		int startIndex = lastReceivedMessage.indexOf(prefix);
		System.out.println(lastReceivedMessage);

		if (startIndex != -1) {
			String token = lastReceivedMessage.substring(startIndex + prefix.length()).trim();
			int endIndex = token.indexOf("\"");
			if (endIndex != -1) {
				token = token.substring(0, endIndex);
			}

			return token;
		} else {
			return "";
		}
	}

}
