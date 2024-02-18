package com.nasaApp.login.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

@TestPropertySource(properties = { "jwt.secret.key=my-secret-key" })
public class JWTUtilTest {

	@InjectMocks
	private JWTUtil jwtUtil;

	@Mock
	private Logger logger;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		ReflectionTestUtils.setField(jwtUtil, "logger", logger);
		ReflectionTestUtils.setField(jwtUtil, "secretKey", "testSecretKey");
	}

	@Test
	void testGenerateToken() {
		String username = "testUser";
		String generatedToken = jwtUtil.generateToken(username);

		Assertions.assertNotNull(generatedToken);

		Mockito.verify(logger).info(Mockito.anyString());
//		JWTUtil jwtUtil = new JWTUtil();
//		String token = jwtUtil.generateToken("testUser");
//		assertNotNull(token);
	}

}
