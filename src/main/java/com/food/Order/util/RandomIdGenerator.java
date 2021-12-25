package com.food.Order.util;

import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;

@Slf4j
public class RandomIdGenerator {

	private static final String CHAR_LOWER = "abcdefghijklmnpqrstuvwxyz";
	private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
	private static final String NUMBER = "123456789";

	private static final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
	private static final String DATA_FOR_ALPHA_NUMERIC_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
	private static SecureRandom random = new SecureRandom();

	public static String generateRandomString(int length) {
		log.info("Generate random number for booking id - ");
		if (length < 1)
			throw new IllegalArgumentException();

		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {

			// 0-62 (exclusive), random returns 0-61
			int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
			char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

			sb.append(rndChar);

		}

		return sb.toString();

	}

	public static String generateRandomNumber(int length) {
		log.info("Generate random number for booking id - ");
		if (length < 1)
			throw new IllegalArgumentException();

		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {

			// 0-62 (exclusive), random returns 0-61
			int rndCharAt = random.nextInt(NUMBER.length());
			char rndChar = NUMBER.charAt(rndCharAt);

			sb.append(rndChar);

		}

		return sb.toString();

	}

	public static String generateRandomAplhaNumericString(int length) {
		if (length < 1)
			throw new IllegalArgumentException();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			// 0-62 (exclusive), random returns 0-61
			int rndCharAt = random.nextInt(DATA_FOR_ALPHA_NUMERIC_RANDOM_STRING.length());
			char rndChar = DATA_FOR_ALPHA_NUMERIC_RANDOM_STRING.charAt(rndCharAt);
			sb.append(rndChar);
		}
		return sb.toString();
	}

}
