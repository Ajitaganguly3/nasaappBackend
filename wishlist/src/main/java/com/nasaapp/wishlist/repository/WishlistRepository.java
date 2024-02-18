package com.nasaapp.wishlist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nasaapp.wishlist.entity.Wishlist;
import com.nasaapp.wishlist.entity.WishlistId;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, WishlistId> {

	List<Wishlist> findByUsername(String username);

}
