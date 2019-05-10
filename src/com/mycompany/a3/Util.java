package com.mycompany.a3;

import java.util.Random;

public class Util {
	public static final int tickRate = 15;
	public static final boolean DEBUG = false;
	
	private static Random ran = new Random();

	public static int randomInt(int min, int max) {
		return (int) (ran.nextDouble() * (max - min + 1) + min);

	}

	public static int randomInt(int max) {

		return randomInt(0, max);
	}

	public static String formatDouble(double input, int decimalPlaces) {
		String inputAsString = Double.toString(input);

		int endIndex = inputAsString.indexOf('.') + decimalPlaces + 1;
		if (endIndex == -1) {// means input is integer
			return inputAsString;
		} else if (endIndex < inputAsString.length()) {// means that input is longer than desired digits
			return inputAsString.substring(0, endIndex);
		} else { // means that input is shorter than required and needs to be padded with 0s
			int i = endIndex - inputAsString.length();
			for (; i > 0; i--) {
				inputAsString = inputAsString + 0;
			}
			return inputAsString;
		}

	}
	
	public static double normalizeAngleDeg(double unNormalized) {
		double normalizedDirection = unNormalized;

		if (normalizedDirection < 0) {
			while (normalizedDirection < 0) {
				normalizedDirection += 360;
			}
		} else if (normalizedDirection > 359.9) {
			while (normalizedDirection > 359.9) {
				normalizedDirection -= 360;
			}
		}
		assert (0 <= normalizedDirection && normalizedDirection < 360);
		return normalizedDirection;
	}

}
