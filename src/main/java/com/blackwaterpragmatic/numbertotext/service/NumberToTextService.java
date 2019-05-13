package com.blackwaterpragmatic.numbertotext.service;


public interface NumberToTextService {

	/**
	 * Convert the input number to text. i.e "5237" will return "Five thousand two hundred and thirty seven"
	 * @param numberToConvert
	 * String representation of the number to convert
	 * @return String representation of the input number
	 * @throws NumberFormatException
	 * if the input number cannot be converted to text
	 */
	String convert(String numberToConvert) throws NumberFormatException;

}
