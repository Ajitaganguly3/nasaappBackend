package com.nasaapp.wishlist.entity;

import java.io.Serializable;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class WishlistId implements Serializable {

	private static final long serialVersionUID = 8242309617655575798L;

	private String date;

	private String url;

	private String username;
	
	public WishlistId() {
		
	}

	public WishlistId(String username, String date, String url) {
		// TODO Auto-generated constructor stub
		this.username = username;
		this.date = date;
		this.url = url;
	}

	@Override
	public String toString() {
		return "WishlistId [username=" + username + ", date=" + date + ", url=" + url + "]";
	}

}
