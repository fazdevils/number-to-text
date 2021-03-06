package com.blackwaterpragmatic.numbertotext.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class NumberToTextServiceViaNumbersTest {

	private final NumberToTextService numberToTextService = new NumberToTextServiceViaNumbers();

	@Test
	public void testZeroConversion() {
		assertEquals("Zero", numberToTextService.convert("0"));
	}

	@Test
	public void testOneConversion() {
		assertEquals("One", numberToTextService.convert("1"));
	}

	@Test
	public void testMinusOneConversion() {
		assertEquals("Minus one", numberToTextService.convert("-1"));
	}

	@Test
	public void testNineConversion() {
		assertEquals("Nine", numberToTextService.convert("9"));
	}

	@Test
	public void testTenConversion() {
		assertEquals("Ten", numberToTextService.convert("10"));
	}

	@Test
	public void testNineteenConversion() {
		assertEquals("Nineteen", numberToTextService.convert("19"));
	}

	@Test
	public void testTwentyConversion() {
		assertEquals("Twenty", numberToTextService.convert("20"));
	}

	@Test
	public void testTwentyNineConversion() {
		assertEquals("Twenty nine", numberToTextService.convert("29"));
	}

	@Test
	public void testNinetyNineConversion() {
		assertEquals("Ninety nine", numberToTextService.convert("99"));
	}

	@Test
	public void testOneHundredConversion() {
		assertEquals("One hundred", numberToTextService.convert("100"));
	}

	@Test
	public void testOneHundredOneConversion() {
		assertEquals("One hundred and one", numberToTextService.convert("101"));
	}

	@Test
	public void testNineHundredNinetyNine() {
		assertEquals("Nine hundred and ninety nine", numberToTextService.convert("999"));
	}

	@Test
	public void testOneThousand() {
		assertEquals("One thousand", numberToTextService.convert("1000"));
	}

	@Test
	public void testOneThousandOne() {
		assertEquals("One thousand one", numberToTextService.convert("1001"));
	}

	@Test
	public void testOneThousandEleven() {
		assertEquals("One thousand eleven", numberToTextService.convert("1011"));
	}

	@Test
	public void testOneThousandOneHundredOne() {
		assertEquals("One thousand one hundred and one", numberToTextService.convert("1101"));
	}

	@Test
	public void testOneThousandOneHundredEleven() {
		assertEquals("One thousand one hundred and eleven", numberToTextService.convert("1111"));
	}

	@Test
	public void testNineHundredNinetyNineThousdandNineHundredNinetyNine() {
		assertEquals("Nine hundred and ninety nine thousand nine hundred and ninety nine", numberToTextService.convert("999999"));
	}

	@Test
	public void testMinInteger() {
		assertEquals("Minus two billion one hundred and fourty seven million four hundred and eighty three thousand six hundred and fourty eight",
				numberToTextService.convert(numberToTextService.minValue()));
	}

	@Test
	public void testMaxInteger() {
		assertEquals("Two billion one hundred and fourty seven million four hundred and eighty three thousand six hundred and fourty seven",
				numberToTextService.convert(numberToTextService.maxValue()));
	}

	@Test(expected = NumberFormatException.class)
	public void testMinIntegerMinus1() {
		numberToTextService.convert("-2147483649");
		fail();
	}

	@Test(expected = NumberFormatException.class)
	public void testMaxIntegerPlus1() {
		numberToTextService.convert("2147483648");
		fail();
	}

	@Test(expected = NumberFormatException.class)
	public void testNonNumericInput() {
		numberToTextService.convert("string");
		fail();
	}

}
