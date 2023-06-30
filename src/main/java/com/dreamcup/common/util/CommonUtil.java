package com.dreamcup.common.util;

import java.util.Random;

public class CommonUtil {

	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	public static String randomAlphaNumeric(int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(ALPHA_NUMERIC_STRING.length());
			char randomChar = ALPHA_NUMERIC_STRING.charAt(index);
			sb.append(randomChar);
		}
		return sb.toString();
	}

	public static String randomNumeric(int length) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			int randomNum = random.nextInt(10);
			sb.append(randomNum);
		}
		return sb.toString();
	}

}
