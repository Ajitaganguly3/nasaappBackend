package com.nasaapp.wishlist.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nasaapp.wishlist.dto.WishlistDTO;
import com.nasaapp.wishlist.entity.Wishlist;
import com.nasaapp.wishlist.exceptions.ImageAlreadyExistsException;
import com.nasaapp.wishlist.exceptions.ImageDoesNotExistException;
import com.nasaapp.wishlist.exceptions.WishlistEmptyException;
import com.nasaapp.wishlist.service.WishlistService;

public class WishlistControllerTest {

	private WishlistService wishlistService;
	private WishlistController wishlistController;

	@BeforeEach
	void setUp() {
		wishlistService = mock(WishlistService.class);
		wishlistController = new WishlistController(wishlistService);

	}

	@Test
	void addToWishlist_Success() throws ImageAlreadyExistsException {
		WishlistDTO wishlistDTO = new WishlistDTO("copyright1", "title1", "2024-01-21", "explanation1", "hdurl",
				"media_type", "service_version", "url1", "ajita1");
		Mockito.doNothing().when(wishlistService).addToWishlist(Mockito.any(WishlistDTO.class));

		// Perform the test
		ResponseEntity<String> response = wishlistController.addToWishlist(wishlistDTO);

		// Assert the results
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Added to wishlist successfully.", response.getBody());

	}

	@Test
	void addToWishlist_Conflict() throws ImageAlreadyExistsException {
		WishlistDTO wishlistDTO = new WishlistDTO("copyright1", "title1", "2024-01-21", "explanation1", "hdurl",
				"media_type", "service_version", "url1", "ajita1");
		Mockito.doThrow(new ImageAlreadyExistsException("URL already exists in the wishlist")).when(wishlistService)
				.addToWishlist(Mockito.any(WishlistDTO.class));

		// Perform the test
		ResponseEntity<String> response = wishlistController.addToWishlist(wishlistDTO);

		// Assert the results
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
		assertEquals("URL already exists in the wishlist", response.getBody());

	}

	@Test
	void getAllItems_Success() throws WishlistEmptyException {

		String username = "ajita1";
		List<WishlistDTO> wishlistDTOs = new ArrayList<>();
		wishlistDTOs.add(new WishlistDTO("copyright1", "title1", "2024-01-21", "explanation1", "hdurl", "media_type",
				"service_version", "url1", "ajita1"));
		Mockito.when(wishlistService.getAllItems(username)).thenReturn(wishlistDTOs);

		// Perform the test
		ResponseEntity<List<WishlistDTO>> response = wishlistController.getAllItems(username);

		// Assert the results
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(wishlistDTOs.size(), response.getBody().size());

	}

	@Test
	void getAllItems_NotFound() throws WishlistEmptyException {

		String username = "ajita1";
		Mockito.when(wishlistService.getAllItems(username))
				.thenThrow(new WishlistEmptyException("Wishlist is empty for user " + username));

		// Perform the test
		ResponseEntity<List<WishlistDTO>> response = wishlistController.getAllItems(username);

		// Assert the results
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		//assertNotNull(response.getBody());

	}

	@Test
	void deleteFromWishlist_Success() throws ImageDoesNotExistException {
		WishlistDTO wishlistDTO = new WishlistDTO("copyright1", "title1", "2024-01-21", "explanation1", "hdurl",
				"media_type", "service_version", "url1", "ajita1");
		Mockito.doNothing().when(wishlistService).deleteFromWishlist(Mockito.any(WishlistDTO.class));

		// Perform the test
		ResponseEntity<String> response = wishlistController.deleteFromWishlist(wishlistDTO);

		// Assert the results
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Deleted from wishlist successfully.", response.getBody());

	}

	@Test
	void deleteFromWishlist_NotFound() throws ImageDoesNotExistException {
		WishlistDTO wishlistDTO = new WishlistDTO("copyright1", "title1", "2024-01-21", "explanation1", "hdurl",
				"media_type", "service_version", "url1", "ajita1");
		Mockito.doThrow(new ImageDoesNotExistException("Image does not exist in the wishlist")).when(wishlistService)
				.deleteFromWishlist(Mockito.any(WishlistDTO.class));

		// Perform the test
		ResponseEntity<String> response = wishlistController.deleteFromWishlist(wishlistDTO);

		// Assert the results
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Image does not exist in the wishlist", response.getBody());

	}

}
