package com.blackwaterpragmatic.numbertotext.service;

import java.util.LinkedHashMap;
import java.util.Map;

public class NumberToTextServiceViaNumbers implements NumberToTextService {
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

	/**
	 * Note the use of LinkedHashMap. Insertion order is important here. This decision might be a little unconventional,
	 * but it felt cleaner to loop through the group of NUMBER_GROUPS than using math to determine the group at each
	 * iteration. See NumberToTextServiceViaNumbers for the alternate implementation.
	 */
	private static final Map<Integer, String> NUMBER_GROUPS = new LinkedHashMap<Integer, String>() {
		{
			put(ONE_BILLION, "billion");
			put(ONE_MILLION, "million");
			put(ONE_THOUSAND, "thousand");
			put(ONE, "");
		}
	};

	@Override
	public String convert(final String numberToConvert) throws NumberFormatException {
		final Integer number = Integer.valueOf(numberToConvert);

		final String result;
		if (0 == number) {
			result = buildZeroString();
		} else {
			result = buildNumberString(number);
		}
		return capitalizeResult(result.trim());
	}


	@Override
	public String minValue() {
		return Integer.toString(Integer.MIN_VALUE);
	}

	@Override
	public String maxValue() {
		return Integer.toString(Integer.MAX_VALUE);
	}

	private String buildZeroString() {
		return ONES[0];
	}

	private String buildNumberString(final Integer originalNumber) {
		final StringBuilder result = new StringBuilder();
		Long number = originalNumber.longValue();

		/**
		 * Handle negative numbers
		 */
		if (number < 0) {
			result.append("minus ");
			number *= -1;
		}

		/**
		 * Start with the largest number group (billions, millions, etc)
		 * and append each group to the number string until we've processed
		 * all the groups
		 */
		for (final Integer numberGroup : NUMBER_GROUPS.keySet()) {
			/**
			 * Print the leading 3 digits to the result string (if applicable)
			 * then move to the next number group
			 */
			result.append(buildGroupNumberString(number, numberGroup));
			number = shiftToNextGroup(number, numberGroup);
		}

		return capitalizeResult(result.toString().trim());
	}

	private Long shiftToNextGroup(final Long number, final Integer numberGroup) {
		return number % numberGroup;
	}

	private String capitalizeResult(final String resultString) {
		final StringBuilder result = new StringBuilder(resultString);
		result.setCharAt(0, result.substring(0, 1).toUpperCase().charAt(0));
		return result.toString();
	}

	private String buildGroupNumberString(final Long number, final Integer numberGroup) {
		Integer groupNumber = getGroupNumber(number, numberGroup);
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

	private Integer getGroupNumber(final Long number, final Integer numberGroup) {
		return new Long(number / numberGroup).intValue();
	}

	private boolean isMoreNumberGroupsToProcess(final Integer numberGroup) {
		return numberGroup > 0;
	}

	private String buildGroupLabel(final Integer numberGroup) {
		return NUMBER_GROUPS.get(numberGroup) + " ";
	}

}
