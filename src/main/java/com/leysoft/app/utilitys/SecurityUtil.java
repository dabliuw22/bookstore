package com.leysoft.app.utilitys;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {
	
	private static final String SALT = "salt";
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
	}
	
	@Bean
	public static String randomPassword() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder builder = new StringBuilder();
		Random random = new Random();
		while(builder.length() < 18) {
			int i = (int) (random.nextDouble()*SALTCHARS.length());
			builder.append(SALTCHARS.charAt(i));
		}
		return builder.toString();
	}
}
