package com.nasaapp.apod.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ApodTest {

	@Test
	void testGettersAndSetters() {

		Apod apod = new Apod("Copyright", "Title", "2024-01-19", "Explanation", "HDURL", "MediaType", "ServiceVersion",
				"URL");

		// Testing getters
		assertEquals("Copyright", apod.getCopyright());
		assertEquals("Title", apod.getTitle());
		assertEquals("2024-01-19", apod.getDate());
		assertEquals("Explanation", apod.getExplanation());
		assertEquals("HDURL", apod.getHdurl());
		assertEquals("MediaType", apod.getMedia_type());
		assertEquals("ServiceVersion", apod.getService_version());
		assertEquals("URL", apod.getUrl());

		// Testing setters
		apod.setCopyright("New Copyright");
		apod.setTitle("New Title");
		apod.setDate("2024-01-20");
		apod.setExplanation("New Explanation");
		apod.setHdurl("New HDURL");
		apod.setMedia_type("New MediaType");
		apod.setService_version("New ServiceVersion");
		apod.setUrl("New URL");

		// Verifying changes
		assertEquals("New Copyright", apod.getCopyright());
		assertEquals("New Title", apod.getTitle());
		assertEquals("2024-01-20", apod.getDate());
		assertEquals("New Explanation", apod.getExplanation());
		assertEquals("New HDURL", apod.getHdurl());
		assertEquals("New MediaType", apod.getMedia_type());
		assertEquals("New ServiceVersion", apod.getService_version());
		assertEquals("New URL", apod.getUrl());
	}

}
