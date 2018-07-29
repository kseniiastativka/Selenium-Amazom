package com.stativka.selenium.amazon.helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Numbers {

	public static double roundToTwoPlaces(double value) {
		return round(value,2);
	}

	private static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(Double.toString(value));
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
