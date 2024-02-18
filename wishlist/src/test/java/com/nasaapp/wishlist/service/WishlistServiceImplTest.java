package com.nasaapp.wishlist.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.nasaapp.wishlist.dto.WishlistDTO;
import com.nasaapp.wishlist.entity.Wishlist;
import com.nasaapp.wishlist.entity.WishlistId;
import com.nasaapp.wishlist.exceptions.ImageAlreadyExistsException;
import com.nasaapp.wishlist.exceptions.ImageDoesNotExistException;
import com.nasaapp.wishlist.exceptions.WishlistEmptyException;
import com.nasaapp.wishlist.repository.WishlistRepository;

public class WishlistServiceImplTest {

	private WishlistServiceImpl wishlistService;

	private WishlistRepository wishlistRepository;

	@BeforeEach
	void setUp() {
		wishlistRepository = mock(WishlistRepository.class);
		wishlistService = new WishlistServiceImpl(wishlistRepository);
	}

	@Test
	public void testGetAllItems_Success() throws WishlistEmptyException {
		List<Wishlist> wishlistItem = new ArrayList<>();
		String username = "ajita1";
		wishlistItem.add(new Wishlist("copyright1", "title1", "2024-01-21", "explanation1", "hdurl", "media_type",
				"service_version", "url1", "ajita1"));
//		Wishlist item2 = new Wishlist("copyright2", "title2", "2024-01-20", "explanation2", "hdurl2", "media_type2",
//				"service_version2", "url2", "ajita2");
		when(wishlistRepository.findByUsername(username)).thenReturn(wishlistItem);

		List<WishlistDTO> result = wishlistService.getAllItems(username);

		assertNotNull(result);
		assertEquals(wishlistItem.size(), result.size());
	}

	@Test
	public void testGetAllItems_EmptyWishlist() throws WishlistEmptyException {
		// Mock data and repository behavior
		String username = "ajita1";
		Mockito.when(wishlistRepository.findByUsername(username)).thenReturn(new ArrayList<>());

		// Perform the test
		try {
			wishlistService.getAllItems(username);
			fail("Expected WishlistEmptyException");
		} catch (WishlistEmptyException e) {
			// Expected exception
			assertEquals("Wishlist is empty for user " + username, e.getMessage());
		}
	}

	@Test
	public void testAddToWishlist_Success() throws ImageAlreadyExistsException {
		WishlistDTO wishlistDTO = new WishlistDTO("copyright1", "title1", "2024-01-21", "explanation1", "hdurl",
				"media_type", "service_version", "url1", "ajita1");
		Wishlist wishlistEntity = new Wishlist("copyright1", "title1", "2024-01-21", "explanation1", "hdurl",
				"media_type", "service_version", "url1", "ajita1");
		WishlistId wishlistId = new WishlistId(wishlistDTO.getUsername(), wishlistDTO.getDate(), wishlistDTO.getUrl());

		Mockito.when(wishlistRepository.existsById(Mockito.any())).thenReturn(false);
		Mockito.when(wishlistRepository.save(Mockito.any())).thenReturn(wishlistEntity);

		try {
			wishlistService.addToWishlist(wishlistDTO);
		} catch (ImageAlreadyExistsException e) {
			assertEquals("Image with Id: " + wishlistId + " already exists in the wishlist.", e.getMessage());

		}

	}

	@Test
	public void testAddToWishlist_Conflict() throws ImageAlreadyExistsException {
		// Mock data and repository behavior
		WishlistDTO wishlistDTO = new WishlistDTO("copyright1", "title1", "2024-01-21", "explanation1", "hdurl",
				"media_type", "service_version", "url1", "ajita1");
		WishlistId wishlistId = new WishlistId(wishlistDTO.getUsername(), wishlistDTO.getDate(), wishlistDTO.getUrl());
		Mockito.when(wishlistRepository.existsById(Mockito.any())).thenReturn(true);

		// Perform the test
		try {
			wishlistService.addToWishlist(wishlistDTO);
		} catch (ImageAlreadyExistsException e) {
			// Expected exception
			assertEquals("Image with Id: " + wishlistId + " already exists in the wishlist.", e.getMessage());
		}
	}

	@Test
	public void testDeleteFromWishlist_Success() throws ImageDoesNotExistException {
		WishlistDTO wishlistDTO = new WishlistDTO("copyright1", "title1", "2024-01-21", "explanation1", "hdurl",
				"media_type", "service_version", "url1", "ajita1");
		WishlistId wishlistId = new WishlistId(wishlistDTO.getUsername(), wishlistDTO.getDate(), wishlistDTO.getUrl());
		Mockito.when(wishlistRepository.existsById(Mockito.any())).thenReturn(true);

		// Perform the test
		try {
			wishlistService.deleteFromWishlist(wishlistDTO);
		} catch (ImageDoesNotExistException e) {
			assertEquals("Image with ID " + wishlistId + " does not exist in the wishlist.", e.getMessage());
		}

	}

	@Test
	public void testDeleteFromWishlist_ImageDoesNotExistException() {
		WishlistDTO wishlistDTO = new WishlistDTO("copyright1", "ajita", "2024-01-21", "explanation1", "hdurl",
				"media_type", "service_version", "url1", "ajita");
		WishlistId wishlistId = new WishlistId(wishlistDTO.getUsername(), wishlistDTO.getDate(), wishlistDTO.getUrl());

		Mockito.when(wishlistRepository.existsById(Mockito.any())).thenReturn(false);

		// Perform the test
		try {
			wishlistService.deleteFromWishlist(wishlistDTO);

		} catch (ImageDoesNotExistException e) {
			// Expected exception
			assertEquals("Image with ID " + wishlistId + " does not exist in the wishlist.", e.getMessage());
		}
	}

}
