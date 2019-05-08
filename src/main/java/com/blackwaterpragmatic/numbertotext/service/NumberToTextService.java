package com.blackwaterpragmatic.numbertotext.service;

import java.util.LinkedHashMap;
import java.util.Map;

public class NumberToTextService {
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

	/**
	 * Note the use of LinkedHashMap. Insertion order is important here.
	 */
	private static final Map<Integer, String> NUMBER_GROUPS = new LinkedHashMap<Integer, String>() {
		{
			put(ONE_BILLION, "billion");
			put(ONE_MILLION, "million");
			put(ONE_THOUSAND, "thousand");
			put(ONE, "");
		}
	};

	public String convert(final String numberToConvert) {
		final Integer number;
		try {
			number = Integer.valueOf(numberToConvert);
		} catch (@SuppressWarnings("unused") final NumberFormatException e) {
			return String.format("ERROR: Number must be a whole number in the range of %d to %d", Integer.MIN_VALUE, Integer.MAX_VALUE);
		}

		final String result;
		if (0 == number) {
			result = buildZeroString();
		} else {
			result = buildNumberString(number);
		}
		return capitalizeResult(result.trim());
	}

	private String buildNumberString(final Integer originalNumber) {
		final StringBuilder result = new StringBuilder();
		Long number = originalNumber.longValue();

		/**
		 * Handle negative numbers
		 */
		if (negativeNumber(number)) {
			result.append("minus ");
			number = convertToPositiveNumber(number);
		}

		/**
		 * Start with the largest number group (billions, millions, etc)
		 * and append each group to the number string until we've processed
		 * all the groups
		 */
		for (final Integer numberGroup : NUMBER_GROUPS.keySet()) {
			/**
			 * If there are numbers in this group to print,
			 * add the leading 3 digits to the result string
			 * then move to the next number group
			 */
			if (printGroupNumbers(number, numberGroup)) {
				final Integer groupNumbers = getGroupNumbers(number, numberGroup);
				result.append(buildGroupNumberString(groupNumbers) + " ");
				result.append(buildGroupLabel(numberGroup));
				number = shiftToNextGroup(number, numberGroup);
			}
		}

		return result.toString();
	}

	private Integer getGroupNumbers(final Long number, final Integer numberGroup) {
		return new Long(number / numberGroup).intValue();
	}

	private String buildGroupLabel(final Integer numberGroup) {
		return NUMBER_GROUPS.get(numberGroup) + " ";
	}

	private Long shiftToNextGroup(final Long number, final Integer numberGroup) {
		return number % numberGroup;
	}

	private boolean printGroupNumbers(final Long number, final Integer numberGroup) {
		return getGroupNumbers(number, numberGroup) > 0;
	}

	private boolean negativeNumber(final Long number) {
		return number < 0;
	}

	private Long convertToPositiveNumber(final Long number) {
		return number * -1;
	}

	private String buildZeroString() {
		return ONES[0];
	}

	private String capitalizeResult(final String resultString) {
		final StringBuilder result = new StringBuilder(resultString);
		result.setCharAt(0, result.substring(0, 1).toUpperCase().charAt(0));
		return result.toString();
	}

	private String buildGroupNumberString(final Integer numberGroup) {
		Integer number = numberGroup;
		final StringBuilder result = new StringBuilder();

		/**
		 * build the hundreds part of the string
		 */
		if ((number / ONE_HUNDRED) > 0) {
			result.append(ONES[number / ONE_HUNDRED] + " hundred ");
			number %= ONE_HUNDRED; // remove the leading digit
			if (moreNumberGroupsToProcess(number)) {
				result.append("and ");
			}
		}

		/**
		 * build the tens part of the string
		 */
		if ((number / TEN) > 0) {
			if (number < TWENTY) { // teen names combine the tens and ones position
				result.append(TEENS[number % TEN]);
				number = 0;
			} else {
				result.append(TENS[number / TEN] + " ");
				number %= TEN; // remove the leading digit
			}
		}

		/**
		 * build the ones part of the string
		 */
		if (moreNumberGroupsToProcess(number)) {
			result.append(ONES[number]);
		}
		return result.toString().trim();
	}

	private boolean moreNumberGroupsToProcess(final Integer numberGroup) {
		return numberGroup > 0;
	}


}
