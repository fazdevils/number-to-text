package com.blackwaterpragmatic.numbertotext.service;

import java.util.HashMap;
import java.util.Map;

public class NumberToTextService {

	private static final int ONE = 1;
	private static final int ONE_THOUSAND = 1000;
	private static final int ONE_MILLION = 1000000;
	private static final int ONE_BILLION = 1000000000;

	private static final String[] ONES = new String[]{
			"zero",
			"one",
			"two",
			"three",
			"four",
			"five",
			"six",
			"seven",
			"eight",
			"nine"
	};

	private static final String[] TEENS = new String[]{
			"ten",
			"eleven",
			"twelve",
			"thirteen",
			"fourteen",
			"fifteen",
			"sixteen",
			"seventeen",
			"eightteen",
			"nineteen"
	};

	private static final String[] TENS = new String[]{
			null,
			null,
			"twenty",
			"thirty",
			"fourty",
			"fifty",
			"sixty",
			"seventy",
			"eighty",
			"ninety"
	};

	private static final Map<Integer, String> NUMBER_GROUPS = new HashMap<Integer, String>() {
		{
			put(ONE_BILLION, "billion");
			put(ONE_MILLION, "million");
			put(ONE_THOUSAND, "thousand");
			put(ONE, "");
		}
	};

	public String convert(final String numberToConvert) {
		try {
			final StringBuilder result = new StringBuilder();
			final Integer number = Integer.valueOf(numberToConvert);
			if (0 == number) {
				result.append(getZeroString());
			} else {
				result.append(buildNumberString(number));
			}
			return capitalizeResultString(result.toString().trim());
		} catch (final NumberFormatException e) {
			return String.format("String must be an integer in the range of %d to %d", Integer.MIN_VALUE, Integer.MAX_VALUE);
		}
	}

	private String buildNumberString(final Integer originalNumber) {
		Integer number = originalNumber;
		final StringBuilder result = new StringBuilder();
		if (number < 0) {
			result.append("minus ");
			number = convertToPositiveNumber(number);
		}

		/**
		 * Start with the largest number group (billions, millions, etc)
		 * and append each group to the number string until we've processed
		 * all the groups
		 */
		Integer numberGroup = ONE_BILLION;
		while (numberGroup > 0) {
			if ((number / numberGroup) > 0) {
				// add the leading 3 digits to the result string then remove them from the number
				result.append(buildNumberGroupString(number / numberGroup) + " " + NUMBER_GROUPS.get(numberGroup) + " ");
				number %= ONE_THOUSAND;
			}
			numberGroup /= ONE_THOUSAND; // move to the next number group
		}

		return result.toString();
	}

	private Integer convertToPositiveNumber(final Integer number) {
		return number * -1;
	}

	private String getZeroString() {
		return ONES[0];
	}

	private String capitalizeResultString(final String resultString) {
		final StringBuilder result = new StringBuilder(resultString);
		result.setCharAt(0, result.substring(0, 1).toUpperCase().charAt(0));
		return result.toString();
	}

	private String buildNumberGroupString(final Integer numberGroup) {
		Integer number = numberGroup;
		final StringBuilder result = new StringBuilder();

		// build the hundreds part of the string
		if ((number / 100) > 0) {
			result.append(ONES[number / 100] + " hundred ");
			number %= 100; // remove the leading digit
			if (number > 0) {
				result.append("and ");
			}
		}

		// build the tens part of the string
		if ((number / 10) > 0) {
			if (number < 20) { // teen names combine the tens and ones position
				result.append(TEENS[number % 10]);
				number = 0;
			} else {
				result.append(TENS[number / 10] + " ");
				number %= 10; // remove the leading digit
			}
		}

		// build the ones part of the string
		if (number > 0) {
			result.append(ONES[number]);
		}
		return result.toString().trim();
	}

}
