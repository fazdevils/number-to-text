package com.blackwaterpragmatic.numbertotext.service;

import java.util.HashMap;
import java.util.Map;

public class NumberToTextServiceViaText implements NumberToTextService {
	private static final int MAX_NUMBERS_IN_GROUP = 3;

	private static final Integer ONE = 1;
	private static final Integer TEN = 10;
	private static final Integer TWENTY = 20;
	private static final Integer ONE_HUNDRED = 100;
	private static final Integer ONE_THOUSAND = 1000;
	private static final Integer ONE_MILLION = 1000000;
	private static final Integer ONE_BILLION = 1000000000;

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
			"eighteen",
			"nineteen"
	};

	private static final String[] TENS = new String[]{
			null, // unused - values supplied by ONES
			null, // unused - values supplied by TEENS
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

	@Override
	public String convert(final String numberToConvert) throws NumberFormatException {
		final String result;
		if (!isNumber(numberToConvert)) {
			throw new NumberFormatException("numberToConvert can only contain an optional minus sign and digits");
		} else if (isZero(numberToConvert)) {
			result = buildZeroString();
		} else {
			result = buildNumberString(numberToConvert);
		}
		return capitalizeResult(result.trim());
	}

	@Override
	public String minValue() {
		return "-999999999999";
	}

	@Override
	public String maxValue() {
		return "999999999999";
	}

	private boolean isNumber(final String numberToConvert) {
		return numberToConvert.matches("-?[0-9]+");
	}

	private boolean isZero(final String numberToConvert) {
		return numberToConvert.matches("-?[0]+");
	}

	private String buildZeroString() {
		return ONES[0];
	}

	private String buildNumberString(final String numberToConvert) throws NumberFormatException {
		final StringBuilder input = new StringBuilder(numberToConvert);
		final StringBuilder result = new StringBuilder();

		/**
		 * Handle negative numbers
		 */
		final boolean isNegativeNumber = isNegativeNumber(input);
		if (isNegativeNumber) {
			input.deleteCharAt(0);
		}

		/**
		 * Start with the smallest number group (ones, hundreds, etc)
		 * and append each group to the number string until we've processed
		 * all the groups
		 */
		Integer numberGroup = ONE;
		while (input.length() > 0) {
			/**
			 * Print the last 3 digits to the result string (if applicable)
			 * then move to the next number group
			 */
			final int inputLength = input.length();
			if (inputLength > MAX_NUMBERS_IN_GROUP) {
				result.insert(0, buildGroupNumberString(input.substring(inputLength - MAX_NUMBERS_IN_GROUP, inputLength), numberGroup));
				input.setLength(inputLength - MAX_NUMBERS_IN_GROUP);
			} else {
				result.insert(0, buildGroupNumberString(input.toString(), numberGroup));
				input.setLength(0);
			}
			numberGroup *= ONE_THOUSAND;
		}

		if (isNegativeNumber) {
			result.insert(0, "minus ");
		}

		return capitalizeResult(result.toString().trim());
	}

	private boolean isNegativeNumber(final StringBuilder input) {
		return input.charAt(0) == '-';
	}

	private String capitalizeResult(final String resultString) {
		final StringBuilder result = new StringBuilder(resultString);
		result.setCharAt(0, result.substring(0, 1).toUpperCase().charAt(0));
		return result.toString();
	}

	private String buildGroupNumberString(final String number, final Integer numberGroup) throws NumberFormatException {
		Integer groupNumber = Integer.parseInt(number);
		final StringBuilder result = new StringBuilder();

		/**
		 * build the hundreds part of the string
		 */
		if ((groupNumber / ONE_HUNDRED) > 0) {
			result.append(ONES[groupNumber / ONE_HUNDRED]).append(" hundred ");
			groupNumber %= ONE_HUNDRED; // remove the leading digit
			if (isMoreNumberGroupsToProcess(groupNumber)) {
				result.append("and ");
			}
		}

		/**
		 * build the tens part of the string
		 */
		if ((groupNumber / TEN) > 0) {
			if (groupNumber < TWENTY) { // teen names combine the tens and ones position
				result.append(TEENS[groupNumber % TEN]).append(" ");
				groupNumber = 0;
			} else {
				result.append(TENS[groupNumber / TEN]).append(" ");
				groupNumber %= TEN; // remove the leading digit
			}
		}

		/**
		 * build the ones part of the string
		 */
		if (groupNumber > 0) {
			result.append(ONES[groupNumber]).append(" ");
		}

		if (result.length() > 0) {
			result.append(buildGroupLabel(numberGroup));
		}

		return result.toString();
	}

	private boolean isMoreNumberGroupsToProcess(final Integer numberGroup) {
		return numberGroup > 0;
	}

	private String buildGroupLabel(final Integer numberGroup) throws NumberFormatException {
		if (NUMBER_GROUPS.containsKey(numberGroup)) {
			return NUMBER_GROUPS.get(numberGroup) + " ";
		} else {
			throw new NumberFormatException("Cannot represent number");
		}
	}

}
