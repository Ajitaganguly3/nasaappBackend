package com.nasaApp.login.util;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTUtil {

	@Value("${jwt.secret.key}")
	private String secretKey;
	private long expirationTime = 1000 * 60 * 60;

	Logger logger = LoggerFactory.getLogger(JWTUtil.class);

	public String generateToken(String username) {
		logger.info(secretKey);
		return Jwts.builder().setSubject(username).setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS256, secretKey).compact();
	}

}
