package com.nasaapp.wishlist;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = WishlistApplication.class)
class WishlistApplicationTests {

	@Autowired
	WishlistApplication wishlistApplication;

	@Test
	void contextLoads() {
		assertNotNull(wishlistApplication);
	}

	@Test
	void mainMethod() {
		WishlistApplication.main(new String[] {});
	}

	@Test
	void nasaAppOpenAPI() {
		assertNotNull(wishlistApplication.nasaAppOpenAPI());
	}

}
