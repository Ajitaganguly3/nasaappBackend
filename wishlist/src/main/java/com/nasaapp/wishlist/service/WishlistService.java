package com.nasaapp.wishlist.service;

import java.util.List;

import com.nasaapp.wishlist.dto.WishlistDTO;
import com.nasaapp.wishlist.exceptions.ImageAlreadyExistsException;
import com.nasaapp.wishlist.exceptions.ImageDoesNotExistException;
import com.nasaapp.wishlist.exceptions.WishlistEmptyException;

public interface WishlistService {

	void addToWishlist(WishlistDTO apodDTO) throws ImageAlreadyExistsException;

	List<WishlistDTO> getAllItems(String username) throws WishlistEmptyException;

	void deleteFromWishlist(WishlistDTO apodDTO) throws ImageDoesNotExistException;

}
