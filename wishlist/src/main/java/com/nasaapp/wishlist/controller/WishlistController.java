package com.nasaapp.wishlist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nasaapp.wishlist.dto.WishlistDTO;
import com.nasaapp.wishlist.entity.Wishlist;
import com.nasaapp.wishlist.exceptions.ImageAlreadyExistsException;
import com.nasaapp.wishlist.exceptions.ImageDoesNotExistException;
import com.nasaapp.wishlist.exceptions.WishlistEmptyException;
import com.nasaapp.wishlist.service.WishlistService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/wishlist")
public class WishlistController {
	@Autowired
	private WishlistService wishlistService;

	public WishlistController(WishlistService wishlistService) {
		// TODO Auto-generated constructor stub
		this.wishlistService = wishlistService;
	}

	@Operation(summary = "To List the details of APOD")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Added to wishlist successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Wishlist.class))),
			@ApiResponse(responseCode = "409", description = "URL already exists in the wishlist", content = @Content) })

	@PostMapping("/add")
	public ResponseEntity<String> addToWishlist(@RequestBody WishlistDTO apodDTO) throws ImageAlreadyExistsException {
		try {
			wishlistService.addToWishlist(apodDTO);
			return ResponseEntity.ok("Added to wishlist successfully.");
		} catch (ImageAlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

	@Operation(summary = "To List the details of APOD")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "All the details retrived successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Wishlist.class))),
			@ApiResponse(responseCode = "400", description = "check the content", content = @Content) })

	@GetMapping("/all/{username}")
	public ResponseEntity<List<WishlistDTO>> getAllItems(@PathVariable String username) throws WishlistEmptyException {
		try {
			List<WishlistDTO> wishlistItems = wishlistService.getAllItems(username);
			return new ResponseEntity<>(wishlistItems, HttpStatus.OK);

		} catch (WishlistEmptyException e) {
			// TODO: handle exception
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}
	}

	@Operation(summary = "To List the details of APOD")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Deleted from wishlist successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Wishlist.class))),
			@ApiResponse(responseCode = "404", description = "Image does not exist in the wishlist", content = @Content) })

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteFromWishlist(@RequestBody WishlistDTO apodDTO)
			throws ImageDoesNotExistException {
		try {
			wishlistService.deleteFromWishlist(apodDTO);
			return ResponseEntity.ok("Deleted from wishlist successfully.");
		} catch (ImageDoesNotExistException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

}
