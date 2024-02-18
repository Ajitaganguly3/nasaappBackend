package com.nasaapp.wishlist.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WishlistDTOTest {

	@Test
	void testGettersAndSetters() {

		WishlistDTO wishlistDTO = new WishlistDTO("copyright1", "title1", "2024-01-21", "explanation1", "hdurl",
				"media_type", "service_version", "url1", "ajita1");

		// Testing getters
		assertEquals("copyright1", wishlistDTO.getCopyright());
		assertEquals("title1", wishlistDTO.getTitle());
		assertEquals("2024-01-21", wishlistDTO.getDate());
		assertEquals("explanation1", wishlistDTO.getExplanation());
		assertEquals("hdurl", wishlistDTO.getHdurl());
		assertEquals("media_type", wishlistDTO.getMedia_type());
		assertEquals("service_version", wishlistDTO.getService_version());
		assertEquals("url1", wishlistDTO.getUrl());
		assertEquals("ajita1", wishlistDTO.getUsername());

		// Testing setters
		wishlistDTO.setCopyright("New Copyright");
		wishlistDTO.setTitle("New Title");
		wishlistDTO.setDate("2024-01-20");
		wishlistDTO.setExplanation("New Explanation");
		wishlistDTO.setHdurl("New HDURL");
		wishlistDTO.setMedia_type("New MediaType");
		wishlistDTO.setService_version("New ServiceVersion");
		wishlistDTO.setUrl("New URL");
		wishlistDTO.setUsername("ajita");

		// Verifying changes
		assertEquals("New Copyright", wishlistDTO.getCopyright());
		assertEquals("New Title", wishlistDTO.getTitle());
		assertEquals("2024-01-20", wishlistDTO.getDate());
		assertEquals("New Explanation", wishlistDTO.getExplanation());
		assertEquals("New HDURL", wishlistDTO.getHdurl());
		assertEquals("New MediaType", wishlistDTO.getMedia_type());
		assertEquals("New ServiceVersion", wishlistDTO.getService_version());
		assertEquals("New URL", wishlistDTO.getUrl());
		assertEquals("ajita", wishlistDTO.getUsername());
	}
}
