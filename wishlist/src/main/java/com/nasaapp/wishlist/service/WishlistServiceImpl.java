package com.nasaapp.wishlist.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.nasaapp.wishlist.dto.WishlistDTO;
import com.nasaapp.wishlist.entity.Wishlist;
import com.nasaapp.wishlist.entity.WishlistId;
import com.nasaapp.wishlist.exceptions.ImageAlreadyExistsException;
import com.nasaapp.wishlist.exceptions.ImageDoesNotExistException;
import com.nasaapp.wishlist.exceptions.WishlistEmptyException;
import com.nasaapp.wishlist.repository.WishlistRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WishlistServiceImpl implements WishlistService {

	@Autowired
	private WishlistRepository wishlistRepository;

	public WishlistServiceImpl(WishlistRepository wishlistRepository) {
		// TODO Auto-generated constructor stub
		this.wishlistRepository = wishlistRepository;
	}

	@Override
	@Cacheable(cacheNames = "wishList", key = "#username")
	public List<WishlistDTO> getAllItems(String username) throws WishlistEmptyException {
		// TODO Auto-generated method stub

		List<Wishlist> wishlistItems = wishlistRepository.findByUsername(username);

		if (wishlistItems.isEmpty()) {
			throw new WishlistEmptyException("Wishlist is empty for user " + username);
		}

		return wishlistItems.stream().map(this::convertToDTO).collect(Collectors.toList());

	}

	@Override
	@CacheEvict(cacheNames = "wishList", allEntries = true)
	public void addToWishlist(WishlistDTO wishlistDTO) throws ImageAlreadyExistsException {
		// TODO Auto-generated method stub
		WishlistId wishlistId = new WishlistId(wishlistDTO.getUsername(), wishlistDTO.getDate(), wishlistDTO.getUrl());

		if (wishlistRepository.existsById(wishlistId)) {
			throw new ImageAlreadyExistsException("Image with Id: " + wishlistId + " already exists in the wishlist.");
		}

		Wishlist wishlist = new Wishlist();

		wishlist.setUrl(wishlistDTO.getUrl());
		wishlist.setTitle(wishlistDTO.getTitle());
		wishlist.setDate(wishlistDTO.getDate());
		wishlist.setCopyright(wishlistDTO.getCopyright());
		wishlist.setExplanation(wishlistDTO.getExplanation());
		wishlist.setHdurl(wishlistDTO.getHdurl());
		wishlist.setMedia_type(wishlistDTO.getMedia_type());
		wishlist.setService_version(wishlistDTO.getService_version());
		wishlist.setUsername(wishlistDTO.getUsername());

		wishlistRepository.save(wishlist);

	}

	@Override
	@CacheEvict(cacheNames = "wishList", allEntries = true)
	public void deleteFromWishlist(WishlistDTO wishlistDTO) throws ImageDoesNotExistException {
		// TODO Auto-generated method stub
		WishlistId wishlistId = new WishlistId(wishlistDTO.getUsername(), wishlistDTO.getDate(), wishlistDTO.getUrl());

		if (!wishlistRepository.existsById(wishlistId)) {
			throw new ImageDoesNotExistException("Image with ID " + wishlistId + " does not exist in the wishlist.");
		}

		wishlistRepository.deleteById(wishlistId);

	}

	private WishlistDTO convertToDTO(Wishlist wishlist) {
		WishlistDTO wishlistDTO = new WishlistDTO();
		wishlistDTO.setCopyright(wishlist.getCopyright());
		wishlistDTO.setDate(wishlist.getDate());
		wishlistDTO.setExplanation(wishlist.getExplanation());
		wishlistDTO.setHdurl(wishlist.getHdurl());
		wishlistDTO.setMedia_type(wishlist.getMedia_type());
		wishlistDTO.setService_version(wishlist.getService_version());
		wishlistDTO.setTitle(wishlist.getTitle());
		wishlistDTO.setUrl(wishlist.getUrl());
		wishlistDTO.setUsername(wishlist.getUsername());
		return wishlistDTO;
	}

}
